package com.beletech.payment.utils;

import com.beletech.payment.entity.PayChannel;
import lombok.experimental.UtilityClass;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 聚合支付配置管理
 */
@UtilityClass
public class ChannelPayApiConfigKit {

	private static final ThreadLocal<PayChannel> TL = new ThreadLocal();

	public PayChannel get() {
		return TL.get();
	}

	public void put(PayChannel channel) {
		TL.set(channel);
	}

	public void remove() {
		TL.remove();
	}
}
