package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 行政区划表实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_region")
@ApiModel(value = "Region对象", description = "行政区划表")
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "code", type = IdType.INPUT)
	@ApiModelProperty(value = "区划编号")
	private String code;

	@ApiModelProperty(value = "父区划编号")
	private String parentCode;

	@ApiModelProperty(value = "祖区划编号")
	private String ancestors;

	@ApiModelProperty(value = "区划名称")
	private String name;

	@ApiModelProperty(value = "省级区划编号")
	private String provinceCode;

	@ApiModelProperty(value = "省级名称")
	private String provinceName;

	@ApiModelProperty(value = "市级区划编号")
	private String cityCode;

	@ApiModelProperty(value = "市级名称")
	private String cityName;

	@ApiModelProperty(value = "区级区划编号")
	private String districtCode;

	@ApiModelProperty(value = "区级名称")
	private String districtName;

	@ApiModelProperty(value = "镇级区划编号")
	private String townCode;

	@ApiModelProperty(value = "镇级名称")
	private String townName;

	@ApiModelProperty(value = "村级区划编号")
	private String villageCode;

	@ApiModelProperty(value = "村级名称")
	private String villageName;

	@ApiModelProperty(value = "层级")
	private Integer regionLevel;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "备注")
	private String remark;
}
