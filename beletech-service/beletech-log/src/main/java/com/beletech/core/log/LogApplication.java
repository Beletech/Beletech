package com.beletech.core.log;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 日志服务
 *
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
public class LogApplication {
	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_LOG_NAME, LogApplication.class, args);
	}
}
