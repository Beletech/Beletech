package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.mp.base.BaseEntity;
import com.beletech.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_tenant")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Tenant对象", description = "Tenant对象")
public class Tenant extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "租户名称")
	private String tenantName;

	@ApiModelProperty(value = "域名地址")
	private String domain;

	@ApiModelProperty(value = "系统背景")
	private String backgroundUrl;

	@ApiModelProperty(value = "联系人")
	private String linkman;

	@ApiModelProperty(value = "联系电话")
	private String contactNumber;

	@ApiModelProperty(value = "联系地址")
	private String address;

	@ApiModelProperty(value = "账号额度")
	private Integer accountNumber;

	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@ApiModelProperty(value = "过期时间")
	private Date expireTime;

	@JsonSerialize(nullsUsing = NullSerializer.class)
	@ApiModelProperty(value = "数据源ID")
	private Long datasourceId;

	@ApiModelProperty(value = "授权码")
	private String licenseKey;
}
