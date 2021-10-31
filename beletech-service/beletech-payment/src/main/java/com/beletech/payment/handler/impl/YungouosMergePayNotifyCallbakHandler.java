package com.beletech.payment.handler.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.common.utils.TenantContextHolder;
import com.beletech.payment.entity.PayGoodsOrder;
import com.beletech.payment.entity.PayNotifyRecord;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.handler.MessageDuplicateCheckerHandler;
import com.beletech.payment.service.PayGoodsOrderService;
import com.beletech.payment.service.PayNotifyRecordService;
import com.beletech.payment.service.PayTradeOrderService;
import com.beletech.payment.utils.OrderStatusEnum;
import com.beletech.payment.utils.PayConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 聚合支付回调处理
 */
@Slf4j
@AllArgsConstructor
@SuppressWarnings("all")
@Service("mergePayCallback")
public class YungouosMergePayNotifyCallbakHandler extends AbstractPayNotifyCallbakHandler {

	private final MessageDuplicateCheckerHandler duplicateCheckerHandler;

	private final PayNotifyRecordService recordService;

	private final PayTradeOrderService tradeOrderService;

	private final PayGoodsOrderService goodsOrderService;

	@Override
	public void before(Map<String, String> params) {
		Integer tenant = MapUtil.getInt(params, "attach");
		TenantContextHolder.setTenantId(tenant);
	}

	@Override
	public Boolean duplicateChecker(Map<String, String> params) {
		// 判断10秒内是否已经回调处理
		if (duplicateCheckerHandler.isDuplicate(params.get(PayConstants.MERGE_OUT_TRADE_NO))) {
			log.info("聚合支付订单重复回调 {} 不做处理", params);
			this.saveNotifyRecord(params, "重复回调");
			return true;
		}
		return false;
	}

	@Override
	public Boolean verifyNotify(Map<String, String> params) {
		return Boolean.TRUE;
	}

	@Override
	public String parse(Map<String, String> params) {
		String mergeCode = params.get(PayConstants.MERGE_CODE);
		String orderNo = params.get(PayConstants.MERGE_OUT_TRADE_NO);
		PayGoodsOrder goodsOrder = goodsOrderService
			.getOne(Wrappers.<PayGoodsOrder>lambdaQuery().eq(PayGoodsOrder::getPayOrderId, orderNo));
		PayTradeOrder tradeOrder = tradeOrderService
			.getOne(Wrappers.<PayTradeOrder>lambdaQuery().eq(PayTradeOrder::getOrderId, orderNo));

		if (OrderStatusEnum.SUCCESS.getStatus().equals(mergeCode)) {
			goodsOrder.setStatus(OrderStatusEnum.SUCCESS.getStatus());
			tradeOrder.setStatus(OrderStatusEnum.SUCCESS.getStatus());
		} else {
			goodsOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
			tradeOrder.setStatus(OrderStatusEnum.FAIL.getStatus());
		}

		goodsOrderService.updateById(goodsOrder);

		String succTime = MapUtil.getStr(params, "time");
		tradeOrder.setPaySuccTime(DateUtil.parse(succTime, DatePattern.NORM_DATETIME_FORMAT).getTime());
		tradeOrder.setChannelOrderNo(params.get("orderNo"));
		tradeOrderService.updateById(tradeOrder);
		return "SUCCESS";
	}

	@Override
	public void saveNotifyRecord(Map<String, String> params, String result) {
		PayNotifyRecord record = new PayNotifyRecord();
		String notifyId = RandomUtil.randomNumbers(12);
		MapUtil.renameKey(params, PayConstants.MERGE_OUT_TRADE_NO, PayConstants.OUT_TRADE_NO);
		saveRecord(params, result, record, notifyId, recordService);
	}
}
