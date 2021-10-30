package com.beletech.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.core.mp.support.Query;
import com.beletech.system.entity.Dict;
import com.beletech.system.vo.DictVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IDictService extends IService<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页
	 * @param dict 参数
	 * @return 分页对象
	 */
	IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict);

	/**
	 * 树形结构
	 *
	 * @return 列表
	 */
	List<DictVO> tree();

	/**
	 * 树形结构
	 *
	 * @return 列表
	 */
	List<DictVO> parentTree();

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return 集合
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return 集合
	 */
	List<Dict> getList(String code);

	/**
	 * 新增或修改
	 *
	 * @param dict 字典
	 * @return 状态
	 */
	boolean submit(Dict dict);

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
	 * @param query 餐胡
	 * @return 分页
	 */
	IPage<DictVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * 子列表
	 *
	 * @param dict     字典
	 * @param parentId 父id
	 * @return 列表
	 */
	List<DictVO> childList(Map<String, Object> dict, Long parentId);

}
