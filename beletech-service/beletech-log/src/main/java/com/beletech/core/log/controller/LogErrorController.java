package com.beletech.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.beletech.core.log.model.LogError;
import com.beletech.core.log.service.ILogErrorService;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/error")
public class LogErrorController {

	private final ILogErrorService errorLogService;

	@GetMapping("/detail")
	public Result<LogError> detail(LogError logError) {
		return Result.data(errorLogService.getOne(Condition.getQueryWrapper(logError)));
	}

	@GetMapping("/list")
	public Result<IPage<LogError>> list(@ApiIgnore @RequestParam Map<String, Object> logError, Query query) {
		IPage<LogError> pages = errorLogService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(logError, LogError.class));
		return Result.data(pages);
	}

}
