package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PayChannel;
import com.beletech.payment.mapper.PayChannelMapper;
import com.beletech.payment.service.PayChannelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 渠道
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Service
@AllArgsConstructor
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {

	@Override
	public Boolean saveChannel(PayChannel payChannel) {
		return this.saveOrUpdate(payChannel);
	}
}
