package com.beletech.system;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 系统模块启动器
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
public class SystemApplication {
	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
	}
}
