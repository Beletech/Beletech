package com.beletech.resource.mapper;

import com.beletech.resource.entity.Sms;
import com.beletech.resource.vo.SmsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 短信配置表 Mapper 接口
 *
 * @author Beletech
 */
public interface SmsMapper extends BaseMapper<Sms> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param sms  查询参数
	 * @return 分页对象
	 */
	List<SmsVO> selectSmsPage(IPage<SmsVO> page, SmsVO sms);

}
