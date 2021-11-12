package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.handler.PlatformSchemeHandler;
import com.beletech.payment.mapper.PlatformSchemeOrderMapper;
import com.beletech.payment.service.PlatformSchemeOrderService;
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
 * 支付方案
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformSchemeOrderServiceImpl extends ServiceImpl<PlatformSchemeOrderMapper, PlatformSchemeOrder>
	implements PlatformSchemeOrderService {

	private final Map<String, PlatformSchemeHandler> platformSchemeHandlerMap;

	private final HttpServletRequest request;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> buy(PlatformSchemeOrder platformSchemeOrder, boolean isMerge) {
		// 是否聚合支付
		String ua = isMerge ? "MERGE_PAY" : request.getHeader(HttpHeaders.USER_AGENT);
		PayChannelNameEnum channel = PayChannelNameEnum.getChannel(ua);
		PlatformSchemeHandler orderHandler = platformSchemeHandlerMap.get(channel.name());
		Object params = orderHandler.handle(platformSchemeOrder);
		Map<String, Object> result = new HashMap<>(4);
		result.put("channel", channel.name());
		result.put("platformScheme", platformSchemeOrder);
		result.put("params", params);
		return result;
	}
}
