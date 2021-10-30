package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beletech.system.entity.Region;
import com.beletech.system.excel.RegionExcel;
import com.beletech.system.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 Mapper 接口
 *
 * @author XueBing
 */
public interface RegionMapper extends BaseMapper<Region> {

	/**
	 * 懒加载列表
	 *
	 * @param parentCode 父code
	 * @param param      参数
	 * @return 集合
	 */
	List<RegionVO> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode 父code
	 * @param param      参数
	 * @return 集合
	 */
	List<RegionVO> lazyTree(String parentCode, Map<String, Object> param);

	/**
	 * 导出区划数据
	 *
	 * @param queryWrapper 参数
	 * @return 集合
	 */
	List<RegionExcel> exportRegion(@Param("ew") Wrapper<Region> queryWrapper);

}
