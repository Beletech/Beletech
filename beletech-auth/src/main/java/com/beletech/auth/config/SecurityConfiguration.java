package com.beletech.auth.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.beletech.auth.support.BeletechPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置
 *
 * @author XueBing
 */
@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return BeletechPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.httpBasic().and().csrf().disable().authorizeRequests().anyRequest().fullyAuthenticated();
	}

}
