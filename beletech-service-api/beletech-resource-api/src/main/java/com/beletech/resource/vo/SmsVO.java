package com.beletech.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.resource.entity.Sms;

/**
 * 短信配置表视图实体类
 *
 * @author Beletech
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SmsVO对象", description = "短信配置表")
public class SmsVO extends Sms {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("分类名")
	private String categoryName;

	@ApiModelProperty("是否启用")
	private String statusName;
}
