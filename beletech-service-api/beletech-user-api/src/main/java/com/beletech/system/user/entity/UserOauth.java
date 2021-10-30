package com.beletech.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_user_oauth")
public class UserOauth implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private String tenantId;

	private String uuid;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户主键")
	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;

	@ApiModelProperty(value = "用户头像")
	private String avatar;

	@ApiModelProperty(value = "用户网址")
	private String blog;

	@ApiModelProperty(value = "所在公司")
	private String company;

	@ApiModelProperty(value = "位置")
	private String location;

	@ApiModelProperty(value = "用户邮箱")
	private String email;

	@ApiModelProperty(value = "用户备注")
	private String remark;

	@ApiModelProperty(value = "性别")
	private String gender;

	@ApiModelProperty(value = "用户来源")
	private String source;
}
