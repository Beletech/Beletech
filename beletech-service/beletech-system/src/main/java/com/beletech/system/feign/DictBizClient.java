package com.beletech.system.feign;


import lombok.AllArgsConstructor;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.service.IDictBizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 * @author XueBing
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictBizClient implements IDictBizClient {

	private final IDictBizService service;

	@Override
	@GetMapping(GET_BY_ID)
	public Result<DictBiz> getById(Long id) {
		return Result.data(service.getById(id));
	}

	@Override
	@GetMapping(GET_VALUE)
	public Result<String> getValue(String code, String dictKey) {
		return Result.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(GET_LIST)
	public Result<List<DictBiz>> getList(String code) {
		return Result.data(service.getList(code));
	}

}
