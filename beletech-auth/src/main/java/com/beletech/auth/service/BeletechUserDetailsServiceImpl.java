package com.beletech.auth.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import com.beletech.auth.constant.AuthConstant;
import com.beletech.auth.utils.TokenUtil;
import com.beletech.common.cache.CacheNames;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.*;
import com.beletech.system.entity.Tenant;
import com.beletech.system.feign.ISysClient;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.entity.UserInfo;
import com.beletech.system.user.enums.UserEnum;
import com.beletech.system.user.feign.IUserClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.List;

/**
 * 用户信息
 *
 * @author XueBing
 */
@Service
@AllArgsConstructor
public class BeletechUserDetailsServiceImpl implements UserDetailsService {

	public static final Integer FAIL_COUNT = 5;

	private final IUserClient userClient;
	private final ISysClient sysClient;

	private final BeletechRedis beletechRedis;

	@Override
	@SneakyThrows
	public BeletechUserDetails loadUserByUsername(String username) {
		HttpServletRequest request = WebUtil.getRequest();
		// 获取用户绑定ID
		String headerDept = request.getHeader(TokenUtil.DEPT_HEADER_KEY);
		String headerRole = request.getHeader(TokenUtil.ROLE_HEADER_KEY);
		// 获取租户ID
		String headerTenant = request.getHeader(TokenUtil.TENANT_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.TENANT_PARAM_KEY);
		String password = request.getParameter(TokenUtil.PASSWORD_KEY);
		String grantType = request.getParameter(TokenUtil.GRANT_TYPE_KEY);
		if (StringUtil.isAllBlank(headerTenant, paramTenant)) {
			throw new UserDeniedAuthorizationException(TokenUtil.TENANT_NOT_FOUND);
		}
		String tenantId = StringUtils.isBlank(headerTenant) ? paramTenant : headerTenant;

		// 判断登录是否锁定
		// TODO 2.8.3版本将增加：1.参数管理读取配置 2.用户管理增加解封按钮
		int count = getFailCount(tenantId, username);
		if (count >= FAIL_COUNT) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_TOO_MANY_FAILS);
		}

		// 获取租户信息
		Result<Tenant> tenant = sysClient.getTenant(tenantId);
		if (tenant.isSuccess()) {
			if (TokenUtil.judgeTenant(tenant.getData())) {
				throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
			}
		} else {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_TENANT);
		}

		// 获取用户类型
		String userType = Func.toStr(request.getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		// 远程调用返回数据
		Result<UserInfo> result;
		// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
		if (userType.equals(UserEnum.WEB.getName())) {
			result = userClient.userInfo(tenantId, username, UserEnum.WEB.getName());
		} else if (userType.equals(UserEnum.APP.getName())) {
			result = userClient.userInfo(tenantId, username, UserEnum.APP.getName());
		} else {
			result = userClient.userInfo(tenantId, username, UserEnum.OTHER.getName());
		}

		// 判断返回信息
		if (result.isSuccess()) {
			UserInfo userInfo = result.getData();
			User user = userInfo.getUser();
			// 用户不存在,但提示用户名与密码错误并锁定账号
			if (user == null || user.getId() == null) {
				setFailCount(tenantId, username, count);
				throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
			}
			// 用户存在但密码错误,超过次数则锁定账号
			if (!grantType.equals(TokenUtil.REFRESH_TOKEN_KEY) && !user.getPassword().equals(DigestUtil.hex(password))) {
				setFailCount(tenantId, username, count);
				throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
			}
			// 用户角色不存在
			if (Func.isEmpty(userInfo.getRoles())) {
				throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_ROLE);
			}
			// 多部门情况下指定单部门
			if (Func.isNotEmpty(headerDept) && user.getDeptId().contains(headerDept)) {
				user.setDeptId(headerDept);
			}
			// 多角色情况下指定单角色
			if (Func.isNotEmpty(headerRole) && user.getRoleId().contains(headerRole)) {
				Result<List<String>> roleResult = sysClient.getRoleAliases(headerRole);
				if (roleResult.isSuccess()) {
					userInfo.setRoles(roleResult.getData());
				}
				user.setRoleId(headerRole);
			}
			return new BeletechUserDetails(user.getId(),
				user.getTenantId(), StringPool.EMPTY, user.getName(), user.getRealName(), user.getDeptId(), user.getPostId(), user.getRoleId(), Func.join(userInfo.getRoles()), Func.toStr(user.getAvatar(), TokenUtil.DEFAULT_AVATAR),
				username, AuthConstant.ENCRYPT + user.getPassword(), userInfo.getDetail(), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(Func.join(result.getData().getRoles())));
		} else {
			throw new UsernameNotFoundException(result.getMsg());
		}
	}

	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(beletechRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		beletechRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}
}
