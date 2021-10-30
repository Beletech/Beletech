package com.beletech.system.cache;

import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.SpringUtil;
import com.beletech.system.entity.Param;
import com.beletech.system.feign.ISysClient;

import static com.beletech.core.cache.constant.CacheConstant.PARAM_CACHE;

/**
 * 参数缓存工具类
 *
 * @author XueBing
 */
public class ParamCache {

	private static final String PARAM_ID = "param:id:";
	private static final String PARAM_VALUE = "param:value:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}

	/**
	 * 获取参数实体
	 *
	 * @param id 主键
	 * @return Param
	 */
	public static Param getById(Long id) {
		return CacheUtil.get(PARAM_CACHE, PARAM_ID, id, () -> {
			Result<Param> result = getSysClient().getParam(id);
			return result.getData();
		});
	}

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数值
	 * @return String
	 */
	public static String getValue(String paramKey) {
		return CacheUtil.get(PARAM_CACHE, PARAM_VALUE, paramKey, () -> {
			Result<String> result = getSysClient().getParamValue(paramKey);
			return result.getData();
		});
	}

}
