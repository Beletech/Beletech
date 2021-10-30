package com.beletech.admin.config;

import com.beletech.admin.dingtalk.MonitorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 启动器
 *
 * @author XueBing
 */
@Configuration
@EnableConfigurationProperties(MonitorProperties.class)
public class AdminConfiguration {

}
