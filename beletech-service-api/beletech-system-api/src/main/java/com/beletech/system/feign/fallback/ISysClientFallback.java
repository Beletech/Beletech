package com.beletech.system.feign.fallback;

import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.*;
import com.beletech.system.feign.ISysClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author XueBing
 */
@Component
public class ISysClientFallback implements ISysClient {

	@Override
	public Result<Menu> getMenu(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Dept> getDept(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getDeptIds(String tenantId, String deptNames) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getDeptIdsByFuzzy(String tenantId, String deptNames) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getDeptName(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<String>> getDeptNames(String deptIds) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<Dept>> getDeptChild(Long deptId) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Post> getPost(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getPostIds(String tenantId, String postNames) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getPostIdsByFuzzy(String tenantId, String postNames) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getPostName(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<String>> getPostNames(String postIds) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Role> getRole(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getRoleIds(String tenantId, String roleNames) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getRoleName(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getRoleAlias(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<String>> getRoleNames(String roleIds) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<String>> getRoleAliases(String roleIds) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Tenant> getTenant(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Tenant> getTenant(String tenantId) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Param> getParam(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getParamValue(String paramKey) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<Region> getRegion(String code) {
		return Result.fail("获取数据失败");
	}


}
