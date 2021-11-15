package com.beletech.payment.handler.impl;

import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformScheme;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.handler.PlatformSchemeHandler;
import com.beletech.payment.mapper.PlatformSchemeMapper;
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

	@Resource
	private PlatformSchemeMapper platformSchemeMapper;

	@Override
	public void createPlatformSchemeOrder(PlatformSchemeOrder platformSchemeOrder) {
		platformSchemeOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		// 获取平台方案信息
		PlatformScheme platformScheme = platformSchemeMapper.selectById(platformSchemeOrder.getPlatformId());
		platformSchemeOrder.setPlatformName(platformScheme.getName());
		platformSchemeOrder.setPrice(platformScheme.getPrice());
		platformSchemeOrder.setType(platformScheme.getType());
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
