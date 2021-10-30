package com.beletech.system.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.core.mp.support.Query;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.entity.UserInfo;
import com.beletech.system.user.entity.UserOauth;
import com.beletech.system.user.enums.UserEnum;
import com.beletech.system.user.excel.UserExcel;
import com.beletech.system.user.vo.UserVO;

import java.util.List;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增用户
	 *
	 * @param user 用户
	 * @return 状态
	 */
	boolean submit(User user);

	/**
	 * 修改用户
	 *
	 * @param user 用户
	 * @return 状态
	 */
	boolean updateUser(User user);

	/**
	 * 修改用户基本信息
	 *
	 * @param user 用户
	 * @return 状态
	 */
	boolean updateUserInfo(User user);

	/**
	 * 自定义分页
	 *
	 * @param page     分页
	 * @param user     用户
	 * @param deptId   部门
	 * @param tenantId 租户
	 * @return 分页
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId);

	/**
	 * 自定义分页
	 *
	 * @param user  用户
	 * @param query 查询
	 * @return 分页
	 */
	IPage<UserVO> selectUserSearch(UserVO user, Query query);


	/**
	 * 用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return 用户信息
	 */
	UserInfo userInfo(String tenantId, String account);

	/**
	 * 用户信息
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @param userEnum 用户枚举
	 * @return 用户信息
	 */
	UserInfo userInfo(String tenantId, String account, UserEnum userEnum);

	/**
	 * 用户信息
	 *
	 * @param userOauth 用户
	 * @return 用户信息
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 根据账号获取用户
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return 用户
	 */
	User userByAccount(String tenantId, String account);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds 用户id
	 * @param roleIds 角色集合
	 * @return 状态
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds 用户id
	 * @return 状态
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId       用户id
	 * @param oldPassword  旧密码
	 * @param newPassword  新密码
	 * @param newPassword1 确认密码
	 * @return 状态
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 删除用户
	 *
	 * @param userIds 用户id
	 * @return 状态
	 */
	boolean removeUser(String userIds);

	/**
	 * 导入用户数据
	 *
	 * @param data      数据
	 * @param isCovered 是否覆盖
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * 导出用户数据
	 *
	 * @param queryWrapper 参数
	 * @return 集合
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user    用户
	 * @param oauthId id
	 * @return 状态
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 配置用户平台
	 *
	 * @param userId   用户id
	 * @param userType 用户类型
	 * @param userExt  ext
	 * @return 状态
	 */
	boolean updatePlatform(Long userId, Integer userType, String userExt);

	/**
	 * 用户详细信息
	 *
	 * @param user 用户信息
	 * @return 用户信息
	 */
	UserVO platformDetail(User user);
}
