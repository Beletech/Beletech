package com.beletech.admin;

import com.beletech.core.launch.BeletechApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * admin启动器
 *
 * @author XueBing
 */
@EnableAdminServer
@SpringCloudApplication
public class AdminApplication {

	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_ADMIN_NAME, AdminApplication.class, args);
	}

}
