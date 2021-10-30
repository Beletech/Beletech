package com.beletech.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.resource.entity.Oss;
import com.beletech.resource.vo.OssVO;

/**
 * 服务类
 *
 * @author Beletech
 */
public interface IOssService extends BaseService<Oss> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param oss  查询参数
	 * @return 分页对象
	 */
	IPage<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss);

	/**
	 * 提交oss信息
	 *
	 * @param oss 参数
	 * @return 状态
	 */
	boolean submit(Oss oss);

	/**
	 * 启动配置
	 *
	 * @param id 参数
	 * @return 状态
	 */
	boolean enable(Long id);

}
