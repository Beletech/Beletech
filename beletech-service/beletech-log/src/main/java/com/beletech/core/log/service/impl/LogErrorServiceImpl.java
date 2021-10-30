package com.beletech.core.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.core.log.mapper.LogErrorMapper;
import com.beletech.core.log.model.LogError;
import com.beletech.core.log.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author XueBing
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {

}
