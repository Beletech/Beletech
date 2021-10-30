package com.beletech.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.RoleMenu;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleMenuVO对象", description = "RoleMenuVO对象")
public class RoleMenuVO extends RoleMenu {
	private static final long serialVersionUID = 1L;

}
