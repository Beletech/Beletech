package com.beletech.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_user_other")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserOther对象", description = "UserOther对象")
public class UserOther extends Model<UserOther> {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "用户拓展信息")
	private String userExt;
}
