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
import com.beletech.system.entity.DataScope;
import com.beletech.system.service.IDataScopeService;
import com.beletech.system.vo.DataScopeVO;
import com.beletech.system.wrapper.DataScopeWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.beletech.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 数据权限控制器
 *
 * @author Beletech
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("data-scope")
@Api(value = "数据权限", tags = "数据权限")
public class DataScopeController extends BeletechController {

	private final IDataScopeService dataScopeService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dataScope")
	public Result<DataScope> detail(DataScope dataScope) {
		DataScope detail = dataScopeService.getOne(Condition.getQueryWrapper(dataScope));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入dataScope")
	public Result<IPage<DataScopeVO>> list(DataScope dataScope, Query query) {
		IPage<DataScope> pages = dataScopeService.page(Condition.getPage(query), Condition.getQueryWrapper(dataScope));
		return Result.data(DataScopeWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入dataScope")
	public Result<Boolean> save(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(dataScopeService.save(dataScope));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入dataScope")
	public Result<Boolean> update(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(dataScopeService.updateById(dataScope));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入dataScope")
	public Result<Boolean> submit(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(dataScopeService.saveOrUpdate(dataScope));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(dataScopeService.deleteLogic(Func.toLongList(ids)));
	}
}
