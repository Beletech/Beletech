package com.beletech.resource.enums;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sms资源编码枚举类
 *
 * @author XueBing
 * @apiNote 该枚举类对应短信配置模块的资源编码，可根据业务需求自行拓展
 */
@Getter
@AllArgsConstructor
public enum SmsCodeEnum {

	/**
	 * 默认编号
	 */
	DEFAULT(StringPool.EMPTY, 1),

	/**
	 * 验证码编号
	 */
	VALIDATE("validate", 2),

	/**
	 * 通知公告编号
	 */
	NOTICE("notice", 3),

	/**
	 * 下单通知编号
	 */
	ORDER("order", 4),

	/**
	 * 会议通知编号
	 */
	MEETING("meeting", 5),
	;

	final String name;
	final int category;

}
