package com.beletech.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.DataScope;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DataScopeVO对象", description = "DataScopeVO对象")
public class DataScopeVO extends DataScope {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则类型名
	 */
	private String scopeTypeName;
}
