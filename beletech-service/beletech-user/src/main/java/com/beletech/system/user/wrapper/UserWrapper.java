package com.beletech.system.user.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.cache.DictCache;
import com.beletech.system.cache.SysCache;
import com.beletech.system.entity.Tenant;
import com.beletech.system.enums.DictEnum;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.vo.UserVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = Objects.requireNonNull(BeanUtil.copy(user, UserVO.class));
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		List<String> roleName = SysCache.getRoleNames(user.getRoleId());
		List<String> deptName = SysCache.getDeptNames(user.getDeptId());
		List<String> postName = SysCache.getPostNames(user.getPostId());
		userVO.setTenantName(tenant.getTenantName());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		userVO.setPostName(Func.join(postName));
		userVO.setSexName(DictCache.getValue(DictEnum.SEX, user.getSex()));
		userVO.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
		return userVO;
	}

}
