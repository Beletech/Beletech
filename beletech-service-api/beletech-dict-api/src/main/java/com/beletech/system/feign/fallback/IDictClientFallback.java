package com.beletech.system.feign.fallback;

import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.Dict;
import com.beletech.system.feign.IDictClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author XueBing
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public Result<Dict> getById(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getValue(String code, String dictKey) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<Dict>> getList(String code) {
		return Result.fail("获取数据失败");
	}
}
