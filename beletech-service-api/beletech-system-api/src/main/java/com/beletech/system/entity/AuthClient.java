package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author Beletech
 */
@Data
@TableName("base_client")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Client对象", description = "Client对象")
public class AuthClient extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "客户端id")
	private String clientId;

	@ApiModelProperty(value = "客户端密钥")
	private String clientSecret;

	@ApiModelProperty(value = "资源集合")
	private String resourceIds;

	@ApiModelProperty(value = "授权范围")
	private String scope;

	@ApiModelProperty(value = "授权类型")
	private String authorizedGrantTypes;

	@ApiModelProperty(value = "回调地址")
	private String webServerRedirectUri;

	@ApiModelProperty(value = "权限")
	private String authorities;

	@ApiModelProperty(value = "令牌过期秒数")
	private Integer accessTokenValidity;

	@ApiModelProperty(value = "刷新令牌过期秒数")
	private Integer refreshTokenValidity;

	@JsonIgnore
	@ApiModelProperty(value = "附加说明")
	private String additionalInformation;

	@ApiModelProperty(value = "自动授权")
	private String autoapprove;
}
