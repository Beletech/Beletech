package com.beletech.payment.handler;

import java.util.Map;

/**
 * @author XueBing
 * @date 2021-10-31
 * <p>
 * 支付回调处理器
 */
public interface PayNotifyCallbakHandler {

	/**
	 * 初始化执行
	 *
	 * @param params 参数
	 */
	void before(Map<String, String> params);

	/**
	 * 去重处理
	 *
	 * @param params 回调报文
	 * @return 状态
	 */
	Boolean duplicateChecker(Map<String, String> params);

	/**
	 * 验签逻辑
	 *
	 * @param params 回调报文
	 * @return 状态
	 */
	Boolean verifyNotify(Map<String, String> params);

	/**
	 * 解析报文
	 *
	 * @param params 参数
	 * @return 报文
	 */
	String parse(Map<String, String> params);

	/**
	 * 调用入口
	 *
	 * @param params 参数
	 * @return 地址
	 */
	String handle(Map<String, String> params);

	/**
	 * 保存回调记录
	 *
	 * @param result 处理结果
	 * @param params 回调报文
	 */
	void saveNotifyRecord(Map<String, String> params, String result);

}
