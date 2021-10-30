package com.beletech.system.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author XueBing
 */
@Getter
@AllArgsConstructor
public enum UserEnum {

	/**
	 * web
	 */
	WEB("web", 1),

	/**
	 * app
	 */
	APP("app", 2),

	/**
	 * other
	 */
	OTHER("other", 3),
	;

	final String name;
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return BeletechUserEnum
	 */
	public static UserEnum of(String name) {
		if (name == null) {
			return null;
		}
		UserEnum[] values = UserEnum.values();
		for (UserEnum smsEnum : values) {
			if (smsEnum.name.equals(name)) {
				return smsEnum;
			}
		}
		return null;
	}

}
