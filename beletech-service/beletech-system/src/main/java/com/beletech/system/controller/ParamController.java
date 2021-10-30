package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.Param;
import com.beletech.system.service.IParamService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.PARAM_CACHE;

/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "参数管理", tags = "接口")
public class ParamController extends BeletechController {

	private final IParamService paramService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入param")
	public Result<Param> detail(Param param) {
		Param detail = paramService.getOne(Condition.getQueryWrapper(param));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paramName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramKey", value = "参数键名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramValue", value = "参数键值", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入param")
	public Result<IPage<Param>> list(@ApiIgnore @RequestParam Map<String, Object> param, Query query) {
		IPage<Param> pages = paramService.page(Condition.getPage(query), Condition.getQueryWrapper(param, Param.class));
		return Result.data(pages);
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入param")
	public Result<Boolean> submit(@Valid @RequestBody Param param) {
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return Result.status(paramService.saveOrUpdate(param));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return Result.status(paramService.deleteLogic(Func.toLongList(ids)));
	}
}
