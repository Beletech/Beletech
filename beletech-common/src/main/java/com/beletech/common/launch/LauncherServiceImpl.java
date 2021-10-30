package com.beletech.common.launch;

import com.beletech.common.constant.LauncherConstant;
import com.beletech.core.auto.service.AutoService;
import com.beletech.core.launch.service.LauncherService;
import com.beletech.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author smallchil
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// 通用注册
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
		PropsUtil.setProperty(props, "spring.zipkin.base-url", LauncherConstant.zipkinAddr(profile));
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");

		// 开启elk日志
		// PropsUtil.setProperty(props, "beletech.log.elk.destination", LauncherConstant.elkAddr(profile));

		// seata注册地址
		// PropsUtil.setProperty(props, "seata.service.grouplist.default", LauncherConstant.seataAddr(profile));
		// seata注册group格式
		// PropsUtil.setProperty(props, "seata.tx-service-group", LauncherConstant.seataServiceGroup(appName));
		// seata配置服务group
		// PropsUtil.setProperty(props, "seata.service.vgroup-mapping.".concat(LauncherConstant.seataServiceGroup(appName)), LauncherConstant.DEFAULT_MODE);
		// seata注册模式配置
		// PropsUtil.setProperty(props, "seata.registry.type", LauncherConstant.NACOS_MODE);
		// PropsUtil.setProperty(props, "seata.registry.nacos.server-addr", LauncherConstant.nacosAddr(profile));
		// PropsUtil.setProperty(props, "seata.config.type", LauncherConstant.NACOS_MODE);
		// PropsUtil.setProperty(props, "seata.config.nacos.server-addr", LauncherConstant.nacosAddr(profile));
	}

}
