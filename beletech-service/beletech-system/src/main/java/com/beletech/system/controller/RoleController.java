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
import com.beletech.core.tool.utils.Func;
import com.beletech.system.cache.SysCache;
import com.beletech.system.entity.Role;
import com.beletech.system.service.IRoleService;
import com.beletech.system.vo.GrantVO;
import com.beletech.system.vo.RoleVO;
import com.beletech.system.wrapper.RoleWrapper;
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
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class RoleController extends BeletechController {

	private final IRoleService roleService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public Result<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return Result.data(RoleWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public Result<List<RoleVO>> list(@ApiIgnore @RequestParam Map<String, Object> role, BeletechUser beletechUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!beletechUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, beletechUser.getTenantId()) : queryWrapper);
		return Result.data(RoleWrapper.build().listNodeVO(list));
	}

	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<RoleVO>> tree(String tenantId, BeletechUser beletechUser) {
		List<RoleVO> tree = roleService.tree(Func.toStrWithEmpty(tenantId, beletechUser.getTenantId()));
		return Result.data(tree);
	}

	@GetMapping("/tree-by-id")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public Result<List<RoleVO>> treeById(Long roleId, BeletechUser beletechUser) {
		Role role = SysCache.getRole(roleId);
		List<RoleVO> tree = roleService.tree(Func.notNull(role) ? role.getTenantId() : beletechUser.getTenantId());
		return Result.data(tree);
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public Result<Boolean> submit(@Valid @RequestBody Role role) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(roleService.submit(role));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return Result.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public Result<Boolean> grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds(), grantVO.getDataScopeIds(), grantVO.getApiScopeIds());
		return Result.status(temp);
	}

	@PreAuth(AuthConstant.PERMIT_ALL)
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉数据源", notes = "传入id集合")
	public Result<List<Role>> select(String roleId) {
		List<Role> list = roleService.list(Wrappers.<Role>lambdaQuery().in(Role::getId, Func.toLongList(roleId)));
		return Result.data(list);
	}
}
