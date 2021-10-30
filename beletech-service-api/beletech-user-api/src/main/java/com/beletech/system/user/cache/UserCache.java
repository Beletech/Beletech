package com.beletech.system.user.cache;

import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.core.tool.utils.SpringUtil;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.core.tool.utils.StringUtil;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.feign.IUserClient;

import static com.beletech.core.cache.constant.CacheConstant.USER_CACHE;
import static com.beletech.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 系统缓存
 *
 * @author XueBing
 */
public class UserCache {
	private static final String USER_CACHE_ID = "user:id:";
	private static final String USER_CACHE_ACCOUNT = "user:account:";

	private static IUserClient userClient;

	private static IUserClient getUserClient() {
		if (userClient == null) {
			userClient = SpringUtil.getBean(IUserClient.class);
		}
		return userClient;
	}

	/**
	 * 根据任务用户id获取用户信息
	 *
	 * @param taskUserId 任务用户id
	 * @return 数据
	 */
	public static User getUserByTaskUser(String taskUserId) {
		Long userId = Func.toLong(StringUtil.removePrefix(taskUserId, TASK_USR_PREFIX));
		return getUser(userId);
	}

	/**
	 * 获取用户
	 *
	 * @param userId 用户id
	 * @return 数据
	 */
	public static User getUser(Long userId) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, userId, () -> {
			Result<User> result = getUserClient().userInfoById(userId);
			return result.getData();
		});
	}

	/**
	 * 获取用户
	 *
	 * @param tenantId 租户id
	 * @param account  账号名
	 * @return 数据
	 */
	public static User getUser(String tenantId, String account) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ACCOUNT, tenantId + StringPool.DASH + account, () -> {
			Result<User> result = getUserClient().userByAccount(tenantId, account);
			return result.getData();
		});
	}

}
