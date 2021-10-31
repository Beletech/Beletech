package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PayRefundOrder;
import com.beletech.payment.mapper.PayRefundOrderMapper;
import com.beletech.payment.service.PayRefundOrderService;
import org.springframework.stereotype.Service;

/**
 * 退款
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Service
public class PayRefundOrderServiceImpl extends ServiceImpl<PayRefundOrderMapper, PayRefundOrder>
	implements PayRefundOrderService {

}
