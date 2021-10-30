package com.beletech.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.system.entity.Region;
import com.beletech.system.excel.RegionExcel;
import com.beletech.system.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务类
 *
 * @author XueBing
 */
public interface IRegionService extends IService<Region> {

	/**
	 * 提交
	 *
	 * @param region 参数
	 * @return 状态
	 */
	boolean submit(Region region);

	/**
	 * 删除
	 *
	 * @param id id
	 * @return 状态
	 */
	boolean removeRegion(String id);

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
	 * 导入区划数据
	 *
	 * @param data      参数
	 * @param isCovered is
	 */
	void importRegion(List<RegionExcel> data, Boolean isCovered);

	/**
	 * 导出区划数据
	 *
	 * @param queryWrapper 参数
	 * @return 集合
	 */
	List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper);

}
