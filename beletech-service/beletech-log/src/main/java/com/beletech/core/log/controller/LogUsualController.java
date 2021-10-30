package com.beletech.core.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.beletech.core.log.model.LogUsual;
import com.beletech.core.log.service.ILogUsualService;
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
@RequestMapping("/usual")
public class LogUsualController {

	private final ILogUsualService logService;

	@GetMapping("/detail")
	public Result<LogUsual> detail(LogUsual log) {
		return Result.data(logService.getOne(Condition.getQueryWrapper(log)));
	}

	@GetMapping("/list")
	public Result<IPage<LogUsual>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {
		IPage<LogUsual> pages = logService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogUsual.class));
		return Result.data(pages);
	}

}
