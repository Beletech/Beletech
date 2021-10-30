package com.beletech.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.system.entity.TopMenu;
import com.beletech.system.entity.TopMenuSetting;
import com.beletech.system.mapper.TopMenuMapper;
import com.beletech.system.service.ITopMenuService;
import com.beletech.system.service.ITopMenuSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 顶部菜单表 服务实现类
 *
 * @author Beletech
 */
@Service
@AllArgsConstructor
public class TopMenuServiceImpl extends BaseServiceImpl<TopMenuMapper, TopMenu> implements ITopMenuService {

	private final ITopMenuSettingService topMenuSettingService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds) {
		// 删除顶部菜单配置的菜单集合
		topMenuSettingService.remove(Wrappers.<TopMenuSetting>update().lambda().in(TopMenuSetting::getTopMenuId, topMenuIds));
		// 组装配置
		List<TopMenuSetting> menuSettings = new ArrayList<>();
		topMenuIds.forEach(topMenuId -> menuIds.forEach(menuId -> {
			TopMenuSetting menuSetting = new TopMenuSetting();
			menuSetting.setTopMenuId(topMenuId);
			menuSetting.setMenuId(menuId);
			menuSettings.add(menuSetting);
		}));
		// 新增配置
		topMenuSettingService.saveBatch(menuSettings);
		return true;
	}

}
