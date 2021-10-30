package com.beletech.system.user.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.entity.UserInfo;
import com.beletech.system.user.entity.UserOauth;
import com.beletech.system.user.enums.UserEnum;
import com.beletech.system.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private final IUserService service;

	@Override
	@GetMapping(USER_INFO_BY_ID)
	public Result<User> userInfoById(Long userId) {
		return Result.data(service.getById(userId));
	}

	@Override
	@GetMapping(USER_INFO_BY_ACCOUNT)
	public Result<User> userByAccount(String tenantId, String account) {
		return Result.data(service.userByAccount(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO)
	public Result<UserInfo> userInfo(String tenantId, String account) {
		return Result.data(service.userInfo(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO_BY_TYPE)
	public Result<UserInfo> userInfo(String tenantId, String account, String userType) {
		return Result.data(service.userInfo(tenantId, account, UserEnum.of(userType)));
	}

	@Override
	@PostMapping(USER_AUTH_INFO)
	public Result<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth) {
		return Result.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(SAVE_USER)
	public Result<Boolean> saveUser(@RequestBody User user) {
		return Result.data(service.submit(user));
	}

	@Override
	@PostMapping(REMOVE_USER)
	public Result<Boolean> removeUser(String tenantIds) {
		return Result.data(service.remove(Wrappers.<User>query().lambda().in(User::getTenantId, Func.toStrList(tenantIds))));
	}

}
