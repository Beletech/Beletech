package com.beletech.core.log.feign;

import lombok.AllArgsConstructor;
import com.beletech.core.log.model.LogApi;
import com.beletech.core.log.model.LogError;
import com.beletech.core.log.model.LogUsual;
import com.beletech.core.log.service.ILogApiService;
import com.beletech.core.log.service.ILogErrorService;
import com.beletech.core.log.service.ILogUsualService;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志服务Feign实现类
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
public class LogClient implements ILogClient {

	private final ILogUsualService usualLogService;

	private final ILogApiService apiLogService;

	private final ILogErrorService errorLogService;

	@Override
	@PostMapping(API_PREFIX + "/saveUsualLog")
	public Result<Boolean> saveUsualLog(@RequestBody LogUsual log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return Result.data(usualLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveApiLog")
	public Result<Boolean> saveApiLog(@RequestBody LogApi log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return Result.data(apiLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveErrorLog")
	public Result<Boolean> saveErrorLog(@RequestBody LogError log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return Result.data(errorLogService.save(log));
	}
}
