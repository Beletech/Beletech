package com.beletech.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

import java.net.URI;

/**
 * 监控安全配置
 *
 * @author XueBing
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {
	private final String contextPath;

	public SecurityConfiguration(AdminServerProperties adminServerProperties) {
		this.contextPath = adminServerProperties.getContextPath();
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		// @formatter:off
		RedirectServerAuthenticationSuccessHandler successHandler = new RedirectServerAuthenticationSuccessHandler();
		successHandler.setLocation(URI.create(contextPath + "/"));
		return http.headers().frameOptions().disable().and()
			.authorizeExchange()
			.pathMatchers(
				contextPath + "/assets/**"
				, contextPath + "/login"
				, contextPath + "/actuator/**"
				, contextPath + "/v1/agent/**"
				, contextPath + "/v1/catalog/**"
				, contextPath + "/v1/health/**"
			).permitAll()
			.anyExchange().authenticated().and()
			.formLogin().loginPage(contextPath + "/login")
			.authenticationSuccessHandler(successHandler).and()
			.logout().logoutUrl(contextPath + "/logout").and()
			.httpBasic().disable()
			.csrf().disable()
			.build();
		// @formatter:on
	}

}
