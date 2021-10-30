package com.beletech.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.vo.DictBizVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return 中文名称
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return 字典表
	 */
	List<DictBiz> getList(String code);

	/**
	 * 获取树形节点
	 *
	 * @return 树形节点
	 */
	List<DictBizVO> tree();

	/**
	 * 获取树形节点
	 *
	 * @return 树形节点
	 */
	List<DictBizVO> parentTree();

}
