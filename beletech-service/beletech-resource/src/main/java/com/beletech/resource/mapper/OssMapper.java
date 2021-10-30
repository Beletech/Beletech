package com.beletech.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.resource.entity.Oss;
import com.beletech.resource.vo.OssVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Beletech
 */
public interface OssMapper extends BaseMapper<Oss> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param oss  查询参数
	 * @return 分页列表
	 */
	List<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss);
}
