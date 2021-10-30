package com.beletech.desk;

import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import com.beletech.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Desk启动器
 *
 * @author XueBing
 */
@EnableBeletechFeign
@SpringCloudApplication
// @SeataCloudApplication
public class DeskApplication {

	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.APPLICATION_DESK_NAME, DeskApplication.class, args);
	}
}
