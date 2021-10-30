package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.system.entity.Dict;
import com.beletech.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author XueBing
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param dict 查询参数
	 * @return 分页
	 */
	List<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict);

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
	List<Dict> getList(String code);

	/**
	 * 获取树形节点
	 *
	 * @return 树形节点
	 */
	List<DictVO> tree();

	/**
	 * 获取树形节点
	 *
	 * @return 树形节点
	 */
	List<DictVO> parentTree();
}
