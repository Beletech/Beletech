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
import com.beletech.system.entity.Dict;
import com.beletech.system.service.IDictService;
import com.beletech.system.vo.DictVO;
import com.beletech.system.wrapper.DictWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.DICT_CACHE;


/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends BeletechController {

	private final IDictService dictService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dict")
	public Result<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return Result.data(DictWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dict")
	public Result<List<DictVO>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		DictWrapper dictWrapper = new DictWrapper();
		return Result.data(dictWrapper.listNodeVO(list));
	}

	@GetMapping("/parent-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入dict")
	public Result<IPage<DictVO>> parentList(@ApiIgnore @RequestParam Map<String, Object> dict, Query query) {
		return Result.data(dictService.parentList(dict, query));
	}

	@GetMapping("/child-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "parentId", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "列表", notes = "传入dict")
	public Result<List<DictVO>> childList(@ApiIgnore @RequestParam Map<String, Object> dict, @RequestParam(required = false, defaultValue = "-1") Long parentId) {
		return Result.data(dictService.childList(dict, parentId));
	}

	@GetMapping("/tree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return Result.data(tree);
	}

	@GetMapping("/parent-tree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<DictVO>> parentTree() {
		List<DictVO> tree = dictService.parentTree();
		return Result.data(tree);
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	public Result<Boolean> submit(@Valid @RequestBody Dict dict) {
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		return Result.status(dictService.submit(dict));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		return Result.status(dictService.removeDict(ids));
	}

	@GetMapping("/dictionary")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取字典", notes = "获取字典")
	public Result<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return Result.data(tree);
	}

	@GetMapping("/dictionary-tree")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取字典树", notes = "获取字典树")
	public Result<List<DictVO>> dictionaryTree(String code) {
		List<Dict> tree = dictService.getList(code);
		return Result.data(DictWrapper.build().listNodeVO(tree));
	}
}
