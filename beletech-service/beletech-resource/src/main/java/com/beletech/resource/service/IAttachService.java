package com.beletech.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.resource.entity.Attach;
import com.beletech.resource.vo.AttachVO;

/**
 * 附件表 服务类
 *
 * @author XueBing
 */
public interface IAttachService extends BaseService<Attach> {

	/**
	 * 自定义分页
	 *
	 * @param page   分页对象
	 * @param attach 查询参数
	 * @return 分页对象
	 */
	IPage<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach);
}
