package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.system.cache.DictCache;
import com.beletech.system.entity.ApiScope;
import com.beletech.system.enums.DictEnum;
import com.beletech.system.vo.ApiScopeVO;

import java.util.Objects;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class ApiScopeWrapper extends BaseEntityWrapper<ApiScope, ApiScopeVO> {

	public static ApiScopeWrapper build() {
		return new ApiScopeWrapper();
	}

	@Override
	public ApiScopeVO entityVO(ApiScope dataScope) {
		ApiScopeVO apiScopeVO = Objects.requireNonNull(BeanUtil.copy(dataScope, ApiScopeVO.class));
		String scopeTypeName = DictCache.getValue(DictEnum.API_SCOPE_TYPE, dataScope.getScopeType());
		apiScopeVO.setScopeTypeName(scopeTypeName);
		return apiScopeVO;
	}

}
