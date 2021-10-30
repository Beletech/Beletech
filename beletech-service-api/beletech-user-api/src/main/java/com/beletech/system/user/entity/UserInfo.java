package com.beletech.system.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.beletech.core.tool.support.Kv;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author XueBing
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "第三方授权id")
	private String oauthId;

	@ApiModelProperty(value = "用户")
	private User user;

	@ApiModelProperty(value = "拓展信息")
	private Kv detail;

	@ApiModelProperty(value = "权限集合")
	private List<String> permissions;

	@ApiModelProperty(value = "角色集合")
	private List<String> roles;
}
