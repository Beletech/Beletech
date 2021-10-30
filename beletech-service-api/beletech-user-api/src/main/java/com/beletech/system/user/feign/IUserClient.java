package com.beletech.system.user.feign;


import com.beletech.core.launch.constant.AppConstant;
import com.beletech.core.tool.api.Result;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.entity.UserInfo;
import com.beletech.system.user.entity.UserOauth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 * @author XueBing
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface IUserClient {

	String API_PREFIX = "/client";
	String USER_INFO = API_PREFIX + "/user-info";
	String USER_INFO_BY_TYPE = API_PREFIX + "/user-info-by-type";
	String USER_INFO_BY_ID = API_PREFIX + "/user-info-by-id";
	String USER_INFO_BY_ACCOUNT = API_PREFIX + "/user-info-by-account";
	String USER_AUTH_INFO = API_PREFIX + "/user-auth-info";
	String SAVE_USER = API_PREFIX + "/save-user";
	String REMOVE_USER = API_PREFIX + "/remove-user";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping(USER_INFO_BY_ID)
	Result<User> userInfoById(@RequestParam("userId") Long userId);


	/**
	 * 根据账号获取用户信息
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return 用户信息
	 */
	@GetMapping(USER_INFO_BY_ACCOUNT)
	Result<User> userByAccount(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @return 用户信息
	 */
	@GetMapping(USER_INFO)
	Result<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @param userType 用户平台
	 * @return 用户信息
	 */
	@GetMapping(USER_INFO_BY_TYPE)
	Result<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("userType") String userType);

	/**
	 * 获取第三方平台信息
	 *
	 * @param userOauth 第三方授权用户信息
	 * @return UserInfo
	 */
	@PostMapping(USER_AUTH_INFO)
	Result<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth);

	/**
	 * 新建用户
	 *
	 * @param user 用户实体
	 * @return 用户信息
	 */
	@PostMapping(SAVE_USER)
	Result<Boolean> saveUser(@RequestBody User user);

	/**
	 * 删除用户
	 *
	 * @param tenantIds 租户id集合
	 * @return 用户信息
	 */
	@PostMapping(REMOVE_USER)
	Result<Boolean> removeUser(@RequestParam("tenantIds") String tenantIds);
}
