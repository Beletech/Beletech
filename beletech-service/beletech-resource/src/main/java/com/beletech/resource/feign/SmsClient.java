package com.beletech.resource.feign;

import lombok.AllArgsConstructor;
import com.beletech.core.sms.model.SmsCode;
import com.beletech.core.sms.model.SmsData;
import com.beletech.core.sms.model.SmsResponse;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.jackson.JsonUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.builder.sms.SmsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.beletech.resource.utils.SmsUtil.*;

/**
 * 短信远程调用服务
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
public class SmsClient implements ISmsClient {

	private final SmsBuilder smsBuilder;

	@Override
	@PostMapping(SEND_MESSAGE)
	public Result<SmsResponse> sendMessage(String code, String params, String phones) {
		SmsData smsData = new SmsData(JsonUtil.readMap(params, String.class, String.class));
		SmsResponse response = smsBuilder.template(code).sendMessage(smsData, Func.toStrList(phones));
		return Result.data(response);
	}

	@Override
	@PostMapping(SEND_VALIDATE)
	public Result<Object> sendValidate(String code, String phone) {
		Map<String, String> params = getValidateParams();
		SmsCode smsCode = smsBuilder.template(code).sendValidate(new SmsData(params).setKey(PARAM_KEY), phone);
		return smsCode.isSuccess() ? Result.data(smsCode, SEND_SUCCESS) : Result.fail(SEND_FAIL);
	}

	@Override
	@PostMapping(VALIDATE_MESSAGE)
	public Result<Object> validateMessage(String code, String id, String value, String phone) {
		SmsCode smsCode = new SmsCode().setId(id).setValue(value).setPhone(phone);
		boolean validate = smsBuilder.template(code).validateMessage(smsCode);
		return validate ? Result.success(VALIDATE_SUCCESS) : Result.fail(VALIDATE_FAIL);
	}
}
