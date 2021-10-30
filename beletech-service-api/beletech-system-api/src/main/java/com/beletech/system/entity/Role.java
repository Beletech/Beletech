package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("base_role")
@ApiModel(value = "Role对象", description = "Role对象")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "父主键")
	private Long parentId;

	@ApiModelProperty(value = "角色名")
	private String roleName;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "角色别名")
	private String roleAlias;

	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;
}
