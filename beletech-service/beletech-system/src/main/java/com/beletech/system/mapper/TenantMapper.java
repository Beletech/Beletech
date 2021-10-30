package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.system.entity.Tenant;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page   分页对象
	 * @param tenant 租户id
	 * @return 集合
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);

}
