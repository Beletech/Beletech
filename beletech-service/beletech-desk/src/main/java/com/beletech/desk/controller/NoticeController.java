package com.beletech.desk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.TenantDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.desk.entity.Notice;
import com.beletech.desk.feign.INoticeClient;
import com.beletech.desk.service.INoticeService;
import com.beletech.desk.vo.NoticeVO;
import com.beletech.desk.wrapper.NoticeWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author XueBing
 */
@TenantDS
@RestController
@RequestMapping("notice")
@AllArgsConstructor
@Api(value = "用户博客", tags = "博客接口")
public class NoticeController extends BeletechController {

	private final INoticeService noticeService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public Result<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return Result.data(NoticeWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入notice")
	public Result<IPage<NoticeVO>> list(@ApiIgnore @RequestParam Map<String, Object> notice, Query query) {
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return Result.data(NoticeWrapper.build().pageVO(pages));
	}

	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入notice")
	public Result<IPage<NoticeVO>> page(@ApiIgnore NoticeVO notice, Query query) {
		IPage<NoticeVO> pages = noticeService.selectNoticePage(Condition.getPage(query), notice);
		return Result.data(pages);
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入notice")
	public Result<Boolean> save(@RequestBody Notice notice) {
		return Result.status(noticeService.save(notice));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入notice")
	public Result<Boolean> update(@RequestBody Notice notice) {
		return Result.status(noticeService.updateById(notice));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入notice")
	public Result<Boolean> submit(@RequestBody Notice notice) {
		return Result.status(noticeService.saveOrUpdate(notice));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public Result<Boolean> remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return Result.status(temp);
	}
}
