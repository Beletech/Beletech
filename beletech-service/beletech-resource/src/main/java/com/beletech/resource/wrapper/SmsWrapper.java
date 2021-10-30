package com.beletech.resource.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.resource.entity.Sms;
import com.beletech.resource.vo.SmsVO;
import com.beletech.system.cache.DictCache;
import com.beletech.system.enums.DictEnum;

import java.util.Objects;

/**
 * 短信配置表包装类,返回视图层所需的字段
 *
 * @author Beletech
 */
public class SmsWrapper extends BaseEntityWrapper<Sms, SmsVO> {

	public static SmsWrapper build() {
		return new SmsWrapper();
	}

	@Override
	public SmsVO entityVO(Sms sms) {
		SmsVO smsVO = Objects.requireNonNull(BeanUtil.copy(sms, SmsVO.class));
		String categoryName = DictCache.getValue(DictEnum.SMS, sms.getCategory());
		String statusName = DictCache.getValue(DictEnum.YES_NO, sms.getStatus());
		smsVO.setCategoryName(categoryName);
		smsVO.setStatusName(statusName);
		return smsVO;
	}

}
