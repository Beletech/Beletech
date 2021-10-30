package com.beletech.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * TopMenuSetting
 *
 * @author XueBing
 */
@Data
@TableName("base_top_menu_setting")
public class TopMenuSetting {

	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long topMenuId;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
}
