package com.beletech.payment.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayNotifyRecord;
import com.beletech.payment.handler.PayNotifyCallbakHandler;
import com.beletech.payment.service.PayNotifyRecordService;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import io.swagger.annotations.Api;
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

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param payNotifyRecord 异步通知记录
	 * @return
	 */
	@GetMapping("/page")
	public Result getPayNotifyRecordPage(Page page, PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.page(page, Wrappers.query(payNotifyRecord)));
	}

	/**
	 * 通过id查询异步通知记录
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public Result getById(@PathVariable("id") Long id) {
		return Result.data(payNotifyRecordService.getById(id));
	}

	/**
	 * 新增异步通知记录
	 *
	 * @param payNotifyRecord 异步通知记录
	 * @return R
	 */
	@PostMapping
	public Result save(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.save(payNotifyRecord));
	}

	/**
	 * 修改异步通知记录
	 *
	 * @param payNotifyRecord 异步通知记录
	 * @return R
	 */
	@PutMapping
	public Result updateById(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.data(payNotifyRecordService.updateById(payNotifyRecord));
	}

	/**
	 * 通过id删除异步通知记录
	 *
	 * @param id id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public Result removeById(@PathVariable Long id) {
		return Result.data(payNotifyRecordService.removeById(id));
	}

	/**
	 * 支付宝渠道异步回调
	 *
	 * @param request 渠道请求
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/ali/callbak")
	public void aliCallbak(HttpServletRequest request, HttpServletResponse response) {
		// 解析回调信息
		Map<String, String> params = AliPayApi.toMap(request);
		response.getWriter().print(alipayCallback.handle(params));
	}

	/**
	 * 微信渠道支付回调
	 *
	 * @param request
	 * @return
	 */
	@SneakyThrows
	@ResponseBody
	@PostMapping("/wx/callbak")
	public String wxCallbak(HttpServletRequest request) {
		String xmlMsg = HttpKit.readData(request);
		log.info("微信订单回调信息:{}", xmlMsg);
		Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);
		return weChatCallback.handle(params);
	}

	/**
	 * 聚合渠道异步回调
	 *
	 * @param request 渠道请求
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/merge/callbak")
	public void mergeCallbak(HttpServletRequest request, HttpServletResponse response) {
		// 解析回调信息
		Map<String, String> params = AliPayApi.toMap(request);
		response.getWriter().print(mergePayCallback.handle(params));
	}

}
