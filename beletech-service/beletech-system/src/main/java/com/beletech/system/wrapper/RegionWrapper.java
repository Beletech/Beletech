package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.node.ForestNodeMerger;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.system.cache.RegionCache;
import com.beletech.system.entity.Region;
import com.beletech.system.vo.RegionVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = RegionCache.getByCode(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<RegionVO> listNodeLazyVO(List<RegionVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
