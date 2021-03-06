package com.beletech.system.excel;

import lombok.RequiredArgsConstructor;
import com.beletech.core.excel.support.ExcelImporter;
import com.beletech.system.service.IRegionService;

import java.util.List;

/**
 * 行政区划数据导入类
 *
 * @author XueBing
 */
@RequiredArgsConstructor
public class RegionImporter implements ExcelImporter<RegionExcel> {

	private final IRegionService service;
	private final Boolean isCovered;

	@Override
	public void save(List<RegionExcel> data) {
		service.importRegion(data, isCovered);
	}
}
