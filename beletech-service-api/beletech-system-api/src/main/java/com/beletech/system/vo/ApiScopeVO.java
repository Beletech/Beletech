package com.beletech.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.ApiScope;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiScopeVO对象", description = "ApiScopeVO对象")
public class ApiScopeVO extends ApiScope {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则类型名
	 */
	private String scopeTypeName;
}
