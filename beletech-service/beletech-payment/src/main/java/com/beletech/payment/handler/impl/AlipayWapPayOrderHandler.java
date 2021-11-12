package com.beletech.payment.handler.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.common.utils.TenantContextHolder;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.mapper.PayChannelMapper;
import com.beletech.payment.mapper.PayTradeOrderMapper;
import com.beletech.payment.mapper.PlatformSchemeOrderMapper;
import com.beletech.payment.utils.ChannelPayApiConfigKit;
import com.beletech.payment.utils.OrderStatusEnum;
import com.beletech.payment.utils.PayChannelNameEnum;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 支付宝手机支付
 */
@Slf4j
@Service("ALIPAY_WAP")
@AllArgsConstructor
public class AlipayWapPayOrderHandler extends AbstractPayOrderHandler {

	private PayTradeOrderMapper tradeOrderMapper;

	private PlatformSchemeOrderMapper platformSchemeOrderMapper;

	private PayChannelMapper channelMapper;

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Override
	public PayChannel preparePayParams() {
		PayChannel channel = channelMapper.selectOne(
			Wrappers.<PayChannel>lambdaQuery().eq(PayChannel::getChannelId, PayChannelNameEnum.ALIPAY_WAP.name()));

		if (channel == null) {
			throw new IllegalArgumentException("支付宝网页支付渠道配置为空");
		}

		JSONObject params = JSONUtil.parseObj(channel.getParam());
		AliPayApiConfig aliPayApiConfig = AliPayApiConfig.builder().setAppId(channel.getAppId())
			.setPrivateKey(params.getStr("privateKey")).setCharset(CharsetUtil.UTF_8)
			.setAliPayPublicKey(params.getStr("publicKey")).setServiceUrl(params.getStr("serviceUrl"))
			.setSignType("RSA2").build();
		AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
		return channel;
	}

	@Override
	public PayTradeOrder createTradeOrder(PlatformSchemeOrder platformSchemeOrder) {
		PayTradeOrder tradeOrder = new PayTradeOrder();
		tradeOrder.setOrderId(platformSchemeOrder.getSerialNumber());
		tradeOrder.setAmount(platformSchemeOrder.getAmount());
		tradeOrder.setChannelId(PayChannelNameEnum.ALIPAY_WAP.getName());
		tradeOrder.setChannelMchId(AliPayApiConfigKit.getAliPayApiConfig().getAppId());
		tradeOrder.setClientIp(ServletUtil.getClientIP(request));
		tradeOrder.setCurrency("cny");
		tradeOrder.setExpireTime(30L);
		tradeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		tradeOrder.setBody(platformSchemeOrder.getPlatformName());
		tradeOrderMapper.insert(tradeOrder);
		return tradeOrder;
	}

	@Override
	public PayTradeOrder pay(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder) {
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody(tradeOrder.getBody());
		model.setSubject(tradeOrder.getBody());
		model.setOutTradeNo(tradeOrder.getOrderId());
		model.setTimeoutExpress("30m");
		// 分转成元 并且保留两位
		BigDecimal totalAmount = tradeOrder.getAmount().divide(new BigDecimal(100)).setScale(2);
		model.setTotalAmount(totalAmount.toString());
		model.setProductCode(platformSchemeOrder.getPlatformId().toString());
		model.setPassbackParams(String.valueOf(TenantContextHolder.getTenantId()));
		try {
			log.info("拉起支付宝wap 支付参数 {}", model);
			AliPayApi.wapPay(response, model, ChannelPayApiConfigKit.get().getReturnUrl(),
				ChannelPayApiConfigKit.get().getNotifyUrl() + "/beletech-payment/notify/ali/callbak");
		} catch (AlipayApiException e) {
			log.error("支付宝手机支付失败", e);
			tradeOrder.setErrMsg(e.getErrMsg());
			tradeOrder.setErrCode(e.getErrCode());
			tradeOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
			platformSchemeOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
		} catch (IOException e) {
			log.error("支付宝手机支付失败", e);
			tradeOrder.setErrMsg(e.getMessage());
			tradeOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
			platformSchemeOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
		}
		return tradeOrder;
	}

	@Override
	public void updateOrder(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder) {
		tradeOrderMapper.updateById(tradeOrder);
		platformSchemeOrderMapper.updateById(platformSchemeOrder);
	}
}
