package com.beletech.system.service;

import com.beletech.core.mp.base.BaseService;
import com.beletech.system.entity.Param;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IParamService extends BaseService<Param> {

	/**
	 * 获取参数值
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	String getValue(String paramKey);

}
