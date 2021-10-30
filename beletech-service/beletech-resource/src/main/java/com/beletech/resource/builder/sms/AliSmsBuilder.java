package com.beletech.resource.builder.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.sms.AliSmsTemplate;
import com.beletech.core.sms.SmsTemplate;
import com.beletech.core.sms.props.SmsProperties;
import lombok.SneakyThrows;
import com.beletech.resource.entity.Sms;

/**
 * 阿里云短信构建类
 *
 * @author XueBing
 */
public class AliSmsBuilder {

	@SneakyThrows
	public static SmsTemplate template(Sms sms, BeletechRedis beletechRedis) {
		SmsProperties smsProperties = new SmsProperties();
		smsProperties.setTemplateId(sms.getTemplateId());
		smsProperties.setAccessKey(sms.getAccessKey());
		smsProperties.setSecretKey(sms.getSecretKey());
		smsProperties.setRegionId(sms.getRegionId());
		smsProperties.setSignName(sms.getSignName());
		IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKey(), smsProperties.getSecretKey());
		IAcsClient acsClient = new DefaultAcsClient(profile);
		return new AliSmsTemplate(smsProperties, acsClient, beletechRedis);
	}

}
