package com.beletech.resource.config;

import lombok.AllArgsConstructor;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.sms.props.SmsProperties;
import com.beletech.resource.builder.sms.SmsBuilder;
import com.beletech.resource.service.ISmsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sms配置类
 *
 * @author XueBing
 */
@Configuration
@AllArgsConstructor
public class BeletechSmsConfiguration {

	private final SmsProperties smsProperties;

	private final ISmsService smsService;

	private final BeletechRedis beletechRedis;

	@Bean
	public SmsBuilder smsBuilder() {
		return new SmsBuilder(smsProperties, smsService, beletechRedis);
	}

}
