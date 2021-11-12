package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.tool.constant.BeletechConstant;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.secure.BeletechUser;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.secure.constant.AuthConstant;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.support.Kv;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.cache.DictCache;
import com.beletech.system.entity.Dept;
import com.beletech.system.enums.DictEnum;
import com.beletech.system.service.IDeptService;
import com.beletech.system.vo.DeptVO;
import com.beletech.system.wrapper.DeptWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class DeptController extends BeletechController {

	private final IDeptService deptService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dept")
	public Result<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return Result.data(DeptWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dept")
	public Result<List<DeptVO>> list(@ApiIgnore @RequestParam Map<String, Object> dept, BeletechUser beletechUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!beletechUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, beletechUser.getTenantId()) : queryWrapper);
		return Result.data(DeptWrapper.build().listNodeVO(list));
	}

	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "懒加载列表", notes = "传入dept")
	public Result<List<DeptVO>> lazyList(@ApiIgnore @RequestParam Map<String, Object> dept, Long parentId, BeletechUser beletechUser) {
		List<DeptVO> list = deptService.lazyList(beletechUser.getTenantId(), parentId, dept);
		return Result.data(DeptWrapper.build().listNodeLazyVO(list));
	}

	@GetMapping("/tree")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<DeptVO>> tree(String tenantId, BeletechUser beletechUser) {
		List<DeptVO> tree = deptService.tree(Func.toStrWithEmpty(tenantId, beletechUser.getTenantId()));
		return Result.data(tree);
	}

	@GetMapping("/lazy-tree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "懒加载树形结构", notes = "树形结构")
	public Result<List<DeptVO>> lazyTree(String tenantId, Long parentId, BeletechUser beletechUser) {
		List<DeptVO> tree = deptService.lazyTree(Func.toStrWithEmpty(tenantId, beletechUser.getTenantId()), parentId);
		return Result.data(tree);
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入dept")
	public Result<Kv> submit(@Valid @RequestBody Dept dept) {
		if (deptService.submit(dept)) {
			CacheUtil.clear(SYS_CACHE);
			// 返回懒加载树更新节点所需字段
			Kv kv = Kv.create().set("id", String.valueOf(dept.getId())).set("tenantId", dept.getTenantId())
				.set("deptCategoryName", DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory()));
			return Result.data(kv);
		}
		return Result.fail("操作失败");
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return Result.status(deptService.removeDept(ids));
	}

	@PreAuth(AuthConstant.PERMIT_ALL)
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉数据源", notes = "传入id集合")
	public Result<List<Dept>> select(String deptId) {
		List<Dept> list = deptService.list(Wrappers.<Dept>lambdaQuery().in(Dept::getId, Func.toLongList(deptId)));
		return Result.data(list);
	}
}
