package com.beletech.system.feign;


import com.beletech.core.launch.constant.AppConstant;
import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.Dict;
import com.beletech.system.feign.fallback.IDictClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author XueBing
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDictClientFallback.class
)
public interface IDictClient {

	String API_PREFIX = "/client";
	String GET_BY_ID = API_PREFIX + "/dict/get-by-id";
	String GET_VALUE = API_PREFIX + "/dict/get-value";
	String GET_LIST = API_PREFIX + "/dict/get-list";

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping(GET_BY_ID)
	Result<Dict> getById(@RequestParam("id") Long id);

	/**
	 * 获取字典表对应值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	@GetMapping(GET_VALUE)
	Result<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	@GetMapping(GET_LIST)
	Result<List<Dict>> getList(@RequestParam("code") String code);

}
