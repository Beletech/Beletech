package com.beletech.payment.handler.impl;

import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.mapper.PayChannelMapper;
import com.beletech.payment.mapper.PayTradeOrderMapper;
import com.beletech.payment.mapper.PlatformSchemeOrderMapper;
import com.beletech.payment.utils.ChannelPayApiConfigKit;
import com.beletech.payment.utils.OrderStatusEnum;
import com.beletech.payment.utils.PayChannelNameEnum;
import com.beletech.sequence.sequence.Sequence;
import com.yungouos.pay.merge.MergePay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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

	private final PlatformSchemeOrderMapper platformSchemeOrderMapper;

	private final PayChannelMapper channelMapper;

	private final HttpServletRequest request;

	private final Sequence paySequence;

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
	public PayTradeOrder createTradeOrder(PlatformSchemeOrder platformSchemeOrder) {
		PayTradeOrder tradeOrder = new PayTradeOrder();
		String serialNumber = paySequence.nextNo();
		platformSchemeOrder.setSerialNumber(serialNumber);
		tradeOrder.setOrderId(serialNumber);
		tradeOrder.setAmount(platformSchemeOrder.getAmount());
		tradeOrder.setChannelId(PayChannelNameEnum.MERGE_PAY.getName());
		tradeOrder.setChannelMchId(ChannelPayApiConfigKit.get().getChannelMchId());
		tradeOrder.setClientIp(ServletUtil.getClientIP(request));
		tradeOrder.setCurrency("CNY");
		tradeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		tradeOrder.setBody(platformSchemeOrder.getPlatformName());
		tradeOrderMapper.insert(tradeOrder);
		return tradeOrder;
	}

	@Override
	public Object pay(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder) {
		PayChannel channel = ChannelPayApiConfigKit.get();
		String money = platformSchemeOrder.getAmount().divide(new BigDecimal(100)).setScale(2).toString();
		tradeOrder.setOrderId(paySequence.nextNo());
		return MergePay.nativePay(tradeOrder.getOrderId(), money, channel.getChannelMchId(), tradeOrder.getBody(), "1",
			AuthUtil.getTenantId(),
			ChannelPayApiConfigKit.get().getNotifyUrl() + "/beletech-payment/notify/merge/callbak",
			ChannelPayApiConfigKit.get().getReturnUrl(), "", "", "", channel.getParam());
	}

	@Override
	public void updateOrder(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder) {
		tradeOrderMapper.updateById(tradeOrder);
		platformSchemeOrderMapper.updateById(platformSchemeOrder);
	}
}
