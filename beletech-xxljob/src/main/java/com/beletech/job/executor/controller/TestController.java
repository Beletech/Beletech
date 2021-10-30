package com.beletech.job.executor.controller;

import com.beletech.core.tool.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试定时请求
 *
 * @author job
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("testRequest")
	public Result testRequest(String name) {
		return Result.data("我是测试请求" + name);
	}


}
