package com.beletech.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.core.secure.BeletechUser;
import com.beletech.core.tool.support.Kv;
import com.beletech.system.entity.Menu;
import com.beletech.system.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IMenuService extends IService<Menu> {

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
	 * @param param    参数
	 * @return 列表
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId    角色id
	 * @param topMenuId id
	 * @return 列表
	 */
	List<MenuVO> routes(String roleId, Long topMenuId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId 角色id
	 * @return 列表
	 */
	List<MenuVO> buttons(String roleId);

	/**
	 * 树形结构
	 *
	 * @return 集合
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @param user 用户
	 * @return 集合
	 */
	List<MenuVO> grantTree(BeletechUser user);

	/**
	 * 顶部菜单树形结构
	 *
	 * @param user 用户
	 * @return 列表
	 */
	List<MenuVO> grantTopTree(BeletechUser user);

	/**
	 * 数据权限授权树形结构
	 *
	 * @param user 参数
	 * @return 集合
	 */
	List<MenuVO> grantDataScopeTree(BeletechUser user);

	/**
	 * 接口权限授权树形结构
	 *
	 * @param user 用户
	 * @return 列表
	 */
	List<MenuVO> grantApiScopeTree(BeletechUser user);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds 角色id
	 * @return 名称
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 *
	 * @param topMenuIds id
	 * @return 名称
	 */
	List<String> topTreeKeys(String topMenuIds);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds 角色id
	 * @return 名称
	 */
	List<String> dataScopeTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds 角色id
	 * @return 名称
	 */
	List<String> apiScopeTreeKeys(String roleIds);

	/**
	 * 获取配置的角色权限
	 *
	 * @param user 用户
	 * @return 集合
	 */
	List<Kv> authRoutes(BeletechUser user);

	/**
	 * 删除菜单
	 *
	 * @param ids id
	 * @return 状态
	 */
	boolean removeMenu(String ids);

	/**
	 * 提交
	 *
	 * @param menu 菜单
	 * @return 状态
	 */
	boolean submit(Menu menu);

}
