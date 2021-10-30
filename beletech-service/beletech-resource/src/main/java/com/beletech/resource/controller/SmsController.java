package com.beletech.resource.controller;

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
import com.beletech.resource.entity.Sms;
import com.beletech.resource.service.ISmsService;
import com.beletech.resource.vo.SmsVO;
import com.beletech.resource.wrapper.SmsWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static com.beletech.core.cache.constant.CacheConstant.RESOURCE_CACHE;

/**
 * 短信配置表 控制器
 *
 * @author Beletech
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/sms")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@Api(value = "短信配置表", tags = "短信配置表接口")
public class SmsController extends BeletechController {

	private final ISmsService smsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sms")
	public Result<SmsVO> detail(Sms sms) {
		Sms detail = smsService.getOne(Condition.getQueryWrapper(sms));
		return Result.data(SmsWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 短信配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入sms")
	public Result<IPage<SmsVO>> list(Sms sms, Query query) {
		IPage<Sms> pages = smsService.page(Condition.getPage(query), Condition.getQueryWrapper(sms));
		return Result.data(SmsWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 短信配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入sms")
	public Result<IPage<SmsVO>> page(SmsVO sms, Query query) {
		IPage<SmsVO> pages = smsService.selectSmsPage(Condition.getPage(query), sms);
		return Result.data(pages);
	}

	/**
	 * 新增 短信配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入sms")
	public Result save(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(smsService.save(sms));
	}

	/**
	 * 修改 短信配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入sms")
	public Result update(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(smsService.updateById(sms));
	}

	/**
	 * 新增或修改 短信配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入sms")
	public Result submit(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(smsService.submit(sms));
	}


	/**
	 * 删除 短信配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(smsService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 启用
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "配置启用", notes = "传入id")
	public Result enable(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(smsService.enable(id));
	}


}
