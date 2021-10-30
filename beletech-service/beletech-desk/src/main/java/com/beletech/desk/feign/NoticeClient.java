package com.beletech.desk.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.beletech.core.mp.support.BeletechPage;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.desk.entity.Notice;
import com.beletech.desk.service.INoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Notice Feign
 *
 * @author XueBing
 */
@NonDS
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	private final INoticeService service;

	@Override
	@GetMapping(TOP)
	public BeletechPage<Notice> top(Integer current, Integer size) {
		Query query = new Query();
		query.setCurrent(current);
		query.setSize(size);
		IPage<Notice> page = service.page(Condition.getPage(query));
		return BeletechPage.of(page);
	}

}
