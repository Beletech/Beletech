package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author Beletech
 */
@Data
@TableName("base_scope_api")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiScope对象", description = "ApiScope对象")
public class ApiScope extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单主键")
	private Long menuId;

	@ApiModelProperty(value = "资源编号")
	private String resourceCode;

	@ApiModelProperty(value = "接口权限名称")
	private String scopeName;

	@ApiModelProperty(value = "接口权限字段")
	private String scopePath;

	@ApiModelProperty(value = "接口权限类型")
	private Integer scopeType;

	@ApiModelProperty(value = "接口权限备注")
	private String remark;
}
