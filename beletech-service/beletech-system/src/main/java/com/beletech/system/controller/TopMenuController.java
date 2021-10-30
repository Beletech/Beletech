package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.TopMenu;
import com.beletech.system.service.ITopMenuService;
import com.beletech.system.vo.GrantVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.beletech.core.cache.constant.CacheConstant.MENU_CACHE;
import static com.beletech.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 顶部菜单表 控制器
 *
 * @author Beletech
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/topmenu")
@Api(value = "顶部菜单表", tags = "顶部菜单")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class TopMenuController extends BeletechController {

	private final ITopMenuService topMenuService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入topMenu")
	public Result<TopMenu> detail(TopMenu topMenu) {
		TopMenu detail = topMenuService.getOne(Condition.getQueryWrapper(topMenu));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入topMenu")
	public Result<IPage<TopMenu>> list(TopMenu topMenu, Query query) {
		IPage<TopMenu> pages = topMenuService.page(Condition.getPage(query), Condition.getQueryWrapper(topMenu).lambda().orderByAsc(TopMenu::getSort));
		return Result.data(pages);
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入topMenu")
	public Result<Boolean> save(@Valid @RequestBody TopMenu topMenu) {
		return Result.status(topMenuService.save(topMenu));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入topMenu")
	public Result<Boolean> update(@Valid @RequestBody TopMenu topMenu) {
		return Result.status(topMenuService.updateById(topMenu));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入topMenu")
	public Result<Boolean> submit(@Valid @RequestBody TopMenu topMenu) {
		return Result.status(topMenuService.saveOrUpdate(topMenu));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return Result.status(topMenuService.deleteLogic(Func.toLongList(ids)));
	}

	@PostMapping("/grant")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "顶部菜单配置", notes = "传入topMenuId集合以及menuId集合")
	public Result<Boolean> grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		boolean temp = topMenuService.grant(grantVO.getTopMenuIds(), grantVO.getMenuIds());
		return Result.status(temp);
	}
}
