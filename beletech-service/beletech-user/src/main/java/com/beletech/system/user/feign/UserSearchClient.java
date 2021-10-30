package com.beletech.system.user.feign;

import lombok.AllArgsConstructor;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.user.entity.User;
import com.beletech.system.user.service.IUserSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户查询服务Feign实现类
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
public class UserSearchClient implements IUserSearchClient {

	private final IUserSearchService service;

	@Override
	@GetMapping(LIST_BY_USER)
	public Result<List<User>> listByUser(String userId) {
		return Result.data(service.listByUser(Func.toLongList(userId)));
	}

	@Override
	@GetMapping(LIST_BY_DEPT)
	public Result<List<User>> listByDept(String deptId) {
		return Result.data(service.listByDept(Func.toLongList(deptId)));
	}

	@Override
	@GetMapping(LIST_BY_POST)
	public Result<List<User>> listByPost(String postId) {
		return Result.data(service.listByPost(Func.toLongList(postId)));
	}

	@Override
	@GetMapping(LIST_BY_ROLE)
	public Result<List<User>> listByRole(String roleId) {
		return Result.data(service.listByRole(Func.toLongList(roleId)));
	}
}
