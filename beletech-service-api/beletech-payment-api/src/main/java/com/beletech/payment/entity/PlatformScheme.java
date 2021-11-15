package com.beletech.payment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 平台方案表
 *
 * @author XueBing
 * @date 2021-11-10
 */
@Data
@TableName("base_platform_scheme")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "平台收费方案")
public class PlatformScheme extends BaseEntity {

	@ApiModelProperty("方案描述内容")
	private String content;

	@ApiModelProperty("方案名称")
	private String name;

	@ApiModelProperty("单价")
	private BigDecimal price;

	@ApiModelProperty("类型 1天 2月 3年 4 永久")
	private Integer type;
}
