package com.beletech.payment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Data
@TableName("base_pay_channel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "渠道")
public class PayChannel extends TenantEntity {

	@ApiModelProperty("应用ID")
	private String appId;

	@ApiModelProperty("渠道ID")
	private String channelId;

	@ApiModelProperty("渠道名称,如:alipay,wechat")
	private String channelName;

	@ApiModelProperty("渠道商户ID | 12****123")
	private String channelMchId;

	@ApiModelProperty("前端回调地址")
	private String returnUrl;

	@ApiModelProperty("后端回调地址")
	private String notifyUrl;

	@ApiModelProperty("渠道状态")
	private String state;

	@ApiModelProperty("配置参数,json字符串")
	private String param;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("删除标记")
	private String delFlag;
}
