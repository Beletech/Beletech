package com.beletech.desk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.desk.entity.Notice;
import com.beletech.desk.mapper.NoticeMapper;
import com.beletech.desk.service.INoticeService;
import com.beletech.desk.vo.NoticeVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author XueBing
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO notice) {
		// 若不使用mybatis-plus自带的分页方法，则不会自动带入tenantId，所以我们需要自行注入
		notice.setTenantId(AuthUtil.getTenantId());
		return page.setRecords(baseMapper.selectNoticePage(page, notice));
	}

}
