package com.beletech.system.feign.fallback;

import com.beletech.core.tool.api.Result;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.feign.IDictBizClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author XueBing
 */
@Component
public class IDictBizClientFallback implements IDictBizClient {
	@Override
	public Result<DictBiz> getById(Long id) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<String> getValue(String code, String dictKey) {
		return Result.fail("获取数据失败");
	}

	@Override
	public Result<List<DictBiz>> getList(String code) {
		return Result.fail("获取数据失败");
	}
}
