package com.beletech.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.beletech.core.boot.ctrl.BeletechController;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.AuthClient;
import com.beletech.system.service.IAuthClientService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 *  应用管理控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@ApiIgnore
@Api(value = "应用管理", tags = "接口")
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class AuthClientController extends BeletechController {

	private final IAuthClientService clientService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入client")
	public Result<AuthClient> detail(AuthClient authClient) {
		AuthClient detail = clientService.getOne(Condition.getQueryWrapper(authClient));
		return Result.data(detail);
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入client")
	public Result<IPage<AuthClient>> list(AuthClient authClient, Query query) {
		IPage<AuthClient> pages = clientService.page(Condition.getPage(query), Condition.getQueryWrapper(authClient));
		return Result.data(pages);
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入client")
	public Result<Boolean> save(@Valid @RequestBody AuthClient authClient) {
		return Result.status(clientService.save(authClient));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入client")
	public Result<Boolean> update(@Valid @RequestBody AuthClient authClient) {
		return Result.status(clientService.updateById(authClient));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入client")
	public Result<Boolean> submit(@Valid @RequestBody AuthClient authClient) {
		return Result.status(clientService.saveOrUpdate(authClient));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public Result<Boolean> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return Result.status(clientService.deleteLogic(Func.toLongList(ids)));
	}
}
