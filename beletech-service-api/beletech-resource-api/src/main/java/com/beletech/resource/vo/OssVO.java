package com.beletech.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.resource.entity.Oss;

/**
 * OssVO
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OssVO对象", description = "对象存储表")
public class OssVO extends Oss {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("分类名")
	private String categoryName;

	@ApiModelProperty("是否启用")
	private String statusName;

}
