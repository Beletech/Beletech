package com.beletech.system.feign;

import com.beletech.core.launch.constant.AppConstant;
import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.*;
import com.beletech.system.feign.fallback.ISysClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author XueBing
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = ISysClientFallback.class
)
public interface ISysClient {

	String API_PREFIX = "/client";
	String MENU = API_PREFIX + "/menu";
	String DEPT = API_PREFIX + "/dept";
	String DEPT_IDS = API_PREFIX + "/dept-ids";
	String DEPT_IDS_FUZZY = API_PREFIX + "/dept-ids-fuzzy";
	String DEPT_NAME = API_PREFIX + "/dept-name";
	String DEPT_NAMES = API_PREFIX + "/dept-names";
	String DEPT_CHILD = API_PREFIX + "/dept-child";
	String POST = API_PREFIX + "/post";
	String POST_IDS = API_PREFIX + "/post-ids";
	String POST_IDS_FUZZY = API_PREFIX + "/post-ids-fuzzy";
	String POST_NAME = API_PREFIX + "/post-name";
	String POST_NAMES = API_PREFIX + "/post-names";
	String ROLE = API_PREFIX + "/role";
	String ROLE_IDS = API_PREFIX + "/role-ids";
	String ROLE_NAME = API_PREFIX + "/role-name";
	String ROLE_NAMES = API_PREFIX + "/role-names";
	String ROLE_ALIAS = API_PREFIX + "/role-alias";
	String ROLE_ALIASES = API_PREFIX + "/role-aliases";
	String TENANT = API_PREFIX + "/tenant";
	String TENANT_ID = API_PREFIX + "/tenant-id";
	String PARAM = API_PREFIX + "/param";
	String PARAM_VALUE = API_PREFIX + "/param-value";
	String REGION = API_PREFIX + "/region";

	/**
	 * 获取菜单
	 *
	 * @param id 主键
	 * @return Menu
	 */
	@GetMapping(MENU)
	Result<Menu> getMenu(@RequestParam("id") Long id);

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(DEPT)
	Result<Dept> getDept(@RequestParam("id") Long id);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(DEPT_IDS)
	Result<String> getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(DEPT_IDS_FUZZY)
	Result<String> getDeptIdsByFuzzy(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	@GetMapping(DEPT_NAME)
	Result<String> getDeptName(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 主键
	 * @return 数据
	 */
	@GetMapping(DEPT_NAMES)
	Result<List<String>> getDeptNames(@RequestParam("deptIds") String deptIds);

	/**
	 * 获取子部门ID
	 *
	 * @param deptId 数据
	 * @return 数据
	 */
	@GetMapping(DEPT_CHILD)
	Result<List<Dept>> getDeptChild(@RequestParam("deptId") Long deptId);

	/**
	 * 获取岗位
	 *
	 * @param id 主键
	 * @return Post
	 */
	@GetMapping(POST)
	Result<Post> getPost(@RequestParam("id") Long id);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(POST_IDS)
	Result<String> getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(POST_IDS_FUZZY)
	Result<String> getPostIdsByFuzzy(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param id 主键
	 * @return 岗位名
	 */
	@GetMapping(POST_NAME)
	Result<String> getPostName(@RequestParam("id") Long id);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 主键
	 * @return 数据
	 */
	@GetMapping(POST_NAMES)
	Result<List<String>> getPostNames(@RequestParam("postIds") String postIds);

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	@GetMapping(ROLE)
	Result<Role> getRole(@RequestParam("id") Long id);

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return 角色id
	 */
	@GetMapping(ROLE_IDS)
	Result<String> getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	@GetMapping(ROLE_NAME)
	Result<String> getRoleName(@RequestParam("id") Long id);

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIAS)
	Result<String> getRoleAlias(@RequestParam("id") Long id);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 主键
	 * @return 数据
	 */
	@GetMapping(ROLE_NAMES)
	Result<List<String>> getRoleNames(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param roleIds 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIASES)
	Result<List<String>> getRoleAliases(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	@GetMapping(TENANT)
	Result<Tenant> getTenant(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(TENANT_ID)
	Result<Tenant> getTenant(@RequestParam("tenantId") String tenantId);

	/**
	 * 获取参数
	 *
	 * @param id 主键
	 * @return Param
	 */
	@GetMapping(PARAM)
	Result<Param> getParam(@RequestParam("id") Long id);

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	@GetMapping(PARAM_VALUE)
	Result<String> getParamValue(@RequestParam("paramKey") String paramKey);

	/**
	 * 获取行政区划
	 *
	 * @param code 主键
	 * @return Region
	 */
	@GetMapping(REGION)
	Result<Region> getRegion(@RequestParam("code") String code);

}
