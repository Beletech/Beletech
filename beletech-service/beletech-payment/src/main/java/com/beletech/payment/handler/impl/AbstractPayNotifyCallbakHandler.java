package com.beletech.payment.handler.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.beletech.payment.entity.PayNotifyRecord;
import com.beletech.payment.handler.PayNotifyCallbakHandler;
import com.beletech.payment.service.PayNotifyRecordService;
import com.beletech.payment.utils.PayConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
public abstract class AbstractPayNotifyCallbakHandler implements PayNotifyCallbakHandler {

	@Override
	public String handle(Map<String, String> params) {
		// 初始化租户
		before(params);
		// 去重处理
		if (duplicateChecker(params)) {
			return null;
		}
		// 验签处理
		if (!verifyNotify(params)) {
			return null;
		}
		String result = parse(params);
		// 保存处理结果
		saveNotifyRecord(params, result);
		return result;
	}

	public void saveRecord(Map<String, String> params, String result, PayNotifyRecord record, String notifyId,
						   PayNotifyRecordService recordService) {
		record.setNotifyId(notifyId);
		String orderNo = params.get(PayConstants.OUT_TRADE_NO);
		record.setOrderNo(orderNo);
		record.setRequest(MapUtil.join(params, StrUtil.DASHED, StrUtil.DASHED));
		record.setResponse(result);
		recordService.save(record);
	}
}
