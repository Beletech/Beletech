package com.beletech.resource.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.resource.entity.Attach;
import com.beletech.resource.mapper.AttachMapper;
import com.beletech.resource.service.IAttachService;
import com.beletech.resource.vo.AttachVO;
import org.springframework.stereotype.Service;

/**
 * 附件表 服务实现类
 *
 * @author XueBing
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<AttachMapper, Attach> implements IAttachService {

	@Override
	public IPage<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach) {
		return page.setRecords(baseMapper.selectAttachPage(page, attach));
	}

}
