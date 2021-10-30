package com.beletech.resource.builder.sms;

import com.yunpian.sdk.YunpianClient;
import lombok.SneakyThrows;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.sms.SmsTemplate;
import com.beletech.core.sms.props.SmsProperties;
import com.beletech.core.sms.YunpianSmsTemplate;
import com.beletech.resource.entity.Sms;

/**
 * 云片短信构建类
 *
 * @author XueBing
 */
public class YunpianSmsBuilder {

	@SneakyThrows
	public static SmsTemplate template(Sms sms, BeletechRedis beletechRedis) {
		SmsProperties smsProperties = new SmsProperties();
		smsProperties.setTemplateId(sms.getTemplateId());
		smsProperties.setAccessKey(sms.getAccessKey());
		smsProperties.setSignName(sms.getSignName());
		YunpianClient client = new YunpianClient(smsProperties.getAccessKey()).init();
		return new YunpianSmsTemplate(smsProperties, client, beletechRedis);
	}

}
