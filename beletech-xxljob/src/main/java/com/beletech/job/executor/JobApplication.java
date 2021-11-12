package com.beletech.job.executor;

import com.beletech.core.launch.BeletechApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Job启动器
 *
 * @author XueBing
 */
@SpringCloudApplication
public class JobApplication {

	public static void main(String[] args) {
		BeletechApplication.run("", JobApplication.class, args);
	}
}

