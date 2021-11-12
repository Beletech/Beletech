package com.beletech.payment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayRefundOrder;
import com.beletech.payment.service.PayRefundOrderService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 退款
 *
 * @author XueBing
 * @date 2021-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pay-refund-order")
@Api(value = "pay-refund-order", tags = "pay-refund-order管理")
public class PayRefundOrderController {

	private final PayRefundOrderService payRefundOrderService;

	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public Result<IPage<PayRefundOrder>> getPayRefundOrderPage(Query query, PayRefundOrder payRefundOrder) {
		return Result.data(payRefundOrderService.page(Condition.getPage(query), Wrappers.query(payRefundOrder)));
	}

	@GetMapping("/{refundOrderId}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "通过id查询退款", notes = "通过id查询退款")
	public Result<PayRefundOrder> getById(@PathVariable("refundOrderId") String refundOrderId) {
		return Result.data(payRefundOrderService.getById(refundOrderId));
	}

	@PostMapping
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增退款", notes = "新增退款")
	public Result<Boolean> save(@RequestBody PayRefundOrder payRefundOrder) {
		return Result.data(payRefundOrderService.save(payRefundOrder));
	}

	@PutMapping
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改退款", notes = "修改退款")
	public Result<Boolean> updateById(@RequestBody PayRefundOrder payRefundOrder) {
		return Result.data(payRefundOrderService.updateById(payRefundOrder));
	}

	@DeleteMapping("/{refundOrderId}")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "通过id删除退款", notes = "通过id删除退款")
	public Result<Boolean> removeById(@PathVariable String refundOrderId) {
		return Result.data(payRefundOrderService.removeById(refundOrderId));
	}
}
