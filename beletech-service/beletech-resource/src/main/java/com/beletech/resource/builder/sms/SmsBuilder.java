package com.beletech.resource.builder.sms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.log.exception.ServiceException;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.sms.SmsTemplate;
import com.beletech.core.sms.enums.SmsEnum;
import com.beletech.core.sms.enums.SmsStatusEnum;
import com.beletech.core.sms.props.SmsProperties;
import com.beletech.core.tool.utils.Func;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.core.tool.utils.StringUtil;
import com.beletech.core.tool.utils.WebUtil;
import com.beletech.resource.entity.Sms;
import com.beletech.resource.service.ISmsService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.beletech.core.cache.constant.CacheConstant.RESOURCE_CACHE;

/**
 * Sms短信服务统一构建类
 *
 * @author XueBing
 */
public class SmsBuilder {

	public static final String SMS_CODE = "sms:code:";
	public static final String SMS_PARAM_KEY = "code";

	private final SmsProperties smsProperties;
	private final ISmsService smsService;
	private final BeletechRedis beletechRedis;


	public SmsBuilder(SmsProperties smsProperties, ISmsService smsService, BeletechRedis beletechRedis) {
		this.smsProperties = smsProperties;
		this.smsService = smsService;
		this.beletechRedis = beletechRedis;
	}

	/**
	 * SmsTemplate配置缓存池
	 */
	private final Map<String, SmsTemplate> templatePool = new ConcurrentHashMap<>();

	/**
	 * Sms配置缓存池
	 */
	private final Map<String, Sms> smsPool = new ConcurrentHashMap<>();


	/**
	 * 获取template
	 *
	 * @return SmsTemplate
	 */
	public SmsTemplate template() {
		return template(StringPool.EMPTY);
	}

	/**
	 * 获取template
	 *
	 * @param code 资源编号
	 * @return SmsTemplate
	 */
	public SmsTemplate template(String code) {
		String tenantId = AuthUtil.getTenantId();
		Sms sms = getSms(tenantId, code);
		Sms smsCached = smsPool.get(tenantId);
		SmsTemplate template = templatePool.get(tenantId);
		// 若为空或者不一致，则重新加载
		if (Func.hasEmpty(template, smsCached) || !sms.getTemplateId().equals(smsCached.getTemplateId()) || !sms.getAccessKey().equals(smsCached.getAccessKey())) {
			synchronized (SmsBuilder.class) {
				template = templatePool.get(tenantId);
				if (Func.hasEmpty(template, smsCached) || !sms.getTemplateId().equals(smsCached.getTemplateId()) || !sms.getAccessKey().equals(smsCached.getAccessKey())) {
					if (sms.getCategory() == SmsEnum.YUNPIAN.getCategory()) {
						template = YunpianSmsBuilder.template(sms, beletechRedis);
					} else if (sms.getCategory() == SmsEnum.QINIU.getCategory()) {
						template = QiniuSmsBuilder.template(sms, beletechRedis);
					} else if (sms.getCategory() == SmsEnum.ALI.getCategory()) {
						template = AliSmsBuilder.template(sms, beletechRedis);
					} else if (sms.getCategory() == SmsEnum.TENCENT.getCategory()) {
						template = TencentSmsBuilder.template(sms, beletechRedis);
					}
					templatePool.put(tenantId, template);
					smsPool.put(tenantId, sms);
				}
			}
		}
		return template;
	}


	/**
	 * 获取短信实体
	 *
	 * @param tenantId 租户ID
	 * @return Sms
	 */
	public Sms getSms(String tenantId, String code) {
		String key = tenantId;
		LambdaQueryWrapper<Sms> lqw = Wrappers.<Sms>query().lambda().eq(Sms::getTenantId, tenantId);
		// 获取传参的资源编号并查询，若有则返回，若没有则调启用的配置
		String smsCode = StringUtil.isBlank(code) ? WebUtil.getParameter(SMS_PARAM_KEY) : code;
		if (StringUtil.isNotBlank(smsCode)) {
			key = key.concat(StringPool.DASH).concat(smsCode);
			lqw.eq(Sms::getSmsCode, smsCode);
		} else {
			lqw.eq(Sms::getStatus, SmsStatusEnum.ENABLE.getNum());
		}
		Sms sms = CacheUtil.get(RESOURCE_CACHE, SMS_CODE, key, () -> {
			Sms s = smsService.getOne(lqw);
			// 若为空则调用默认配置
			if ((Func.isEmpty(s))) {
				Sms defaultSms = new Sms();
				defaultSms.setId(0L);
				defaultSms.setTemplateId(smsProperties.getTemplateId());
				defaultSms.setRegionId(smsProperties.getRegionId());
				defaultSms.setCategory(SmsEnum.of(smsProperties.getName()).getCategory());
				defaultSms.setAccessKey(smsProperties.getAccessKey());
				defaultSms.setSecretKey(smsProperties.getSecretKey());
				defaultSms.setSignName(smsProperties.getSignName());
				return defaultSms;
			} else {
				return s;
			}
		});
		if (sms == null || sms.getId() == null) {
			throw new ServiceException("未获取到对应的短信配置");
		} else {
			return sms;
		}
	}

}
