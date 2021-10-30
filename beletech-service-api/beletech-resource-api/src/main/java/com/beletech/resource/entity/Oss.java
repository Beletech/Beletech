package com.beletech.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author Beletech
 */
@Data
@TableName("base_oss")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Oss对象", description = "Oss对象")
public class Oss extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "所属分类")
	private Integer category;

	@ApiModelProperty(value = "资源编号")
	private String ossCode;

	@ApiModelProperty(value = "资源地址")
	private String endpoint;

	@ApiModelProperty(value = "accessKey")
	private String accessKey;

	@ApiModelProperty(value = "secretKey")
	private String secretKey;

	@ApiModelProperty(value = "空间名")
	private String bucketName;

	@ApiModelProperty(value = "应用ID")
	private String appId;

	@ApiModelProperty(value = "地域简称")
	private String region;

	@ApiModelProperty(value = "备注")
	private String remark;
}
