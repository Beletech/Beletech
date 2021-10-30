package com.beletech.swagger;

import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * swagger聚合启动器
 *
 * @author XueBing
 */
@SpringCloudApplication
public class SwaggerApplication {

	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_SWAGGER_NAME, SwaggerApplication.class, args);
	}

}
