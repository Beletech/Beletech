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
@TableName("base_scope_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DataScope对象", description = "DataScope对象")
public class DataScope extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单主键")
	private Long menuId;

	@ApiModelProperty(value = "资源编号")
	private String resourceCode;

	@ApiModelProperty(value = "数据权限名称")
	private String scopeName;

	@ApiModelProperty(value = "数据权限可见字段")
	private String scopeField;

	@ApiModelProperty(value = "数据权限类名")
	private String scopeClass;

	@ApiModelProperty(value = "数据权限字段")
	private String scopeColumn;

	@ApiModelProperty(value = "数据权限类型")
	private Integer scopeType;

	@ApiModelProperty(value = "数据权限值域")
	private String scopeValue;

	@ApiModelProperty(value = "数据权限备注")
	private String remark;
}
