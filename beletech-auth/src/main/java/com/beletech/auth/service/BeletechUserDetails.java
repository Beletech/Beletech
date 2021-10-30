package com.beletech.auth.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import com.beletech.core.tool.support.Kv;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户信息拓展
 *
 * @author XueBing
 */
@Getter
public class BeletechUserDetails extends User {

	@ApiModelProperty("用户id")
	private final Long userId;

	@ApiModelProperty("租户ID")
	private final String tenantId;

	@ApiModelProperty("第三方认证ID")
	private final String oauthId;

	@ApiModelProperty("昵称")
	private final String name;

	@ApiModelProperty("真名")
	private final String realName;

	@ApiModelProperty("账号")
	private final String account;

	@ApiModelProperty("部门id")
	private final String deptId;

	@ApiModelProperty("岗位id")
	private final String postId;

	@ApiModelProperty("角色id")
	private final String roleId;

	@ApiModelProperty("角色名")
	private final String roleName;

	@ApiModelProperty("头像")
	private final String avatar;

	@ApiModelProperty("用户详情")
	private final Kv detail;

	public BeletechUserDetails(Long userId, String tenantId, String oauthId, String name, String realName, String deptId, String postId, String roleId, String roleName, String avatar, String username, String password, Kv detail, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantId = tenantId;
		this.oauthId = oauthId;
		this.name = name;
		this.realName = realName;
		this.account = username;
		this.deptId = deptId;
		this.postId = postId;
		this.roleId = roleId;
		this.roleName = roleName;
		this.avatar = avatar;
		this.detail = detail;
	}

}
