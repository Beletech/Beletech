package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tenant.mp.TenantEntity;

/**
 * 岗位表实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Post对象", description = "岗位表")
public class Post extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "类型")
	private Integer category;

	@ApiModelProperty(value = "岗位编号")
	private String postCode;

	@ApiModelProperty(value = "岗位名称")
	private String postName;

	@ApiModelProperty(value = "岗位排序")
	private Integer sort;

	@ApiModelProperty(value = "岗位描述")
	private String remark;
}
