package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beletech.system.entity.Dept;
import com.beletech.system.vo.DeptVO;

import java.util.List;
import java.util.Map;

/**
 * DeptMapper 接口
 *
 * @author XueBing
 */
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 懒加载部门列表
	 *
	 * @param tenantId 租户id
	 * @param parentId 父id
	 * @param param    参数
	 * @return 部门
	 */
	List<DeptVO> lazyList(String tenantId, Long parentId, Map<String, Object> param);

	/**
	 * 获取树形节点
	 *
	 * @param tenantId 租户id
	 * @return 树形
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 懒加载获取树形节点
	 *
	 * @param tenantId 租户id
	 * @param parentId 租户id
	 * @return 树形
	 */
	List<DeptVO> lazyTree(String tenantId, Long parentId);

	/**
	 * 获取部门名
	 *
	 * @param ids ids
	 * @return 部门名称
	 */
	List<String> getDeptNames(Long[] ids);

}
