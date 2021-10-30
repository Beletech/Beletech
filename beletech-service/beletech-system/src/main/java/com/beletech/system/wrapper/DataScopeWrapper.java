package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.system.cache.DictCache;
import com.beletech.system.entity.DataScope;
import com.beletech.system.enums.DictEnum;
import com.beletech.system.vo.DataScopeVO;

import java.util.Objects;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class DataScopeWrapper extends BaseEntityWrapper<DataScope, DataScopeVO> {

	public static DataScopeWrapper build() {
		return new DataScopeWrapper();
	}

	@Override
	public DataScopeVO entityVO(DataScope dataScope) {
		DataScopeVO dataScopeVO = Objects.requireNonNull(BeanUtil.copy(dataScope, DataScopeVO.class));
		String scopeTypeName = DictCache.getValue(DictEnum.DATA_SCOPE_TYPE, dataScope.getScopeType());
		dataScopeVO.setScopeTypeName(scopeTypeName);
		return dataScopeVO;
	}

}
