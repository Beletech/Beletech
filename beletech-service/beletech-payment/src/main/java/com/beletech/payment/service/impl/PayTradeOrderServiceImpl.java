package com.beletech.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.payment.entity.PayTradeOrder;
import com.beletech.payment.mapper.PayTradeOrderMapper;
import com.beletech.payment.service.PayTradeOrderService;
import org.springframework.stereotype.Service;

/**
 * 支付
 *
 * @author XueBing
 * @date 2021-10-31
 */
@Service
public class PayTradeOrderServiceImpl extends ServiceImpl<PayTradeOrderMapper, PayTradeOrder>
	implements PayTradeOrderService {

}
