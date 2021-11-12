package com.beletech.payment.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 支付渠道名称
 */
@AllArgsConstructor
public enum PayChannelNameEnum {

	/**
	 * 支付宝wap支付
	 */
	ALIPAY_WAP("ALIPAY_WAP", "支付宝手机支付"),

	/**
	 * 微信H5支付
	 */
	WEIXIN_WAP("WEIXIN_WAP", "微信H5支付"),

	/**
	 * 微信公众号支付
	 */
	WEIXIN_MP("WEIXIN_MP", "微信公众号支付"),

	/**
	 * 聚合支付
	 */
	MERGE_PAY("MERGE_PAY", "yungouos 一码付");

	/**
	 * 名称
	 */
	@Getter
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 通过ua 判断所属渠道
	 * @param ua 浏览器类型
	 * @return 枚举
	 */
	public static PayChannelNameEnum getChannel(String ua) {
		if (ua.contains(PayConstants.ALIPAY)) {
			return PayChannelNameEnum.ALIPAY_WAP;
		}
		else if (ua.contains(PayConstants.MICRO_MESSENGER)) {
			return PayChannelNameEnum.WEIXIN_MP;
		}
		else {
			return PayChannelNameEnum.MERGE_PAY;
		}
	}

}
