package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.excel.util.ExcelUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.DateUtil;
import com.beletech.system.entity.Region;
import com.beletech.system.excel.RegionExcel;
import com.beletech.system.excel.RegionImporter;
import com.beletech.system.service.IRegionService;
import com.beletech.system.vo.RegionVO;
import com.beletech.system.wrapper.RegionWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/region")
@Api(value = "行政区划", tags = "行政区划")
public class RegionController extends BeletechController {

	private final IRegionService regionService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入region")
	public Result<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return Result.data(RegionWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入region")
	public Result<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return Result.data(pages);
	}

	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public Result<List<RegionVO>> lazyList(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyList(parentCode, menu);
		return Result.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	@GetMapping("/lazy-tree")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public Result<List<RegionVO>> lazyTree(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyTree(parentCode, menu);
		return Result.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增", notes = "传入region")
	public Result<Boolean> save(@Valid @RequestBody Region region) {
		return Result.status(regionService.save(region));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改", notes = "传入region")
	public Result<Boolean> update(@Valid @RequestBody Region region) {
		return Result.status(regionService.updateById(region));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增或修改", notes = "传入region")
	public Result<Boolean> submit(@Valid @RequestBody Region region) {
		return Result.status(regionService.submit(region));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入主键")
	public Result<Boolean> remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		return Result.status(regionService.removeRegion(id));
	}

	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public Result<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return Result.data(list);
	}

	@PostMapping("import-region")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "导入行政区划", notes = "传入excel")
	public Result<Boolean> importRegion(MultipartFile file, Integer isCovered) {
		RegionImporter regionImporter = new RegionImporter(regionService, isCovered == 1);
		ExcelUtil.save(file, regionImporter, RegionExcel.class);
		return Result.success("操作成功");
	}

	@GetMapping("export-region")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "导出行政区划", notes = "传入user")
	public void exportRegion(@ApiIgnore @RequestParam Map<String, Object> region, HttpServletResponse response) {
		QueryWrapper<Region> queryWrapper = Condition.getQueryWrapper(region, Region.class);
		List<RegionExcel> list = regionService.exportRegion(queryWrapper);
		ExcelUtil.export(response, "行政区划数据" + DateUtil.time(), "行政区划数据表", list, RegionExcel.class);
	}

	@GetMapping("export-template")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<RegionExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "行政区划模板", "行政区划表", list, RegionExcel.class);
	}
}
