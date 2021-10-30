package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.secure.BeletechUser;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.support.Kv;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.Menu;
import com.beletech.system.entity.TopMenu;
import com.beletech.system.service.IMenuService;
import com.beletech.system.service.ITopMenuService;
import com.beletech.system.vo.CheckedTreeVO;
import com.beletech.system.vo.GrantTreeVO;
import com.beletech.system.vo.MenuVO;
import com.beletech.system.wrapper.MenuWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.MENU_CACHE;


/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController extends BeletechController {

	private final IMenuService menuService;
	private final ITopMenuService topMenuService;

	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入menu")
	public Result<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return Result.data(MenuWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入menu")
	public Result<List<MenuVO>> list(@ApiIgnore @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
		return Result.data(MenuWrapper.build().listNodeVO(list));
	}

	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public Result<List<MenuVO>> lazyList(Long parentId, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<MenuVO> list = menuService.lazyList(parentId, menu);
		return Result.data(MenuWrapper.build().listNodeLazyVO(list));
	}

	@GetMapping("/menu-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "菜单列表", notes = "传入menu")
	public Result<List<MenuVO>> menuList(@ApiIgnore @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().eq(Menu::getCategory, 1).orderByAsc(Menu::getSort));
		return Result.data(MenuWrapper.build().listNodeVO(list));
	}

	@GetMapping("/lazy-menu-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "懒加载菜单列表", notes = "传入menu")
	public Result<List<MenuVO>> lazyMenuList(Long parentId, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<MenuVO> list = menuService.lazyMenuList(parentId, menu);
		return Result.data(MenuWrapper.build().listNodeLazyVO(list));
	}

	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入menu")
	public Result<Kv> submit(@Valid @RequestBody Menu menu) {
		if (menuService.submit(menu)) {
			CacheUtil.clear(MENU_CACHE);
			CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
			// 返回懒加载树更新节点所需字段
			Kv kv = Kv.create().set("id", String.valueOf(menu.getId()));
			return Result.data(kv);
		}
		return Result.fail("操作失败");
	}

	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		return Result.status(menuService.removeMenu(ids));
	}

	@GetMapping("/routes")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
	public Result<List<MenuVO>> routes(BeletechUser user, Long topMenuId) {
		List<MenuVO> list = menuService.routes((user == null) ? null : user.getRoleId(), topMenuId);
		return Result.data(list);
	}

	@GetMapping("/buttons")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "前端按钮数据", notes = "前端按钮数据")
	public Result<List<MenuVO>> buttons(BeletechUser user) {
		List<MenuVO> list = menuService.buttons(user.getRoleId());
		return Result.data(list);
	}

	@GetMapping("/tree")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<MenuVO>> tree() {
		List<MenuVO> tree = menuService.tree();
		return Result.data(tree);
	}

	@GetMapping("/grant-tree")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
	public Result<GrantTreeVO> grantTree(BeletechUser user) {
		GrantTreeVO vo = new GrantTreeVO();
		vo.setMenu(menuService.grantTree(user));
		vo.setDataScope(menuService.grantDataScopeTree(user));
		vo.setApiScope(menuService.grantApiScopeTree(user));
		return Result.data(vo);
	}

	@GetMapping("/role-tree-keys")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
	public Result<CheckedTreeVO> roleTreeKeys(String roleIds) {
		CheckedTreeVO vo = new CheckedTreeVO();
		vo.setMenu(menuService.roleTreeKeys(roleIds));
		vo.setDataScope(menuService.dataScopeTreeKeys(roleIds));
		vo.setApiScope(menuService.apiScopeTreeKeys(roleIds));
		return Result.data(vo);
	}

	@GetMapping("/grant-top-tree")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "顶部菜单树形结构", notes = "顶部菜单树形结构")
	public Result<GrantTreeVO> grantTopTree(BeletechUser user) {
		GrantTreeVO vo = new GrantTreeVO();
		vo.setMenu(menuService.grantTopTree(user));
		return Result.data(vo);
	}

	@GetMapping("/top-tree-keys")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "顶部菜单所分配的树", notes = "顶部菜单所分配的树")
	public Result<CheckedTreeVO> topTreeKeys(String topMenuIds) {
		CheckedTreeVO vo = new CheckedTreeVO();
		vo.setMenu(menuService.topTreeKeys(topMenuIds));
		return Result.data(vo);
	}

	@GetMapping("/top-menu")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "顶部菜单数据", notes = "顶部菜单数据")
	public Result<List<TopMenu>> topMenu(BeletechUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		List<TopMenu> list = topMenuService.list(Wrappers.<TopMenu>query().lambda().orderByAsc(TopMenu::getSort));
		return Result.data(list);
	}

	@GetMapping("auth-routes")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "菜单的角色权限")
	public Result<List<Kv>> authRoutes(BeletechUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		return Result.data(menuService.authRoutes(user));
	}
}
