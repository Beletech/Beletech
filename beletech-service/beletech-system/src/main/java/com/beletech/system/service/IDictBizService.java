package com.beletech.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.core.mp.support.Query;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.vo.DictBizVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IDictBizService extends IService<DictBiz> {

	/**
	 * 树形结构
	 *
	 * @return 列表
	 */
	List<DictBizVO> tree();

	/**
	 * 树形结构
	 *
	 * @return 列表
	 */
	List<DictBizVO> parentTree();

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return 列表
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return 列表
	 */
	List<DictBiz> getList(String code);

	/**
	 * 新增或修改
	 *
	 * @param dict 参数
	 * @return 列表
	 */
	boolean submit(DictBiz dict);

	/**
	 * 删除字典
	 *
	 * @param ids id
	 * @return 状态
	 */
	boolean removeDict(String ids);

	/**
	 * 顶级列表
	 *
	 * @param dict  参数
	 * @param query 查询参数
	 * @return 分页对象
	 */
	IPage<DictBizVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * 子列表
	 *
	 * @param dict     参数
	 * @param parentId 父id
	 * @return 列表
	 */
	List<DictBizVO> childList(Map<String, Object> dict, Long parentId);

}
