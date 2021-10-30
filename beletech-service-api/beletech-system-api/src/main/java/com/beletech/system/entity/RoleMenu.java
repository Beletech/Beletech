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
@TableName("base_role_menu")
@ApiModel(value = "RoleMenu对象", description = "RoleMenu对象")
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "菜单id")
	private Long menuId;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "角色id")
	private Long roleId;
}
