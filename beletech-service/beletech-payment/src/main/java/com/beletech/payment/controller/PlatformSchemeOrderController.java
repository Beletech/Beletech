package com.beletech.payment.controller;

import cn.hutool.http.ContentType;
import com.beletech.payment.entity.PlatformSchemeOrder;
import com.beletech.payment.service.PlatformSchemeOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 平台费用记录控制器
 *
 * @author XueBing
 * @date 2021-11-10
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/platform-scheme-order")
@Api(value = "platform-scheme-order", tags = "平台费用记录")
public class PlatformSchemeOrderController {

	private final ObjectMapper objectMapper;

	private final PlatformSchemeOrderService platformSchemeOrderService;

	@SneakyThrows
	@GetMapping("/merge/buy")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "商品订单", notes = "商品订单")
	public void mergeBuy(PlatformSchemeOrder platformSchemeOrder, HttpServletResponse response) {
		Map<String, Object> result = platformSchemeOrderService.buy(platformSchemeOrder, true);
		response.setContentType(ContentType.JSON.getValue());
		response.getWriter().print(objectMapper.writeValueAsString(result));
	}
}
