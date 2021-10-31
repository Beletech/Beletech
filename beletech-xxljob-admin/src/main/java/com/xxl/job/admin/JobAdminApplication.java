package com.xxl.job.admin;

import com.beletech.core.launch.BeletechApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class JobAdminApplication {

	public static void main(String[] args) {
		BeletechApplication.run("", JobAdminApplication.class, args);
	}

}
