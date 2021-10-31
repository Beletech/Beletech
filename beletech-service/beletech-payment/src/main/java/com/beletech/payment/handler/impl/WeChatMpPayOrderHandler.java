package com.beletech.payment.handler.impl;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.common.utils.TenantContextHolder;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.mapper.PayChannelMapper;
import com.beletech.payment.mapper.PayGoodsOrderMapper;
import com.beletech.payment.mapper.PayTradeOrderMapper;
import com.beletech.payment.utils.ChannelPayApiConfigKit;
import com.beletech.payment.utils.OrderStatusEnum;
import com.beletech.payment.utils.PayChannelNameEnum;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 微信公众号支付
 */
@Slf4j
@Service("WEIXIN_MP")
@RequiredArgsConstructor
public class WeChatMpPayOrderHandler extends AbstractPayOrderHandler {

	private final PayTradeOrderMapper tradeOrderMapper;

	private final PayGoodsOrderMapper goodsOrderMapper;

	private final PayChannelMapper channelMapper;

	private final HttpServletRequest request;

	@Override
	public PayChannel preparePayParams() {
		PayChannel channel = channelMapper.selectOne(
				Wrappers.<PayChannel>lambdaQuery().eq(PayChannel::getChannelId, PayChannelNameEnum.WEIXIN_MP.name()));

		if (channel == null) {
			throw new IllegalArgumentException("微信公众号支付渠道配置为空");
		}
		JSONObject params = JSONUtil.parseObj(channel.getParam());
		WxPayApiConfig wx = WxPayApiConfig.builder().appId(channel.getAppId()).mchId(channel.getChannelMchId())
				.partnerKey(params.getStr("partnerKey")).build();
		WxPayApiConfigKit.putApiConfig(wx);
		return channel;
	}

	@Override
	public PayTradeOrder createTradeOrder(PayGoodsOrder goodsOrder) {
		PayTradeOrder tradeOrder = new PayTradeOrder();
		tradeOrder.setOrderId(goodsOrder.getPayOrderId());
		tradeOrder.setAmount(goodsOrder.getAmount());
		tradeOrder.setChannelId(PayChannelNameEnum.WEIXIN_MP.getName());
		tradeOrder.setChannelMchId(WxPayApiConfigKit.getWxPayApiConfig().getMchId());
		tradeOrder.setClientIp(ServletUtil.getClientIP(request));
		tradeOrder.setCurrency("CNY");
		tradeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		tradeOrder.setBody(goodsOrder.getGoodsName());
		tradeOrderMapper.insert(tradeOrder);
		return tradeOrder;
	}

	@Override
	public Object pay(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder) {
		String ip = ServletUtil.getClientIP(request);
		WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
		// 预订单参数
		Map<String, String> params = UnifiedOrderModel.builder().appid(wxPayApiConfig.getAppId())
				.mch_id(wxPayApiConfig.getMchId()).nonce_str(WxPayKit.generateStr()).body(goodsOrder.getGoodsName())
				.attach("000000").out_trade_no(tradeOrder.getOrderId())
				.total_fee(goodsOrder.getAmount()).spbill_create_ip(ip)
				.notify_url(ChannelPayApiConfigKit.get().getNotifyUrl() + "/beletech-payment/notify/wx/callbak")
				.trade_type(TradeType.JSAPI.getTradeType()).openid(goodsOrder.getUserId()).build()
				.createSign(wxPayApiConfig.getPartnerKey(), SignType.HMACSHA256);

		String xmlResult = WxPayApi.pushOrder(false, params);
		log.info("微信统一下单返回 {}", xmlResult);
		Map<String, String> resultMap = WxPayKit.xmlToMap(xmlResult);
		String prepayId = resultMap.get("prepay_id");
		return WxPayKit.prepayIdCreateSign(prepayId, wxPayApiConfig.getAppId(), wxPayApiConfig.getPartnerKey(),
				SignType.HMACSHA256);
	}

	@Override
	public void updateOrder(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder) {
		tradeOrderMapper.updateById(tradeOrder);
		goodsOrderMapper.updateById(goodsOrder);
	}
}
