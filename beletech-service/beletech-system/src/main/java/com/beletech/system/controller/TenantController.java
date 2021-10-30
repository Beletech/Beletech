package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.tool.constant.BeletechConstant;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.secure.BeletechUser;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.support.Kv;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.Tenant;
import com.beletech.system.service.ITenantService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.beletech.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_CACHE;
import static com.beletech.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_EXIST_KEY;

/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "租户管理", tags = "接口")
public class TenantController extends BeletechController {

	private final ITenantService tenantService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tenantId", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tenantName", value = "角色别名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "contactNumber", value = "联系电话", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<IPage<Tenant>> list(@ApiIgnore @RequestParam Map<String, Object> tenant, Query query, BeletechUser bladeUser) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return Result.data(pages);
	}

	@GetMapping("/select")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<List<Tenant>> select(Tenant tenant, BeletechUser bladeUser) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		List<Tenant> list = tenantService.list((!bladeUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return Result.data(list);
	}

	@GetMapping("/page")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分页", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<IPage<Tenant>> page(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return Result.data(pages);
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public Result<Boolean> submit(@Valid @RequestBody Tenant tenant) {
		return Result.status(tenantService.submitTenant(tenant));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return Result.status(tenantService.removeTenant(Func.toLongList(ids)));
	}

	@PostMapping("/setting")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "授权配置", notes = "传入ids,accountNumber,expireTime")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public Result<Boolean> setting(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @ApiParam(value = "账号额度") Integer accountNumber, @ApiParam(value = "过期时间") Date expireTime) {
		return Result.status(tenantService.setting(accountNumber, expireTime, ids));
	}

	@PostMapping("datasource")
	@ApiOperationSupport(order = 8)
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperation(value = "数据源配置", notes = "传入datasource_id")
	public Result<Boolean> datasource(@ApiParam(value = "租户ID", required = true) @RequestParam String tenantId, @ApiParam(value = "数据源ID", required = true) @RequestParam Long datasourceId){
		CacheUtil.evict(TENANT_DATASOURCE_CACHE, TENANT_DATASOURCE_EXIST_KEY, tenantId, Boolean.FALSE);
		return Result.status(tenantService.update(Wrappers.<Tenant>update().lambda().set(Tenant::getDatasourceId, datasourceId).eq(Tenant::getTenantId, tenantId)));
	}

	@GetMapping("/find-by-name")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "详情", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<List<Tenant>> findByName(String name) {
		List<Tenant> list = tenantService.list(Wrappers.<Tenant>query().lambda().like(Tenant::getTenantName, name));
		return Result.data(list);
	}

	@GetMapping("/info")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "配置信息", notes = "传入domain")
	public Result<Kv> info(String domain) {
		Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getDomain, domain));
		Kv kv = Kv.create();
		if (tenant != null) {
			kv.set("tenantId", tenant.getTenantId())
				.set("domain", tenant.getDomain())
				.set("backgroundUrl", tenant.getBackgroundUrl());
		}
		return Result.data(kv);
	}
}
