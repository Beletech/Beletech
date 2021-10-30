package com.beletech.auth;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 用户认证服务器
 *
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
public class AuthApplication {

	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}
