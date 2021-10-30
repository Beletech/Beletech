package com.beletech.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.resource.entity.Attach;
import com.beletech.resource.vo.AttachVO;

import java.util.List;

/**
 * 附件表 Mapper 接口
 *
 * @author XueBing
 */
public interface AttachMapper extends BaseMapper<Attach> {

	/**
	 * 自定义分页
	 *
	 * @param page   分页对象
	 * @param attach 查询参数
	 * @return 分页对象
	 */
	List<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach);
}
