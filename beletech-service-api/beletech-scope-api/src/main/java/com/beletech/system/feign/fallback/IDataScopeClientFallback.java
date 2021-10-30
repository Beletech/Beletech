package com.beletech.system.feign.fallback;

import com.beletech.core.datascope.model.DataScopeModel;
import com.beletech.system.feign.IDataScopeClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IDataScopeClientFallback
 *
 * @author XueBing
 */
@Component
public class IDataScopeClientFallback implements IDataScopeClient {
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		return null;
	}

	@Override
	public DataScopeModel getDataScopeByCode(String code) {
		return null;
	}

	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		return null;
	}
}
