package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.system.entity.Role;
import com.beletech.system.vo.RoleVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页
	 * @param role 查询
	 * @return 分页对象
	 */
	List<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 获取树形节点
	 *
	 * @param tenantId    租户id
	 * @param excludeRole 排除角色
	 * @return 集合
	 */
	List<RoleVO> tree(String tenantId, String excludeRole);

	/**
	 * 获取角色名
	 *
	 * @param ids id集合
	 * @return 角色名称
	 */
	List<String> getRoleNames(Long[] ids);

	/**
	 * 获取角色名
	 *
	 * @param ids id集合
	 * @return 名称集合
	 */
	List<String> getRoleAliases(Long[] ids);

}
