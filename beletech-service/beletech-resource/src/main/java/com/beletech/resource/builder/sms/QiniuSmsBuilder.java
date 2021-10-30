package com.beletech.resource.builder.sms;

import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import lombok.SneakyThrows;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.sms.SmsTemplate;
import com.beletech.core.sms.props.SmsProperties;
import com.beletech.core.sms.QiniuSmsTemplate;
import com.beletech.resource.entity.Sms;

/**
 * 七牛云短信构建类
 *
 * @author XueBing
 */
public class QiniuSmsBuilder {

	@SneakyThrows
	public static SmsTemplate template(Sms sms, BeletechRedis beletechRedis) {
		SmsProperties smsProperties = new SmsProperties();
		smsProperties.setTemplateId(sms.getTemplateId());
		smsProperties.setAccessKey(sms.getAccessKey());
		smsProperties.setSecretKey(sms.getSecretKey());
		smsProperties.setSignName(sms.getSignName());
		Auth auth = Auth.create(smsProperties.getAccessKey(), smsProperties.getSecretKey());
		SmsManager smsManager = new SmsManager(auth);
		return new QiniuSmsTemplate(smsProperties, smsManager, beletechRedis);
	}

}
