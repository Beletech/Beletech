package com.beletech.system.handler;

import com.beletech.core.datascope.handler.ScopeModelHandler;
import com.beletech.core.datascope.model.DataScopeModel;
import com.beletech.system.cache.DataScopeCache;

import java.util.List;

/**
 * 通用数据权限规则
 *
 * @author XueBing
 */
public class DataScopeModelHandler implements ScopeModelHandler {

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		return DataScopeCache.getDataScopeByMapper(mapperId, roleId);
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByCode(String code) {
		return DataScopeCache.getDataScopeByCode(code);
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		return DataScopeCache.getDeptAncestors(deptId);
	}
}
