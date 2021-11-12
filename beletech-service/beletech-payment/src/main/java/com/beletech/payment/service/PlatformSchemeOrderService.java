package com.beletech.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.payment.entity.PlatformSchemeOrder;

import java.util.Map;

/**
 * 系统收费方案记录表
 *
 * @author XueBing
 * @date 2021-10-31
 */
public interface PlatformSchemeOrderService extends IService<PlatformSchemeOrder> {

	/**
	 * 系统收费方案记录
	 *
	 * @param platformSchemeOrder 系统收费方案记录
	 * @param isMerge             是否是服务商
	 * @return 支付信息
	 */
	Map<String, Object> buy(PlatformSchemeOrder platformSchemeOrder, boolean isMerge);
}
