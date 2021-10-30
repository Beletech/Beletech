package com.beletech.gateway.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.beletech.gateway.handler.ErrorExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 异常处理配置类
 *
 * @author XueBing
 */
@Configuration
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ErrorHandlerConfiguration {

	@Bean
	public ErrorExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
		return new ErrorExceptionHandler(objectMapper);
	}

}
