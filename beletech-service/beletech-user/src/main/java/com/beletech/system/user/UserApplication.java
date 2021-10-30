package com.beletech.system.user;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 用户启动器
 *
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
public class UserApplication {
	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
	}
}
