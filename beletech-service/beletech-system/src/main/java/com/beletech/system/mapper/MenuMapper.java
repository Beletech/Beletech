package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beletech.system.dto.MenuDTO;
import com.beletech.system.entity.Menu;
import com.beletech.system.vo.MenuVO;

import java.util.List;
import java.util.Map;


/**
 * MenuMapper 接口
 *
 * @author XueBing
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 懒加载列表
	 *
	 * @param parentId 父id
	 * @param param    参数
	 * @return 列表
	 */
	List<MenuVO> lazyList(Long parentId, Map<String, Object> param);

	/**
	 * 懒加载菜单列表
	 *
	 * @param parentId 父id
	 * @param param 参数
	 * @return 列表
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param);

	/**
	 * 树形结构
	 *
	 * @return 列表
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @return 列表
	 */
	List<MenuVO> grantTree();

	/**
	 * 授权树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<MenuVO> grantTreeByRole(List<Long> roleId);

	/**
	 * 顶部菜单树形结构
	 *
	 * @return 列表
	 */
	List<MenuVO> grantTopTree();

	/**
	 * 顶部菜单树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<MenuVO> grantTopTreeByRole(List<Long> roleId);

	/**
	 * 数据权限授权树形结构
	 *
	 * @return 列表
	 */
	List<MenuVO> grantDataScopeTree();

	/**
	 * 接口权限授权树形结构
	 *
	 * @return 列表
	 */
	List<MenuVO> grantApiScopeTree();

	/**
	 * 数据权限授权树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<MenuVO> grantDataScopeTreeByRole(List<Long> roleId);

	/**
	 * 接口权限授权树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<MenuVO> grantApiScopeTreeByRole(List<Long> roleId);

	/**
	 * 所有菜单
	 *
	 * @return 列表
	 */
	List<Menu> allMenu();

	/**
	 * 权限配置菜单
	 *
	 * @param roleId 角色id
	 * @param topMenuId 父id
	 * @return 列表
	 */
	List<Menu> roleMenu(List<Long> roleId, Long topMenuId);

	/**
	 * 权限配置菜单
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<Menu> roleMenuByRoleId(List<Long> roleId);

	/**
	 * 权限配置菜单
	 *
	 * @param topMenuId id
	 * @return 列表
	 */
	List<Menu> roleMenuByTopMenuId(Long topMenuId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<Menu> routes(List<Long> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @return 列表
	 */
	List<Menu> allButtons();

	/**
	 * 按钮树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<Menu> buttons(List<Long> roleId);

	/**
	 * 获取配置的角色权限
	 *
	 * @param roleIds 角色id集合
	 * @return 列表
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);
}
