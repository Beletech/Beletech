package com.beletech.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.system.entity.Role;
import com.beletech.system.vo.RoleVO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页
	 * @param role 参数
	 * @return 分页
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 树形结构
	 *
	 * @param tenantId 租户id
	 * @return 列表
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * 权限配置
	 *
	 * @param roleIds      角色id集合
	 * @param menuIds      菜单id集合
	 * @param dataScopeIds 数据权限id集合
	 * @param apiScopeIds  接口权限id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> roleIds, List<Long> menuIds, List<Long> dataScopeIds, List<Long> apiScopeIds);

	/**
	 * 获取角色ID
	 *
	 * @param tenantId  住户id
	 * @param roleNames 角色名称
	 * @return id
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 角色id
	 * @return 名称集合
	 */
	List<String> getRoleNames(String roleIds);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 角色id
	 * @return 名称
	 */
	List<String> getRoleAliases(String roleIds);

	/**
	 * 提交
	 *
	 * @param role 参数
	 * @return 状态
	 */
	boolean submit(Role role);

	/**
	 * 角色信息查询
	 *
	 * @param roleName 角色名称
	 * @param parentId 父id
	 * @return 集合
	 */
	List<RoleVO> search(String roleName, Long parentId);

}
