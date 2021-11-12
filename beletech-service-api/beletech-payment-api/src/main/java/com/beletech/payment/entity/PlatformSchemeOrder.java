package com.beletech.payment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 平台方案记录表
 *
 * @author XueBing
 * @date 2021-11-10
 */
@Data
@TableName("base_platform_scheme_order")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "异步通知记录")
public class PlatformSchemeOrder extends BaseEntity {

	@ApiModelProperty("方案id")
	private Long platformId;

	@ApiModelProperty("方案名称")
	private String platformName;

	@ApiModelProperty("流水号")
	private String serialNumber;

	@ApiModelProperty("价格")
	private BigDecimal amount;

	@ApiModelProperty("单价")
	private BigDecimal price;

	@ApiModelProperty("类型 1天 2月 3年 4 永久")
	private Integer type;

	@ApiModelProperty("openId")
	private String userId;

	@ApiModelProperty("租户id")
	@TableField(exist = false)
	private String tenantId;
}
