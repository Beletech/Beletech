package com.beletech.system.user.excel;

import lombok.RequiredArgsConstructor;
import com.beletech.core.excel.support.ExcelImporter;
import com.beletech.system.user.service.IUserService;

import java.util.List;

/**
 * 用户数据导入类
 *
 * @author XueBing
 */
@RequiredArgsConstructor
public class UserImporter implements ExcelImporter<UserExcel> {

	private final IUserService service;
	private final Boolean isCovered;

	@Override
	public void save(List<UserExcel> data) {
		service.importUser(data, isCovered);
	}
}
