package com.beletech.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.payment.entity.PayGoodsOrder;

import java.util.Map;

/**
 * 商品
 *
 * @author XueBing
 * @date 2021-10-31
 */
public interface PayGoodsOrderService extends IService<PayGoodsOrder> {

	/**
	 * 购买商品
	 *
	 * @param goodsOrder goods
	 * @param isMerge    是否是服务商
	 * @return 支付信息
	 */
	Map<String, Object> buy(PayGoodsOrder goodsOrder, boolean isMerge);

}
