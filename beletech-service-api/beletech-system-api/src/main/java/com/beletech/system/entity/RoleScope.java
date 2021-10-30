package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_role_scope")
@ApiModel(value = "RoleScope对象", description = "RoleScope对象")
public class RoleScope implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@ApiModelProperty(value = "权限类型")
	private Integer scopeCategory;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "权限id")
	private Long scopeId;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "角色id")
	private Long roleId;
}
