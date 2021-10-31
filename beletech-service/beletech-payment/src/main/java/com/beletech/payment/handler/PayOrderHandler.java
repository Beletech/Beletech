package com.beletech.payment.handler;

import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.entity.PayTradeOrder;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 支付业务
 */
public interface PayOrderHandler {

	/**
	 * 准备支付参数
	 */
	default PayChannel preparePayParams() {
		return null;
	}

	/**
	 * 创建商品订单
	 *
	 * @param goodsOrder 金额
	 */
	void createGoodsOrder(PayGoodsOrder goodsOrder);

	/**
	 * 创建交易订单
	 *
	 * @param goodsOrder 商品订单
	 * @return 交易订单
	 */
	PayTradeOrder createTradeOrder(PayGoodsOrder goodsOrder);

	/**
	 * 调起渠道支付
	 *
	 * @param goodsOrder 商品订单
	 * @param tradeOrder 交易订单
	 * @return obj
	 */
	Object pay(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder);

	/**
	 * 更新订单信息
	 *
	 * @param goodsOrder 商品订单
	 * @param tradeOrder 交易订单
	 */
	void updateOrder(PayGoodsOrder goodsOrder, PayTradeOrder tradeOrder);

	/**
	 * 调用入口
	 *
	 * @param goodsOrder 商品订单
	 * @return 调用入口
	 */
	Object handle(PayGoodsOrder goodsOrder);

}
