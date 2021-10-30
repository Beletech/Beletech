package com.beletech.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.beletech.core.mp.base.BaseServiceImpl;
import com.beletech.system.entity.Param;
import com.beletech.system.mapper.ParamMapper;
import com.beletech.system.service.IParamService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author XueBing
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public String getValue(String paramKey) {
		Param param = this.getOne(Wrappers.<Param>query().lambda().eq(Param::getParamKey, paramKey));
		return param.getParamValue();
	}

}
