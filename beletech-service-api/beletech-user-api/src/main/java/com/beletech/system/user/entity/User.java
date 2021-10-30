package com.beletech.system.user.entity;

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
@TableName("base_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户编号")
	private String code;

	@ApiModelProperty("用户平台")
	private Integer userType;

	@ApiModelProperty("账号")
	private String account;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("昵称")
	private String name;

	@ApiModelProperty("真名")
	private String realName;

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("手机")
	private String phone;

	@ApiModelProperty("生日")
	private Date birthday;

	@ApiModelProperty("性别")
	private Integer sex;

	@ApiModelProperty("角色id")
	private String roleId;

	@ApiModelProperty("部门id")
	private String deptId;

	@ApiModelProperty("岗位id")
	private String postId;
}
