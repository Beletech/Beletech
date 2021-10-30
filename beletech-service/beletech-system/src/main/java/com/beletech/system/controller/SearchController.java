package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.Post;
import com.beletech.system.service.IDeptService;
import com.beletech.system.service.IPostService;
import com.beletech.system.service.IRoleService;
import com.beletech.system.vo.DeptVO;
import com.beletech.system.vo.PostVO;
import com.beletech.system.vo.RoleVO;
import com.beletech.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/search")
@Api(value = "查询", tags = "查询")
public class SearchController {

	private final IRoleService roleService;

	private final IDeptService deptService;

	private final IPostService postService;

	@GetMapping("/role")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "角色信息查询", notes = "传入roleName或者parentId")
	public Result<List<RoleVO>> roleSearch(String roleName, Long parentId) {
		return Result.data(roleService.search(roleName, parentId));
	}

	@GetMapping("/dept")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "部门信息查询", notes = "传入deptName或者parentId")
	public Result<List<DeptVO>> deptSearch(String deptName, Long parentId) {
		return Result.data(deptService.search(deptName, parentId));
	}

	@GetMapping("/post")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "岗位信息查询", notes = "传入postName")
	public Result<IPage<PostVO>> postSearch(String postName, Query query) {
		LambdaQueryWrapper<Post> queryWrapper = Wrappers.<Post>query().lambda();
		if (Func.isNotBlank(postName)) {
			queryWrapper.like(Post::getPostName, postName);
		}
		IPage<Post> pages = postService.page(Condition.getPage(query), queryWrapper);
		return Result.data(PostWrapper.build().pageVO(pages));
	}
}
