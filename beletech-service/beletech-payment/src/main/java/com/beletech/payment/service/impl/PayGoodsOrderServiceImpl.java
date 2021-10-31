package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.handler.PayOrderHandler;
import com.beletech.payment.mapper.PayGoodsOrderMapper;
import com.beletech.payment.service.PayGoodsOrderService;
import com.beletech.payment.utils.PayChannelNameEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayGoodsOrderServiceImpl extends ServiceImpl<PayGoodsOrderMapper, PayGoodsOrder>
	implements PayGoodsOrderService {

	private final Map<String, PayOrderHandler> orderHandlerMap;

	private final HttpServletRequest request;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> buy(PayGoodsOrder goodsOrder, boolean isMerge) {
		// 是否聚合支付
		String ua = isMerge ? "MERGE_PAY" : request.getHeader(HttpHeaders.USER_AGENT);
		PayChannelNameEnum channel = PayChannelNameEnum.getChannel(ua);
		PayOrderHandler orderHandler = orderHandlerMap.get(channel.name());
		goodsOrder.setGoodsName("测试产品");
		goodsOrder.setGoodsId("10001");
		Object params = orderHandler.handle(goodsOrder);
		Map<String, Object> result = new HashMap<>(4);
		result.put("channel", channel.name());
		result.put("goods", goodsOrder);
		result.put("params", params);
		return result;
	}
}
