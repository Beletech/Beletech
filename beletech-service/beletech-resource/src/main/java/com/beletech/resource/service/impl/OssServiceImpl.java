package com.beletech.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.log.exception.ServiceException;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.entity.Oss;
import com.beletech.resource.vo.OssVO;
import com.beletech.resource.mapper.OssMapper;
import com.beletech.resource.service.IOssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现类
 *
 * @author Beletech
 */
@Service
public class OssServiceImpl extends BaseServiceImpl<OssMapper, Oss> implements IOssService {

	@Override
	public IPage<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss) {
		return page.setRecords(baseMapper.selectOssPage(page, oss));
	}

	@Override
	public boolean submit(Oss oss) {
		LambdaQueryWrapper<Oss> lqw = Wrappers.<Oss>query().lambda()
			.eq(Oss::getOssCode, oss.getOssCode()).eq(Oss::getTenantId, AuthUtil.getTenantId());
		Integer cnt = baseMapper.selectCount(Func.isEmpty(oss.getId()) ? lqw : lqw.notIn(Oss::getId, oss.getId()));
		if (cnt > 0) {
			throw new ServiceException("当前资源编号已存在!");
		}
		return this.saveOrUpdate(oss);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean enable(Long id) {
		// 先禁用
		boolean temp1 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getStatus, 1));
		// 在启用
		boolean temp2 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getStatus, 2).eq(Oss::getId, id));
		return temp1 && temp2;
	}

}
