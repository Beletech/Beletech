package com.beletech.payment;

import com.beletech.common.constant.AppConstant;
import com.beletech.core.cloud.feign.EnableBeletechFeign;
import com.beletech.core.launch.BeletechApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 支付启动类
 *
 * @author XueBing
 * @date 2021-10-31
 */
@EnableBeletechFeign
@SpringCloudApplication
public class PaymentController {
	public static void main(String[] args) {
		BeletechApplication.run(AppConstant.PAYMENT_NAME, PaymentController.class, args);
	}
}
