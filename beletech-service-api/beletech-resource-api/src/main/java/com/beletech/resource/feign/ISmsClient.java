package com.beletech.resource.feign;

import com.beletech.core.launch.constant.AppConstant;
import com.beletech.core.sms.model.SmsResponse;
import com.beletech.core.tool.api.Result;
import com.beletech.resource.feign.fallback.ISmsClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ISmsClient
 *
 * @author XueBing
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = ISmsClientFallback.class
)
public interface ISmsClient {
	String API_PREFIX = "/client";
	String SEND_MESSAGE = API_PREFIX + "/send-message";
	String SEND_VALIDATE = API_PREFIX + "/send-validate";
	String VALIDATE_MESSAGE = API_PREFIX + "/validate-message";

	/**
	 * 通用短信发送
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return R
	 */
	@PostMapping(SEND_MESSAGE)
	Result<SmsResponse> sendMessage(@RequestParam("code") String code, @RequestParam("params") String params, @RequestParam("phones") String phones);

	/**
	 * 短信验证码发送
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(SEND_VALIDATE)
	Result<Object> sendValidate(@RequestParam("code") String code, @RequestParam("phone") String phone);

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(VALIDATE_MESSAGE)
	Result<Object> validateMessage(@RequestParam("code") String code, @RequestParam("id") String id, @RequestParam("value") String value, @RequestParam("phone") String phone);

}
