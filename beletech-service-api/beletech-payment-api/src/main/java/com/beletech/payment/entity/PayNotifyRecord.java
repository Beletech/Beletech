package com.beletech.payment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异步通知记录
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Data
@TableName("base_pay_notify_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "异步通知记录")
public class PayNotifyRecord extends TenantEntity {

	@ApiModelProperty("响应ID")
	private String notifyId;

	@ApiModelProperty("请求报文")
	private String request;

	@ApiModelProperty("响应报文")
	private String response;

	@ApiModelProperty("系统订单号")
	private String orderNo;

	@ApiModelProperty("http状态")
	private String httpStatus;

	@TableField(exist = false)
	@ApiModelProperty("删除标记")
	private String delFlag;
}
