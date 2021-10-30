package com.beletech.system.feign;

import lombok.AllArgsConstructor;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.*;
import com.beletech.system.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author XueBing
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

	private final IDeptService deptService;

	private final IPostService postService;

	private final IRoleService roleService;

	private final IMenuService menuService;

	private final ITenantService tenantService;

	private final IParamService paramService;

	private final IRegionService regionService;

	@Override
	@GetMapping(MENU)
	public Result<Menu> getMenu(Long id) {
		return Result.data(menuService.getById(id));
	}

	@Override
	@GetMapping(DEPT)
	public Result<Dept> getDept(Long id) {
		return Result.data(deptService.getById(id));
	}

	@Override
	public Result<String> getDeptIds(String tenantId, String deptNames) {
		return Result.data(deptService.getDeptIds(tenantId, deptNames));
	}

	@Override
	public Result<String> getDeptIdsByFuzzy(String tenantId, String deptNames) {
		return Result.data(deptService.getDeptIdsByFuzzy(tenantId, deptNames));
	}

	@Override
	@GetMapping(DEPT_NAME)
	public Result<String> getDeptName(Long id) {
		return Result.data(deptService.getById(id).getDeptName());
	}

	@Override
	@GetMapping(DEPT_NAMES)
	public Result<List<String>> getDeptNames(String deptIds) {
		return Result.data(deptService.getDeptNames(deptIds));
	}

	@Override
	@GetMapping(DEPT_CHILD)
	public Result<List<Dept>> getDeptChild(Long deptId) {
		return Result.data(deptService.getDeptChild(deptId));
	}

	@Override
	public Result<Post> getPost(Long id) {
		return Result.data(postService.getById(id));
	}

	@Override
	public Result<String> getPostIds(String tenantId, String postNames) {
		return Result.data(postService.getPostIds(tenantId, postNames));
	}

	@Override
	public Result<String> getPostIdsByFuzzy(String tenantId, String postNames) {
		return Result.data(postService.getPostIdsByFuzzy(tenantId, postNames));
	}

	@Override
	public Result<String> getPostName(Long id) {
		return Result.data(postService.getById(id).getPostName());
	}

	@Override
	public Result<List<String>> getPostNames(String postIds) {
		return Result.data(postService.getPostNames(postIds));
	}

	@Override
	@GetMapping(ROLE)
	public Result<Role> getRole(Long id) {
		return Result.data(roleService.getById(id));
	}

	@Override
	public Result<String> getRoleIds(String tenantId, String roleNames) {
		return Result.data(roleService.getRoleIds(tenantId, roleNames));
	}

	@Override
	@GetMapping(ROLE_NAME)
	public Result<String> getRoleName(Long id) {
		return Result.data(roleService.getById(id).getRoleName());
	}

	@Override
	@GetMapping(ROLE_ALIAS)
	public Result<String> getRoleAlias(Long id) {
		return Result.data(roleService.getById(id).getRoleAlias());
	}

	@Override
	@GetMapping(ROLE_NAMES)
	public Result<List<String>> getRoleNames(String roleIds) {
		return Result.data(roleService.getRoleNames(roleIds));
	}

	@Override
	@GetMapping(ROLE_ALIASES)
	public Result<List<String>> getRoleAliases(String roleIds) {
		return Result.data(roleService.getRoleAliases(roleIds));
	}

	@Override
	@GetMapping(TENANT)
	public Result<Tenant> getTenant(Long id) {
		return Result.data(tenantService.getById(id));
	}

	@Override
	@GetMapping(TENANT_ID)
	public Result<Tenant> getTenant(String tenantId) {
		return Result.data(tenantService.getByTenantId(tenantId));
	}

	@Override
	@GetMapping(PARAM)
	public Result<Param> getParam(Long id) {
		return Result.data(paramService.getById(id));
	}

	@Override
	@GetMapping(PARAM_VALUE)
	public Result<String> getParamValue(String paramKey) {
		return Result.data(paramService.getValue(paramKey));
	}

	@Override
	@GetMapping(REGION)
	public Result<Region> getRegion(String code) {
		return Result.data(regionService.getById(code));
	}


}
