package com.beletech.system.config;


import lombok.AllArgsConstructor;
import com.beletech.core.datascope.handler.ScopeModelHandler;
import com.beletech.core.secure.config.RegistryConfiguration;
import com.beletech.core.secure.handler.IPermissionHandler;
import com.beletech.system.handler.ApiScopePermissionHandler;
import com.beletech.system.handler.DataScopeModelHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 * @author XueBing
 */
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(RegistryConfiguration.class)
public class ScopeConfiguration {

	@Bean
	public ScopeModelHandler scopeModelHandler() {
		return new DataScopeModelHandler();
	}

	@Bean
	public IPermissionHandler permissionHandler() {
		return new ApiScopePermissionHandler();
	}

}
