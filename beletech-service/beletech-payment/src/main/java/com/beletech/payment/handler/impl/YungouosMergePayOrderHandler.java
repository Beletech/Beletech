package com.beletech.payment.handler.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.servlet.ServletUtil;
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
import com.yungouos.pay.merge.MergePay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * https://open.pay.yungouos.com/#/api/api/pay/merge/nativePay 服务商聚合支付模式
 */
@Slf4j
@Service("MERGE_PAY")
@RequiredArgsConstructor
public class YungouosMergePayOrderHandler extends AbstractPayOrderHandler {

	private final PayTradeOrderMapper tradeOrderMapper;

	private final PayGoodsOrderMapper goodsOrderMapper;

	private final PayChannelMapper channelMapper;

	private final HttpServletRequest request;

	@Override
	public PayChannel preparePayParams() {
		PayChannel channel = channelMapper.selectOne(
			Wrappers.<PayChannel>lambdaQuery().eq(PayChannel::getChannelId, PayChannelNameEnum.MERGE_PAY.name()));
		if (channel == null) {
			throw new IllegalArgumentException("聚合支付渠道配置为空");
		}
		return channel;
	}

	@Override
	public PayTradeOrder createTradeOrder(PayGoodsOrder goodsOrder) {
		PayTradeOrder tradeOrder = new PayTradeOrder();
		tradeOrder.setOrderId(goodsOrder.getPayOrderId());
		tradeOrder.setAmount(goodsOrder.getAmount());
		tradeOrder.setChannelId(PayChannelNameEnum.MERGE_PAY.getName());
		tradeOrder.setChannelMchId(ChannelPayApiConfigKit.get().getChannelMchId());
		tradeOrder.setClientIp(ServletUtil.getClientIP(request));
		tradeOrder.setCurrency("CNY");
		tradeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		tradeOrder.setBody(goodsOrder.getGoodsName());
		tradeOrderMapper.insert(tradeOrder);
		return tradeOrder;
	}

	@Override
	public Object pay(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder) {
		PayChannel channel = ChannelPayApiConfigKit.get();

		String money = NumberUtil.div(tradeOrder.getAmount(), "100", 2).toString();

		return MergePay.nativePay(tradeOrder.getOrderId(), money, channel.getChannelMchId(), tradeOrder.getBody(), "1",
			TenantContextHolder.getTenantId().toString(),
			ChannelPayApiConfigKit.get().getNotifyUrl() + "/pay/notify/merge/callbak",
			ChannelPayApiConfigKit.get().getReturnUrl(), "", "", "", channel.getParam());
	}

	@Override
	public void updateOrder(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder) {
		tradeOrderMapper.updateById(tradeOrder);
		goodsOrderMapper.updateById(goodsOrder);
	}
}
