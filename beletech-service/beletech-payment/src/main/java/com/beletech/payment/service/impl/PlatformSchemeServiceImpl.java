package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PlatformScheme;
import com.beletech.payment.mapper.PlatformSchemeMapper;
import com.beletech.payment.service.PlatformSchemeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 平台方案
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformSchemeServiceImpl extends ServiceImpl<PlatformSchemeMapper, PlatformScheme>
	implements PlatformSchemeService {
}
