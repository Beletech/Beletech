package com.beletech.payment.handler.impl;

import com.beletech.payment.handler.MessageDuplicateCheckerHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 消息去重
 */
@Service
@AllArgsConstructor
public class MessageRedisDuplicateCheckerHandler implements MessageDuplicateCheckerHandler {

	private final RedisTemplate redisTemplate;

	/**
	 * 判断回调消息是否重复.
	 * @param messageId messageId需要根据上面讲的方式构造
	 * @return 如果是重复消息true，否则返回false
	 */
	@Override
	public boolean isDuplicate(String messageId) {
		return !redisTemplate.opsForValue().setIfAbsent(messageId, messageId, Duration.ofSeconds(10L));
	}

}
