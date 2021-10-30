package com.beletech.system.cache;

import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.utils.SpringUtil;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.enums.DictBizEnum;
import com.beletech.system.feign.IDictBizClient;

import java.util.List;

import static com.beletech.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 业务字典缓存工具类
 *
 * @author XueBing
 */
public class DictBizCache {

	private static final String DICT_ID = "dictBiz:id";
	private static final String DICT_VALUE = "dictBiz:value";
	private static final String DICT_LIST = "dictBiz:list";

	private static IDictBizClient dictClient;

	private static IDictBizClient getDictClient() {
		if (dictClient == null) {
			dictClient = SpringUtil.getBean(IDictBizClient.class);
		}
		return dictClient;
	}

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return DictBiz
	 */
	public static DictBiz getById(Long id) {
		String keyPrefix = DICT_ID.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix, id, () -> {
			Result<DictBiz> result = getDictClient().getById(id);
			return result.getData();
		});
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号枚举
	 * @param dictKey Integer型字典键
	 * @return String
	 */
	public static String getValue(DictBizEnum code, Integer dictKey) {
		return getValue(code.getName(), dictKey);
	}


	/**
	 * 获取字典值
	 *
	 * @param code    字典编号
	 * @param dictKey Integer型字典键
	 * @return String
	 */
	public static String getValue(String code, Integer dictKey) {
		String keyPrefix = DICT_VALUE.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix + code + StringPool.COLON, String.valueOf(dictKey), () -> {
			Result<String> result = getDictClient().getValue(code, String.valueOf(dictKey));
			return result.getData();
		});
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号枚举
	 * @param dictKey String型字典键
	 * @return String
	 */
	public static String getValue(DictBizEnum code, String dictKey) {
		return getValue(code.getName(), dictKey);
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号
	 * @param dictKey String型字典键
	 * @return String
	 */
	public static String getValue(String code, String dictKey) {
		String keyPrefix = DICT_VALUE.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix + code + StringPool.COLON, dictKey, () -> {
			Result<String> result = getDictClient().getValue(code, dictKey);
			return result.getData();
		});
	}

	/**
	 * 获取字典集合
	 *
	 * @param code 字典编号
	 * @return List<DictBiz>
	 */
	public static List<DictBiz> getList(String code) {
		String keyPrefix = DICT_LIST.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix, code, () -> {
			Result<List<DictBiz>> result = getDictClient().getList(code);
			return result.getData();
		});
	}

}
