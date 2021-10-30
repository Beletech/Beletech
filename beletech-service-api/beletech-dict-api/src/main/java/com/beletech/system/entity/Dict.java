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
@TableName("base_dict")
@ApiModel(value = "Dict对象", description = "Dict对象")
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "父主键")
	private Long parentId;

	@ApiModelProperty(value = "字典码")
	private String code;

	@ApiModelProperty(value = "字典值")
	private String dictKey;

	@ApiModelProperty(value = "字典名称")
	private String dictValue;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "字典备注")
	private String remark;

	@ApiModelProperty(value = "是否已封存")
	private Integer isSealed;

	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;
}
