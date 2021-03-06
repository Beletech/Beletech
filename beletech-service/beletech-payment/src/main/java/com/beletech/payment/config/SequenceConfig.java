package com.beletech.payment.config;

import cn.hutool.core.date.DateUtil;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.sequence.builder.DbSeqBuilder;
import com.beletech.sequence.properties.SequenceDbProperties;
import com.beletech.sequence.sequence.Sequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lengleng
 * @date 2019-05-26
 * <p>
 * 设置发号器生成规则
 */
@Configuration
public class SequenceConfig {

	/**
	 * 订单流水号发号器
	 *
	 * @param dataSource 数据源
	 * @param properties 配置类
	 * @return 发号器
	 */
	@Bean
	public Sequence paySequence(DataSource dataSource, SequenceDbProperties properties) {
		return DbSeqBuilder.create()
			.bizName(() -> String.format("pay_%s_%s", AuthUtil.getTenantId(), DateUtil.today()))
			.dataSource(dataSource).step(properties.getStep()).retryTimes(properties.getRetryTimes())
			.tableName(properties.getTableName()).build();
	}

	/**
	 * 通道编号发号器
	 *
	 * @param dataSource 数据源
	 * @param properties 配置类
	 * @return 发号器
	 */
	@Bean
	public Sequence channelSequence(DataSource dataSource, SequenceDbProperties properties) {
		return DbSeqBuilder.create().bizName(() -> String.format("channel_%s", AuthUtil.getTenantId()))
			.dataSource(dataSource).step(properties.getStep()).retryTimes(properties.getRetryTimes())
			.tableName(properties.getTableName()).build();
	}

}
