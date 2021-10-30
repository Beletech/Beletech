package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.system.entity.RoleMenu;
import com.beletech.system.vo.RoleMenuVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 自定义分页
	 *
	 * @param page     分页对象
	 * @param roleMenu 按钮
	 * @return 集合
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

}
