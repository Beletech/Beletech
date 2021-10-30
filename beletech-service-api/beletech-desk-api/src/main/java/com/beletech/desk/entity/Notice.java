package com.beletech.desk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tenant.mp.TenantEntity;

import java.util.Date;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_notice")
@EqualsAndHashCode(callSuper = true)
public class Notice extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "通知类型")
	private Integer category;

	@ApiModelProperty(value = "发布日期")
	private Date releaseTime;

	@ApiModelProperty(value = "内容")
	private String content;
}
