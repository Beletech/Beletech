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
import com.beletech.resource.entity.Oss;
import com.beletech.resource.service.IOssService;
import com.beletech.resource.vo.OssVO;
import com.beletech.resource.wrapper.OssWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static com.beletech.core.cache.constant.CacheConstant.RESOURCE_CACHE;

/**
 * 控制器
 *
 * @author Beletech
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/oss")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@Api(value = "对象存储接口", tags = "对象存储接口")
public class OssController extends BeletechController {

	private final IOssService ossService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入oss")
	public Result<OssVO> detail(Oss oss) {
		Oss detail = ossService.getOne(Condition.getQueryWrapper(oss));
		return Result.data(OssWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入oss")
	public Result<IPage<OssVO>> list(Oss oss, Query query) {
		IPage<Oss> pages = ossService.page(Condition.getPage(query), Condition.getQueryWrapper(oss));
		return Result.data(OssWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入oss")
	public Result<IPage<OssVO>> page(OssVO oss, Query query) {
		IPage<OssVO> pages = ossService.selectOssPage(Condition.getPage(query), oss);
		return Result.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入oss")
	public Result save(@Valid @RequestBody Oss oss) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(ossService.save(oss));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入oss")
	public Result update(@Valid @RequestBody Oss oss) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(ossService.updateById(oss));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入oss")
	public Result submit(@Valid @RequestBody Oss oss) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(ossService.submit(oss));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(ossService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 启用
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "配置启用", notes = "传入id")
	public Result enable(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		CacheUtil.clear(RESOURCE_CACHE);
		return Result.status(ossService.enable(id));
	}

}
