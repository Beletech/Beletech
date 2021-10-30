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
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.ApiScope;
import com.beletech.system.service.IApiScopeService;
import com.beletech.system.vo.ApiScopeVO;
import com.beletech.system.wrapper.ApiScopeWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.beletech.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 接口权限控制器
 *
 * @author Beletech
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("api-scope")
@Api(value = "接口权限", tags = "接口权限")
public class ApiScopeController extends BeletechController {

	private final IApiScopeService apiScopeService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dataScope")
	public Result<ApiScope> detail(ApiScope dataScope) {
		ApiScope detail = apiScopeService.getOne(Condition.getQueryWrapper(dataScope));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入dataScope")
	public Result<IPage<ApiScopeVO>> list(ApiScope dataScope, Query query) {
		IPage<ApiScope> pages = apiScopeService.page(Condition.getPage(query), Condition.getQueryWrapper(dataScope));
		return Result.data(ApiScopeWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入dataScope")
	public Result<Boolean> save(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(apiScopeService.save(dataScope));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入dataScope")
	public Result<Boolean> update(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(apiScopeService.updateById(dataScope));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入dataScope")
	public Result<Boolean> submit(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(apiScopeService.saveOrUpdate(dataScope));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(apiScopeService.deleteLogic(Func.toLongList(ids)));
	}

}
