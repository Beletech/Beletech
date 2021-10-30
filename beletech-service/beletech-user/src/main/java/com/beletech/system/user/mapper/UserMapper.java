package com.beletech.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.excel.UserExcel;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page       分页
	 * @param user       参数
	 * @param deptIdList 部门id
	 * @param tenantId   租户id
	 * @return 集合
	 */
	List<User> selectUserPage(IPage<User> page, @Param("user") User user, @Param("deptIdList") List<Long> deptIdList, @Param("tenantId") String tenantId);

	/**
	 * 获取用户
	 *
	 * @param tenantId 租户id
	 * @param account  账户
	 * @return 用户
	 */
	User getUser(String tenantId, String account);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper 查询参数
	 * @return 用户
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);

}
