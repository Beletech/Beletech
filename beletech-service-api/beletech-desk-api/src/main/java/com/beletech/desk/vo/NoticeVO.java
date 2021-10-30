package com.beletech.desk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.desk.entity.Notice;

/**
 * 通知公告视图类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "通知类型名")
	private String categoryName;

	@ApiModelProperty(value = "租户编号")
	private String tenantId;

}
