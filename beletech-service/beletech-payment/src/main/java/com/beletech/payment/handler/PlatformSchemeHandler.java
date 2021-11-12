package com.beletech.payment.handler;

import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformSchemeOrder;

/**
 * 支付业务
 *
 * @author XueBing
 * @date 2021-10-31
 */
public interface PlatformSchemeHandler {

	/**
	 * 准备支付参数
	 */
	default PayChannel preparePayParams() {
		return null;
	}

	/**
	 * 创建平台支付记录
	 *
	 * @param platformSchemeOrder 订单记录
	 */
	void createPlatformSchemeOrder(PlatformSchemeOrder platformSchemeOrder);

	/**
	 * 创建交易订单
	 *
	 * @param platformSchemeOrder 订单记录
	 * @return 交易订单
	 */
	PayTradeOrder createTradeOrder(PlatformSchemeOrder platformSchemeOrder);

	/**
	 * 调起渠道支付
	 *
	 * @param platformSchemeOrder 订单记录
	 * @param tradeOrder          交易订单
	 * @return obj
	 */
	Object pay(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder);

	/**
	 * 更新订单信息
	 *
	 * @param platformSchemeOrder 交易订单
	 * @param tradeOrder          交易订单
	 */
	void updateOrder(PlatformSchemeOrder platformSchemeOrder, PayTradeOrder tradeOrder);

	/**
	 * 调用入口
	 *
	 * @param platformSchemeOrder 交易订单
	 * @return 调用入口
	 */
	Object handle(PlatformSchemeOrder platformSchemeOrder);

}
