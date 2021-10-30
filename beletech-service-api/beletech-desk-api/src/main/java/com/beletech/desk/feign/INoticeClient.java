package com.beletech.desk.feign;

import com.beletech.core.launch.constant.AppConstant;
import com.beletech.core.mp.support.BeletechPage;
import com.beletech.desk.entity.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notice Feign接口类
 *
 * @author XueBing
 */
@FeignClient(
	value = AppConstant.APPLICATION_DESK_NAME
)
public interface INoticeClient {

	String API_PREFIX = "/client";
	String TOP = API_PREFIX + "/top";

	/**
	 * 获取notice列表
	 *
	 * @param current 分页
	 * @param size    数量
	 * @return 分页对象
	 */
	@GetMapping(TOP)
	BeletechPage<Notice> top(@RequestParam("current") Integer current, @RequestParam("size") Integer size);
}
