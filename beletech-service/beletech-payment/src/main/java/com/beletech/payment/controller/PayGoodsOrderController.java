package com.beletech.payment.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.common.utils.TenantContextHolder;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.api.Result;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.service.PayChannelService;
import com.beletech.payment.service.PayGoodsOrderService;
import com.beletech.payment.utils.PayChannelNameEnum;
import com.beletech.payment.utils.PayConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商品
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
@Controller
@AllArgsConstructor
@SuppressWarnings("all")
@RequestMapping("/goods")
@Api(value = "goods", tags = "商品订单管理")
public class PayGoodsOrderController {

	private final PayGoodsOrderService payGoodsOrderService;

	private final PayChannelService channelService;

	private final ObjectMapper objectMapper;

	@SneakyThrows
	@GetMapping("/buy")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public void buy(PayGoodsOrder goods, HttpServletRequest request, HttpServletResponse response) {
		String ua = request.getHeader(HttpHeaders.USER_AGENT);
		log.info("当前扫码方式 UA:{}", ua);
		if (ua.contains(PayConstants.MICRO_MESSENGER)) {
			PayChannel channel = channelService.getOne(
				Wrappers.<PayChannel>lambdaQuery().eq(PayChannel::getChannelId, PayChannelNameEnum.WEIXIN_MP),
				false);
			if (channel == null) {
				throw new IllegalArgumentException("公众号支付配置不存在");
			}
			String wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s"
				+ "&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s";
			String redirectUri = String.format("%s/beletech-payment/goods/wx?amount=%s&TENANT-ID=%s", channel.getNotifyUrl(),
				goods.getAmount(), goods.getTenantId());
			response.sendRedirect(
				String.format(wxUrl, channel.getAppId(), URLUtil.encode(redirectUri), channel.getAppId()));
		}
		if (ua.contains(PayConstants.ALIPAY)) {
			payGoodsOrderService.buy(goods, false);
		}
	}

	@SneakyThrows
	@GetMapping("/merge/buy")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public void mergeBuy(PayGoodsOrder goods, HttpServletResponse response) {
		Map<String, Object> result = payGoodsOrderService.buy(goods, true);
		response.setContentType(ContentType.JSON.getValue());
		response.getWriter().print(objectMapper.writeValueAsString(result));
	}

	@SneakyThrows
	@GetMapping("/wx")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public ModelAndView wx(PayGoodsOrder goods, String code, ModelAndView modelAndView) {
		PayChannel channel = channelService.getOne(
			Wrappers.<PayChannel>lambdaQuery().eq(PayChannel::getChannelId, PayChannelNameEnum.WEIXIN_MP), false);
		if (channel == null) {
			throw new IllegalArgumentException("公众号支付配置不存在");
		}
		JSONObject params = JSONUtil.parseObj(channel.getParam());
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpDefaultConfigImpl storage = new WxMpDefaultConfigImpl();
		storage.setAppId(channel.getAppId());
		storage.setSecret(params.getStr("secret"));
		wxMpService.setWxMpConfigStorage(storage);
		WxMpOAuth2AccessToken mpOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
		goods.setUserId(mpOAuth2AccessToken.getOpenId());
		goods.setAmount(goods.getAmount());
		modelAndView.setViewName("pay");
		modelAndView.addAllObjects(payGoodsOrderService.buy(goods, false));
		return modelAndView;
	}

	@ResponseBody
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public Result<IPage<PayGoodsOrder>> getPayGoodsOrderPage(Query query, PayGoodsOrder payGoodsOrder) {
		return Result.data(payGoodsOrderService.page(Condition.getPage(query), Wrappers.query(payGoodsOrder)));
	}

	@ResponseBody
	@ApiOperationSupport(order = 4)
	@GetMapping(value = "/{goodsOrderId}")
	@ApiOperation(value = "通过id查询商品订单表", notes = "通过id查询商品订单表")
	public Result<PayGoodsOrder> getById(@PathVariable("goodsOrderId") Integer goodsOrderId) {
		return Result.data(payGoodsOrderService.getById(goodsOrderId));
	}

	@PostMapping
	@ResponseBody
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增商品订单表", notes = "新增商品订单表")
	public Result<Boolean> save(@RequestBody PayGoodsOrder payGoodsOrder) {
		return Result.data(payGoodsOrderService.save(payGoodsOrder));
	}

	@PutMapping
	@ResponseBody
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改商品订单表", notes = "修改商品订单表")
	public Result<Boolean> updateById(@RequestBody PayGoodsOrder payGoodsOrder) {
		return Result.data(payGoodsOrderService.updateById(payGoodsOrder));
	}

	@ResponseBody
	@ApiOperationSupport(order = 6)
	@DeleteMapping("/{goodsOrderId}")
	@ApiOperation(value = "通过id删除商品订单表", notes = "通过id删除商品订单表")
	public Result<Boolean> removeById(@PathVariable Integer goodsOrderId) {
		return Result.data(payGoodsOrderService.removeById(goodsOrderId));
	}
}
