package com.beletech.system.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.excel.util.ExcelUtil;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.secure.BeletechUser;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.BeletechConstant;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.utils.DateUtil;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.excel.UserExcel;
import com.beletech.system.user.excel.UserImporter;
import com.beletech.system.user.service.IUserService;
import com.beletech.system.user.vo.UserVO;
import com.beletech.system.user.wrapper.UserWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.USER_CACHE;

/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

	private final IUserService userService;

	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		return Result.data(UserWrapper.build().entityVO(detail));
	}

	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/info")
	public Result<UserVO> info(BeletechUser user) {
		User detail = userService.getById(user.getUserId());
		return Result.data(UserWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query, BeletechUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		IPage<User> pages = userService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return Result.data(UserWrapper.build().pageVO(pages));
	}

	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<IPage<UserVO>> page(@ApiIgnore User user, Query query, Long deptId, BeletechUser bladeUser) {
		IPage<User> pages = userService.selectUserPage(Condition.getPage(query), user, deptId, (bladeUser.getTenantId().equals(BeletechConstant.ADMIN_TENANT_ID) ? StringPool.EMPTY : bladeUser.getTenantId()));
		return Result.data(UserWrapper.build().pageVO(pages));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入User")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<Boolean> submit(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return Result.status(userService.submit(user));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入User")
	public Result<Boolean> update(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return Result.status(userService.updateUser(user));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除", notes = "传入id集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<Boolean> remove(@RequestParam String ids) {
		CacheUtil.clear(USER_CACHE);
		return Result.status(userService.removeUser(ids));
	}

	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<Boolean> grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
				   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return Result.status(temp);
	}

	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<Boolean> resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return Result.status(temp);
	}

	@PostMapping("/update-password")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "修改密码", notes = "传入密码")
	public Result<Boolean> updatePassword(BeletechUser user, @ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return Result.status(temp);
	}

	@PostMapping("/update-info")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "修改基本信息", notes = "传入User")
	public Result<Boolean> updateInfo(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return Result.status(userService.updateUserInfo(user));
	}

	@GetMapping("/user-list")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "用户列表", notes = "传入user")
	public Result<List<User>> userList(User user, BeletechUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user);
		List<User> list = userService.list((!AuthUtil.isAdministrator()) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return Result.data(list);
	}

	@PostMapping("import-user")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "导入用户", notes = "传入excel")
	public Result<Boolean> importUser(MultipartFile file, Integer isCovered) {
		UserImporter userImporter = new UserImporter(userService, isCovered == 1);
		ExcelUtil.save(file, userImporter, UserExcel.class);
		return Result.success("操作成功");
	}

	@GetMapping("export-user")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "导出用户", notes = "传入user")
	public void exportUser(@ApiIgnore @RequestParam Map<String, Object> user, BeletechUser bladeUser, HttpServletResponse response) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!AuthUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
		}
		queryWrapper.lambda().eq(User::getIsDeleted, BeletechConstant.DB_NOT_DELETED);
		List<UserExcel> list = userService.exportUser(queryWrapper);
		ExcelUtil.export(response, "用户数据" + DateUtil.time(), "用户数据表", list, UserExcel.class);
	}

	@GetMapping("export-template")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<UserExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "用户数据模板", "用户数据表", list, UserExcel.class);
	}

	@PostMapping("/register-guest")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "第三方注册用户", notes = "传入user")
	public Result<Boolean> registerGuest(User user, Long oauthId) {
		return Result.status(userService.registerGuest(user, oauthId));
	}

	@PostMapping("/update-platform")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "配置用户平台信息", notes = "传入user")
	public Result<Boolean> updatePlatform(Long userId, Integer userType, String userExt) {
		return Result.status(userService.updatePlatform(userId, userType, userExt));
	}

	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "查看平台详情", notes = "传入id")
	@GetMapping("/platform-detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result<UserVO> platformDetail(User user) {
		return Result.data(userService.platformDetail(user));
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "人员姓名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "postName", value = "职位名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "current", value = "当前页数", paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "size", value = "每页数量", paramType = "query", dataType = "int")
	})
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "用户列表查询", notes = "用户列表查询")
	@GetMapping("/search/user")
	public Result<IPage<UserVO>> userSearch(@ApiIgnore UserVO user, @ApiIgnore Query query) {
		return Result.data(userService.selectUserSearch(user, query));
	}

}
