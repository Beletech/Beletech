package com.beletech.admin.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import com.beletech.admin.dingtalk.DingTalkNotifier;
import com.beletech.admin.dingtalk.DingTalkService;
import com.beletech.admin.dingtalk.MonitorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 钉钉自动配置
 *
 * @author XueBing
 */
@Configuration
@ConditionalOnProperty(value = "monitor.ding-talk.enabled", havingValue = "true")
public class DingTalkConfiguration {

	@Bean
	public DingTalkService dingTalkService(MonitorProperties properties,
										   WebClient.Builder builder) {
		return new DingTalkService(properties, builder.build());
	}

	@Bean
	public DingTalkNotifier dingTalkNotifier(MonitorProperties properties,
											 DingTalkService dingTalkService,
											 InstanceRepository repository,
											 Environment environment) {
		return new DingTalkNotifier(dingTalkService, properties, environment, repository);
	}

}
