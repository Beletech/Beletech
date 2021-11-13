package com.beletech.payment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayNotifyRecord;
import com.beletech.core.mp.support.Condition;
import com.beletech.payment.handler.PayNotifyCallbakHandler;
import com.beletech.payment.service.PayNotifyRecordService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异步通知记录
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/notify")
@Api(value = "notify", tags = "notify管理")
public class PayNotifyRecordController {

	private final PayNotifyRecordService payNotifyRecordService;

	private final PayNotifyCallbakHandler alipayCallback;

	private final PayNotifyCallbakHandler weChatCallback;

	private final PayNotifyCallbakHandler mergePayCallback;

	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public Result<IPage<PayNotifyRecord>> getPayNotifyRecordPage(Query query, PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.page(Condition.getPage(query), Wrappers.query(payNotifyRecord)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "通过id查询异步通知记录", notes = "通过id查询异步通知记录")
	public Result<PayNotifyRecord> getById(@PathVariable("id") Long id) {
		return Result.data(payNotifyRecordService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增异步通知记录", notes = "新增异步通知记录")
	public Result<Boolean> save(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.save(payNotifyRecord));
	}

	@PutMapping
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改异步通知记录", notes = "修改异步通知记录")
	public Result<Boolean> updateById(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.updateById(payNotifyRecord));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "通过id删除异步通知记录", notes = "通过id删除异步通知记录")
	public Result<Boolean> removeById(@PathVariable Long id) {
		return Result.data(payNotifyRecordService.removeById(id));
	}

	@SneakyThrows
	@PostMapping("/ali/callbak")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "支付宝渠道异步回调", notes = "支付宝渠道异步回调")
	public void aliCallbak(HttpServletRequest request, HttpServletResponse response) {
		// 解析回调信息
		Map<String, String> params = AliPayApi.toMap(request);
		response.getWriter().print(alipayCallback.handle(params));
	}

	@SneakyThrows
	@ResponseBody
	@PostMapping("/wx/callbak")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "微信渠道支付回调", notes = "微信渠道支付回调")
	public String wxCallbak(HttpServletRequest request) {
		String xmlMsg = HttpKit.readData(request);
		log.info("微信订单回调信息:{}", xmlMsg);
		Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);
		return weChatCallback.handle(params);
	}

	@SneakyThrows
	@PostMapping("/merge/callbak")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "聚合渠道异步回调", notes = "聚合渠道异步回调")
	public void mergeCallbak(HttpServletRequest request, HttpServletResponse response) {
		// 解析回调信息
		Map<String, String> params = AliPayApi.toMap(request);
		response.getWriter().print(mergePayCallback.handle(params));
	}
}
