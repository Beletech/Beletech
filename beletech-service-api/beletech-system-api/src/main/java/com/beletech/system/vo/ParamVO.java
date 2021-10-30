package com.beletech.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.Param;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ParamVO对象", description = "ParamVO对象")
public class ParamVO extends Param {
	private static final long serialVersionUID = 1L;

}
