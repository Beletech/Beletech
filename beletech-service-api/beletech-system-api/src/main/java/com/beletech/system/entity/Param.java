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
 * @author XueBing
 */
@Data
@TableName("base_param")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Param对象", description = "Param对象")
public class Param extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数名")
	private String paramName;

	@ApiModelProperty(value = "参数键")
	private String paramKey;

	@ApiModelProperty(value = "参数值")
	private String paramValue;

	@ApiModelProperty(value = "备注")
	private String remark;
}
