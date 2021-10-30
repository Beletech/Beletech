package com.beletech.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author XueBing
 */
@Data
@TableName("base_user_dept")
@ApiModel(value = "UserDept对象", description = "UserDept对象")
public class UserDept implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "部门ID")
	private Long deptId;
}
