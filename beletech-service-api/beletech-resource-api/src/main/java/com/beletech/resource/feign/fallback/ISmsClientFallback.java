package com.beletech.resource.feign.fallback;

import com.beletech.core.sms.model.SmsResponse;
import com.beletech.core.tool.api.Result;
import com.beletech.resource.feign.ISmsClient;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author XueBing
 */
@Component
public class ISmsClientFallback implements ISmsClient {
	@Override
	public Result<SmsResponse> sendMessage(String code, String params, String phones) {
		return Result.fail("远程调用失败");
	}

	@Override
	public Result<Object> sendValidate(String code, String phone) {
		return Result.fail("远程调用失败");
	}

	@Override
	public Result<Object> validateMessage(String code, String id, String value, String phone) {
		return Result.fail("远程调用失败");
	}
}
