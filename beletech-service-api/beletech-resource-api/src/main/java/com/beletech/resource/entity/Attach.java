package com.beletech.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.beletech.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件表实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_attach")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Attach对象", description = "附件表")
public class Attach extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "附件地址")
	private String link;

	@ApiModelProperty(value = "附件域名")
	private String domain;

	@ApiModelProperty(value = "附件名称")
	private String name;

	@ApiModelProperty(value = "附件原名")
	private String originalName;

	@ApiModelProperty(value = "附件拓展名")
	private String extension;

	@ApiModelProperty(value = "附件大小")
	private Long attachSize;
}
