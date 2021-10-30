package com.beletech.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.resource.entity.Sms;
import com.beletech.resource.vo.SmsVO;

/**
 * 短信配置表 服务类
 *
 * @author Beletech
 */
public interface ISmsService extends BaseService<Sms> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param sms  查询参数
	 * @return 分页对象
	 */
	IPage<SmsVO> selectSmsPage(IPage<SmsVO> page, SmsVO sms);

	/**
	 * 提交oss信息
	 *
	 * @param oss 参数
	 * @return 状态
	 */
	boolean submit(Sms oss);

	/**
	 * 启动配置
	 *
	 * @param id 参数
	 * @return 状态
	 */
	boolean enable(Long id);

}
