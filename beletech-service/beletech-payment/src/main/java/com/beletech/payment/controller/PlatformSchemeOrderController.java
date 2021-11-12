package com.beletech.payment.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.service.PayChannelService;
import com.beletech.payment.service.PlatformSchemeOrderService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 平台费用记录控制器
 *
 * @author XueBing
 * @date 2021-11-10
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/platform-scheme-order")
@Api(value = "platform-scheme-order", tags = "平台费用记录")
public class PlatformSchemeOrderController {

	private final PayChannelService channelService;

	private final ObjectMapper objectMapper;

	private final PlatformSchemeOrderService platformSchemeOrderService;

	@SneakyThrows
	@GetMapping("/buy")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public void buy(PlatformSchemeOrder platformSchemeOrder, HttpServletRequest request, HttpServletResponse response) {
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
			String redirectUri = String.format("%s/api/beletech-payment/platform-scheme-order/wx?amount=%s&TENANT-ID=%s", channel.getNotifyUrl(),
				platformSchemeOrder.getAmount(), platformSchemeOrder.getTenantId());
			response.sendRedirect(
				String.format(wxUrl, channel.getAppId(), URLUtil.encode(redirectUri), channel.getAppId()));
		}
		if (ua.contains(PayConstants.ALIPAY)) {
			platformSchemeOrderService.buy(platformSchemeOrder, false);
		}
	}

	@SneakyThrows
	@GetMapping("/merge/buy")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public void mergeBuy(PlatformSchemeOrder platformSchemeOrder, HttpServletResponse response) {
		Map<String, Object> result = platformSchemeOrderService.buy(platformSchemeOrder, true);
		response.setContentType(ContentType.JSON.getValue());
		response.getWriter().print(objectMapper.writeValueAsString(result));
	}


	@SneakyThrows
	@GetMapping("/wx")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public ModelAndView wx(PlatformSchemeOrder platformSchemeOrder, String code, ModelAndView modelAndView) {
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
		platformSchemeOrder.setUserId(mpOAuth2AccessToken.getOpenId());
		modelAndView.setViewName("pay");
		modelAndView.addAllObjects(platformSchemeOrderService.buy(platformSchemeOrder, false));
		return modelAndView;
	}

}
