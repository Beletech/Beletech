package com.beletech.resource.builder.sms;

import com.github.qcloudsms.SmsMultiSender;
import lombok.SneakyThrows;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.sms.SmsTemplate;
import com.beletech.core.sms.props.SmsProperties;
import com.beletech.core.sms.TencentSmsTemplate;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.entity.Sms;

/**
 * 腾讯云短信构建类
 *
 * @author XueBing
 */
public class TencentSmsBuilder {

	@SneakyThrows
	public static SmsTemplate template(Sms sms, BeletechRedis beletechRedis) {
		SmsProperties smsProperties = new SmsProperties();
		smsProperties.setTemplateId(sms.getTemplateId());
		smsProperties.setAccessKey(sms.getAccessKey());
		smsProperties.setSecretKey(sms.getSecretKey());
		smsProperties.setSignName(sms.getSignName());
		SmsMultiSender smsSender = new SmsMultiSender(Func.toInt(smsProperties.getAccessKey()), sms.getSecretKey());
		return new TencentSmsTemplate(smsProperties, smsSender, beletechRedis);
	}

}
