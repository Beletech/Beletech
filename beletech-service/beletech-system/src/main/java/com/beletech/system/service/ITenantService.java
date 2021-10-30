package com.beletech.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.system.entity.Tenant;

import java.util.Date;
import java.util.List;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page   分页
	 * @param tenant 参数
	 * @return 分页
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId 租户id
	 * @return 租户信息
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * 新增
	 *
	 * @param tenant 租户
	 * @return 状态
	 */
	boolean submitTenant(Tenant tenant);

	/**
	 * 删除
	 *
	 * @param ids id
	 * @return 状态
	 */
	boolean removeTenant(List<Long> ids);

	/**
	 * 配置租户授权
	 *
	 * @param accountNumber 账号
	 * @param expireTime    时间
	 * @param ids           id
	 * @return 状态
	 */
	boolean setting(Integer accountNumber, Date expireTime, String ids);

}
