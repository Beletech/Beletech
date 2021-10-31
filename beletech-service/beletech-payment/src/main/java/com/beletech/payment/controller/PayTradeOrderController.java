package com.beletech.payment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.service.PayTradeOrderService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 支付
 *
 * @author XueBing
 * @date 2021-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Api(value = "order", tags = "订单")
public class PayTradeOrderController {

	private final PayTradeOrderService payTradeOrderService;

	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public Result<IPage<PayTradeOrder>> getPayTradeOrderPage(Query query, PayTradeOrder payTradeOrder) {
		return Result.data(payTradeOrderService.page(Condition.getPage(query), Wrappers.query(payTradeOrder)));
	}

	@GetMapping("/{orderId}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "通过id查询支付", notes = "通过id查询支付")
	public Result<PayTradeOrder> getById(@PathVariable("orderId") String orderId) {
		return Result.data(payTradeOrderService.getById(orderId));
	}

	@PostMapping
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增支付", notes = "新增支付")
	public Result<Boolean> save(@RequestBody PayTradeOrder payTradeOrder) {
		return Result.data(payTradeOrderService.save(payTradeOrder));
	}

	@PutMapping
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改支付", notes = "修改支付")
	public Result<Boolean> updateById(@RequestBody PayTradeOrder payTradeOrder) {
		return Result.data(payTradeOrderService.updateById(payTradeOrder));
	}

	@DeleteMapping("/{orderId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "通过id删除支付", notes = "通过id删除支付")
	public Result<Boolean> removeById(@PathVariable String orderId) {
		return Result.data(payTradeOrderService.removeById(orderId));
	}
}
