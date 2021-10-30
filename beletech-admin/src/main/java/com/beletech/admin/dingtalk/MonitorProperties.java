package com.beletech.admin.dingtalk;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 监控配置
 *
 * @author XueBing
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("monitor")
public class MonitorProperties {
	private DingTalk dingTalk = new DingTalk();

	@Getter
	@Setter
	public static class DingTalk {
		/**
		 * 启用钉钉告警，默认为 true
		 */
		private boolean enabled = false;
		/**
		 * 钉钉机器人 token
		 */
		private String accessToken;
		/**
		 * 签名：如果有 secret 则进行签名，兼容老接口
		 */
		private String secret;
		/**
		 * 地址配置
		 */
		private String link;
		private Service service = new Service();
	}

	@Getter
	@Setter
	public static class Service {
		/**
		 * 服务 状态 title
		 */
		private String title = "服务状态通知";
	}
}
