package com.beletech.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.core.log.exception.ServiceException;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.system.entity.Region;
import com.beletech.system.excel.RegionExcel;
import com.beletech.system.mapper.RegionMapper;
import com.beletech.system.service.IRegionService;
import com.beletech.system.vo.RegionVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.beletech.system.cache.RegionCache.*;


/**
 * 行政区划表 服务实现类
 *
 * @author XueBing
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

	@Override
	public boolean submit(Region region) {
		Integer cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getCode, region.getCode()));
		if (cnt > 0) {
			return this.updateById(region);
		}
		// 设置祖区划编号
		Region parent = getByCode(region.getParentCode());
		if (Func.isNotEmpty(parent) || Func.isNotEmpty(parent.getCode())) {
			String ancestors = parent.getAncestors() + StringPool.COMMA + parent.getCode();
			region.setAncestors(ancestors);
		}
		// 设置省、市、区、镇、村
		Integer level = region.getRegionLevel();
		String code = region.getCode();
		String name = region.getName();
		if (level == PROVINCE_LEVEL) {
			region.setProvinceCode(code);
			region.setProvinceName(name);
		} else if (level == CITY_LEVEL) {
			region.setCityCode(code);
			region.setCityName(name);
		} else if (level == DISTRICT_LEVEL) {
			region.setDistrictCode(code);
			region.setDistrictName(name);
		} else if (level == TOWN_LEVEL) {
			region.setTownCode(code);
			region.setTownName(name);
		} else if (level == VILLAGE_LEVEL) {
			region.setVillageCode(code);
			region.setVillageName(name);
		}
		return this.save(region);
	}

	@Override
	public boolean removeRegion(String id) {
		Integer cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getParentCode, id));
		if (cnt > 0) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeById(id);
	}

	@Override
	public List<RegionVO> lazyList(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyList(parentCode, param);
	}

	@Override
	public List<RegionVO> lazyTree(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyTree(parentCode, param);
	}

	@Override
	public void importRegion(List<RegionExcel> data, Boolean isCovered) {
		List<Region> list = new ArrayList<>();
		data.forEach(regionExcel -> {
			Region region = BeanUtil.copy(regionExcel, Region.class);
			list.add(region);
		});
		if (isCovered) {
			this.saveOrUpdateBatch(list);
		} else {
			this.saveBatch(list);
		}
	}

	@Override
	public List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper) {
		return baseMapper.exportRegion(queryWrapper);
	}
}
