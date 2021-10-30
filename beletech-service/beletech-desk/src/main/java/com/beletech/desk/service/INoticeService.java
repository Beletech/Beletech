package com.beletech.desk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.desk.entity.Notice;
import com.beletech.desk.vo.NoticeVO;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface INoticeService extends BaseService<Notice> {

	/**
	 * 自定义分页
	 *
	 * @param page   分页对象
	 * @param notice 查询参数
	 * @return 分页数据
	 */
	IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO notice);

}
