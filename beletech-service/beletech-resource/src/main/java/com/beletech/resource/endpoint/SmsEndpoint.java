package com.beletech.resource.endpoint;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.beletech.core.sms.model.SmsCode;
import com.beletech.core.sms.model.SmsData;
import com.beletech.core.sms.model.SmsResponse;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.jackson.JsonUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.builder.sms.SmsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.beletech.resource.utils.SmsUtil.*;

/**
 * 短信服务端点
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/sms/endpoint")
@Api(value = "短信服务端点", tags = "短信服务端点")
public class SmsEndpoint {

	/**
	 * 短信服务构建类
	 */
	private final SmsBuilder smsBuilder;

	//================================= 短信服务校验 =================================

	/**
	 * 短信验证码发送
	 *
	 * @param phone 手机号
	 */
	@SneakyThrows
	@PostMapping("/send-validate")
	public Result sendValidate(@RequestParam String phone) {
		Map<String, String> params = getValidateParams();
		SmsCode smsCode = smsBuilder.template().sendValidate(new SmsData(params).setKey(PARAM_KEY), phone);
		return smsCode.isSuccess() ? Result.data(smsCode, SEND_SUCCESS) : Result.fail(SEND_FAIL);
	}

	/**
	 * 校验短信
	 *
	 * @param smsCode 短信校验信息
	 */
	@SneakyThrows
	@PostMapping("/validate-message")
	public Result validateMessage(SmsCode smsCode) {
		boolean validate = smsBuilder.template().validateMessage(smsCode);
		return validate ? Result.success(VALIDATE_SUCCESS) : Result.fail(VALIDATE_FAIL);
	}

	//========== 通用短信自定义发送(支持自定义params参数传递, 推荐用于测试, 不推荐用于生产环境) ==========

	/**
	 * 发送信息
	 *
	 * @param params 自定义短信参数
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-message")
	public Result sendMessage(@RequestParam String code, @RequestParam String params, @RequestParam String phones) {
		SmsData smsData = new SmsData(JsonUtil.readMap(params, String.class, String.class));
		return send(code, smsData, phones);
	}

	//========== 指定短信服务发送(可根据各种场景自定拓展定制, 损失灵活性增加安全性, 推荐用于生产环境) ==========

	/**
	 * 短信通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-notice")
	public Result sendNotice(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(3);
		params.put("title", "通知标题");
		params.put("content", "通知内容");
		params.put("date", "通知时间");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	/**
	 * 订单通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-order")
	public Result sendOrder(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(3);
		params.put("orderNo", "订单编号");
		params.put("packageNo", "快递单号");
		params.put("user", "收件人");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	/**
	 * 会议通知
	 *
	 * @param phones 手机号集合
	 */
	@SneakyThrows
	@PostMapping("/send-meeting")
	public Result sendMeeting(@RequestParam String phones) {
		Map<String, String> params = new HashMap<>(2);
		params.put("roomId", "会议室");
		params.put("topic", "会议主题");
		params.put("date", "会议时间");
		SmsData smsData = new SmsData(params);
		return send(smsData, phones);
	}

	//================================= 通用短信发送接口 =================================

	/**
	 * 通用短信发送接口
	 *
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 是否发送成功
	 */
	private Result send(SmsData smsData, String phones) {
		SmsResponse response = smsBuilder.template().sendMessage(smsData, Func.toStrList(phones));
		return response.isSuccess() ? Result.success(SEND_SUCCESS) : Result.fail(SEND_FAIL);
	}

	/**
	 * 通用短信发送接口
	 *
	 * @param code    资源编号
	 * @param smsData 短信内容
	 * @param phones  手机号列表
	 * @return 是否发送成功
	 */
	private Result send(String code, SmsData smsData, String phones) {
		SmsResponse response = smsBuilder.template(code).sendMessage(smsData, Func.toStrList(phones));
		return response.isSuccess() ? Result.success(SEND_SUCCESS) : Result.fail(SEND_FAIL);
	}

}
