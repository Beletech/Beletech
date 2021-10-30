package com.beletech.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.log.exception.ServiceException;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.entity.Sms;
import com.beletech.resource.mapper.SmsMapper;
import com.beletech.resource.service.ISmsService;
import com.beletech.resource.vo.SmsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短信配置表 服务实现类
 *
 * @author Beletech
 */
@Service
public class SmsServiceImpl extends BaseServiceImpl<SmsMapper, Sms> implements ISmsService {

	@Override
	public IPage<SmsVO> selectSmsPage(IPage<SmsVO> page, SmsVO sms) {
		return page.setRecords(baseMapper.selectSmsPage(page, sms));
	}

	@Override
	public boolean submit(Sms sms) {
		LambdaQueryWrapper<Sms> lqw = Wrappers.<Sms>query().lambda()
			.eq(Sms::getSmsCode, sms.getSmsCode()).eq(Sms::getTenantId, AuthUtil.getTenantId());
		Integer cnt = baseMapper.selectCount(Func.isEmpty(sms.getId()) ? lqw : lqw.notIn(Sms::getId, sms.getId()));
		if (cnt > 0) {
			throw new ServiceException("当前资源编号已存在!");
		}
		return this.saveOrUpdate(sms);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean enable(Long id) {
		// 先禁用
		boolean temp1 = this.update(Wrappers.<Sms>update().lambda().set(Sms::getStatus, 1));
		// 在启用
		boolean temp2 = this.update(Wrappers.<Sms>update().lambda().set(Sms::getStatus, 2).eq(Sms::getId, id));
		return temp1 && temp2;
	}

}
