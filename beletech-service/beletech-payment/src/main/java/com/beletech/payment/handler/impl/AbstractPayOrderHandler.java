package com.beletech.payment.handler.impl;


import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.handler.PayOrderHandler;
import com.beletech.payment.mapper.PayGoodsOrderMapper;
import com.beletech.payment.utils.ChannelPayApiConfigKit;
import com.beletech.payment.utils.OrderStatusEnum;

import javax.annotation.Resource;

/**
 * @author XueBing
 * @date 2021-10-31
 */
public abstract class AbstractPayOrderHandler implements PayOrderHandler {

	@Resource
	private PayGoodsOrderMapper goodsOrderMapper;

	@Override
	public void createGoodsOrder(PayGoodsOrder goodsOrder) {
		goodsOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		goodsOrderMapper.insert(goodsOrder);
	}

	@Override
	public Object handle(PayGoodsOrder payGoodsOrder) {
		PayChannel payChannel = preparePayParams();
		ChannelPayApiConfigKit.put(payChannel);
		createGoodsOrder(payGoodsOrder);
		PayTradeOrder tradeOrder = createTradeOrder(payGoodsOrder);
		Object result = pay(payGoodsOrder, tradeOrder);
		updateOrder(payGoodsOrder, tradeOrder);
		// 情况ttl
		ChannelPayApiConfigKit.remove();
		return result;
	}
}
