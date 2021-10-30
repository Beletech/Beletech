package com.beletech.gateway;

import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动
 *
 * @author XueBing
 */
@EnableHystrix
@EnableScheduling
@SpringCloudApplication
public class GateWayApplication {

	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
	}

}
