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
@TableName("base_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept implements Serializable {

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

	@ApiModelProperty(value = "机构名")
	private String deptName;

	@ApiModelProperty(value = "机构全称")
	private String fullName;

	@ApiModelProperty(value = "祖级机构主键")
	private String ancestors;

	@ApiModelProperty(value = "机构类型")
	private Integer deptCategory;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "备注")
	private String remark;

	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;
}
