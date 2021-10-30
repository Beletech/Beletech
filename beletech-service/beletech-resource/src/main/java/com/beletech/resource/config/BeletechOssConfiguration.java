package com.beletech.resource.config;

import lombok.AllArgsConstructor;
import com.beletech.core.oss.props.OssProperties;
import com.beletech.resource.builder.oss.OssBuilder;
import com.beletech.resource.service.IOssService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Oss配置类
 *
 * @author XueBing
 */
@Configuration
@AllArgsConstructor
public class BeletechOssConfiguration {

	private final OssProperties ossProperties;

	private final IOssService ossService;

	@Bean
	public OssBuilder ossBuilder() {
		return new OssBuilder(ossProperties, ossService);
	}

}
