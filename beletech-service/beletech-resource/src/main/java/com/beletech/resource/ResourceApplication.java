package com.beletech.resource;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 资源启动器
 *
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
public class ResourceApplication {
	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}
}
