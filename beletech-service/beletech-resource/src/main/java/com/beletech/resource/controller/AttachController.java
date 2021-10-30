package com.beletech.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.entity.Attach;
import com.beletech.resource.service.IAttachService;
import com.beletech.resource.vo.AttachVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 附件表 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/attach")
@Api(value = "附件", tags = "附件")
public class AttachController extends BeletechController {

	private final IAttachService attachService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入attach")
	public Result<Attach> detail(Attach attach) {
		Attach detail = attachService.getOne(Condition.getQueryWrapper(attach));
		return Result.data(detail);
	}

	/**
	 * 分页 附件表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入attach")
	public Result<IPage<Attach>> list(Attach attach, Query query) {
		IPage<Attach> pages = attachService.page(Condition.getPage(query), Condition.getQueryWrapper(attach));
		return Result.data(pages);
	}

	/**
	 * 自定义分页 附件表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入attach")
	public Result<IPage<AttachVO>> page(AttachVO attach, Query query) {
		IPage<AttachVO> pages = attachService.selectAttachPage(Condition.getPage(query), attach);
		return Result.data(pages);
	}

	/**
	 * 新增 附件表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入attach")
	public Result save(@Valid @RequestBody Attach attach) {
		return Result.status(attachService.save(attach));
	}

	/**
	 * 修改 附件表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入attach")
	public Result update(@Valid @RequestBody Attach attach) {
		return Result.status(attachService.updateById(attach));
	}

	/**
	 * 新增或修改 附件表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入attach")
	public Result submit(@Valid @RequestBody Attach attach) {
		return Result.status(attachService.saveOrUpdate(attach));
	}


	/**
	 * 删除 附件表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return Result.status(attachService.deleteLogic(Func.toLongList(ids)));
	}


}
