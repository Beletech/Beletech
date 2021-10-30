package com.beletech.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信配置表实体类
 *
 * @author Beletech
 */
@Data
@TableName("base_sms")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Sms对象", description = "短信配置表")
public class Sms extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "资源编号")
	private String smsCode;

	@ApiModelProperty(value = "模板ID")
	private String templateId;

	@ApiModelProperty(value = "分类")
	private Integer category;

	@ApiModelProperty(value = "accessKey")
	private String accessKey;

	@ApiModelProperty(value = "secretKey")
	private String secretKey;

	@ApiModelProperty(value = "regionId")
	private String regionId;

	@ApiModelProperty(value = "短信签名")
	private String signName;

	@ApiModelProperty(value = "备注")
	private String remark;
}
