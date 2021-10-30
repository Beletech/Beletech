package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tenant.mp.TenantEntity;

/**
 * 顶部菜单表实体类
 *
 * @author Beletech
 */
@Data
@TableName("base_top_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TopMenu对象", description = "顶部菜单表")
public class TopMenu extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "顶部菜单编号")
	private String code;

	@ApiModelProperty(value = "顶部菜单名")
	private String name;

	@ApiModelProperty(value = "顶部菜单资源")
	private String source;

	@ApiModelProperty(value = "顶部菜单排序")
	private Integer sort;
}
