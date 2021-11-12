package com.beletech.payment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.service.PayChannelService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 渠道
 *
 * @author XueBing
 * @date 2021-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pay-channel")
@Api(value = "收费渠道", tags = "收费渠道管理")
public class PayChannelController {

	private final PayChannelService payChannelService;

	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public Result<IPage<PayChannel>> getPayChannelPage(Query query, PayChannel payChannel) {
		return Result.data(payChannelService.page(Condition.getPage(query), Wrappers.query(payChannel)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "通过id查询渠道", notes = "通过id查询渠道")
	public Result<PayChannel> getById(@PathVariable("id") Integer id) {
		return Result.data(payChannelService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增渠道", notes = "新增渠道")
	public Result<Boolean> save(@RequestBody PayChannel payChannel) {
		return Result.data(payChannelService.saveChannel(payChannel));
	}

	@PutMapping
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改渠道", notes = "修改渠道")
	public Result<Boolean> updateById(@RequestBody PayChannel payChannel) {
		return Result.data(payChannelService.updateById(payChannel));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "通过id删除渠道", notes = "通过id删除渠道")
	public Result<Boolean> removeById(@PathVariable Integer id) {
		return Result.data(payChannelService.removeById(id));
	}
}
