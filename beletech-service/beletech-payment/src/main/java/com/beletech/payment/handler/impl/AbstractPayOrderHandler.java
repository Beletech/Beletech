package com.beletech.payment.handler.impl;

import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.handler.PlatformSchemeHandler;
import com.beletech.payment.mapper.PlatformSchemeOrderMapper;
import com.beletech.payment.utils.ChannelPayApiConfigKit;
import com.beletech.payment.utils.OrderStatusEnum;

import javax.annotation.Resource;

/**
 * @author XueBing
 * @date 2021-10-31
 */
public abstract class AbstractPayOrderHandler implements PlatformSchemeHandler {

	@Resource
	private PlatformSchemeOrderMapper platformSchemeOrderMapper;

	@Override
	public void createPlatformSchemeOrder(PlatformSchemeOrder platformSchemeOrder) {
		platformSchemeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		platformSchemeOrderMapper.insert(platformSchemeOrder);
	}

	@Override
	public Object handle(PlatformSchemeOrder platformSchemeOrder) {
		PayChannel payChannel = preparePayParams();
		ChannelPayApiConfigKit.put(payChannel);
		createPlatformSchemeOrder(platformSchemeOrder);
		PayTradeOrder tradeOrder = createTradeOrder(platformSchemeOrder);
		Object result = pay(platformSchemeOrder, tradeOrder);
		updateOrder(platformSchemeOrder, tradeOrder);
		// 情况ttl
		ChannelPayApiConfigKit.remove();
		return result;
	}
}
