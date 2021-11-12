package com.beletech.payment.handler.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.common.utils.TenantContextHolder;
import com.beletech.payment.entity.PayNotifyRecord;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.handler.MessageDuplicateCheckerHandler;
import com.beletech.payment.service.PayNotifyRecordService;
import com.beletech.payment.service.PayTradeOrderService;
import com.beletech.payment.service.PlatformSchemeOrderService;
import com.beletech.payment.utils.PayConstants;
import com.beletech.payment.utils.TradeStatusEnum;
import com.ijpay.core.kit.WxPayKit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 微信回调处理
 */
@Slf4j
@AllArgsConstructor
@SuppressWarnings("all")
@Service("weChatCallback")
public class WeChatPayNotifyCallbackHandler extends AbstractPayNotifyCallbakHandler {

	private final MessageDuplicateCheckerHandler duplicateCheckerHandler;

	private final PayTradeOrderService tradeOrderService;

	private final PlatformSchemeOrderService platformSchemeOrderService;

	private final PayNotifyRecordService recordService;

	@Override
	public void before(Map<String, String> params) {
		String tenant = MapUtil.getStr(params, "attach");
		TenantContextHolder.setTenantId(tenant);
	}

	@Override
	public Boolean duplicateChecker(Map<String, String> params) {
		// 判断10秒内是否已经回调处理
		if (duplicateCheckerHandler.isDuplicate(params.get(PayConstants.OUT_TRADE_NO))) {
			log.info("微信订单重复回调 {} 不做处理", params);
			this.saveNotifyRecord(params, "重复回调");
			return true;
		}
		return false;
	}

	@Override
	public Boolean verifyNotify(Map<String, String> params) {
		return true;
	}

	@Override
	public String parse(Map<String, String> params) {
		Integer tradeStatus = EnumUtil.fromString(TradeStatusEnum.class, params.get(PayConstants.RESULT_CODE))
			.getStatus();

		String orderNo = params.get(PayConstants.OUT_TRADE_NO);
		PlatformSchemeOrder platformSchemeOrder = platformSchemeOrderService
			.getOne(Wrappers.<PlatformSchemeOrder>lambdaQuery().eq(PlatformSchemeOrder::getSerialNumber, orderNo));
		platformSchemeOrder.setStatus(tradeStatus);
		platformSchemeOrderService.updateById(platformSchemeOrder);

		PayTradeOrder tradeOrder = tradeOrderService
			.getOne(Wrappers.<PayTradeOrder>lambdaQuery().eq(PayTradeOrder::getOrderId, orderNo));
		Long succTime = MapUtil.getLong(params, "time_end");
		tradeOrder.setPaySuccTime(succTime);
		tradeOrder.setStatus(tradeStatus);
		tradeOrder.setChannelOrderNo(params.get("transaction_id"));
		tradeOrder.setErrMsg(params.get("err_code_des"));
		tradeOrder.setErrCode(params.get("err_code"));
		tradeOrderService.updateById(tradeOrder);

		Map<String, String> xml = new HashMap<>(4);
		xml.put("return_code", "SUCCESS");
		xml.put("return_msg", "OK");
		return WxPayKit.toXml(xml);
	}

	@Override
	public void saveNotifyRecord(Map<String, String> params, String result) {
		PayNotifyRecord record = new PayNotifyRecord();
		String notifyId = params.get("transaction_id");
		saveRecord(params, result, record, notifyId, recordService);
	}
}
