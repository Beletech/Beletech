package com.beletech.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.payment.entity.PayChannel;

/**
 * 渠道
 *
 * @author lengleng
 * @date 2019-05-28 23:57:58
 */
public interface PayChannelService extends IService<PayChannel> {

	/**
	 * 新增支付渠道
	 *
	 * @param payChannel 支付渠道
	 * @return 状态
	 */
	Boolean saveChannel(PayChannel payChannel);
}
