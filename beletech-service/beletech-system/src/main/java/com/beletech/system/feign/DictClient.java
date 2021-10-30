package com.beletech.system.feign;


import lombok.AllArgsConstructor;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.Dict;
import com.beletech.system.service.IDictService;
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
public class DictClient implements IDictClient {

	private final IDictService service;

	@Override
	@GetMapping(GET_BY_ID)
	public Result<Dict> getById(Long id) {
		return Result.data(service.getById(id));
	}

	@Override
	@GetMapping(GET_VALUE)
	public Result<String> getValue(String code, String dictKey) {
		return Result.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(GET_LIST)
	public Result<List<Dict>> getList(String code) {
		return Result.data(service.getList(code));
	}

}
