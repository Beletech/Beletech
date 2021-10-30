package com.beletech.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beletech.system.entity.Dept;
import com.beletech.system.vo.DeptVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author XueBing
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 懒加载部门列表
	 *
	 * @param tenantId 租户id
	 * @param parentId 父id
	 * @param param    参数
	 * @return 列表
	 */
	List<DeptVO> lazyList(String tenantId, Long parentId, Map<String, Object> param);

	/**
	 * 树形结构
	 *
	 * @param tenantId 租户id
	 * @return 集合
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 懒加载树形结构
	 *
	 * @param tenantId 租户id
	 * @param parentId 父id
	 * @return 集合
	 */
	List<DeptVO> lazyTree(String tenantId, Long parentId);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId  租户id
	 * @param deptNames 名称
	 * @return id
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId  租户id
	 * @param deptNames 名称
	 * @return id
	 */
	String getDeptIdsByFuzzy(String tenantId, String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds id集合
	 * @return 名称
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * 获取子部门
	 *
	 * @param deptId id
	 * @return 部门
	 */
	List<Dept> getDeptChild(Long deptId);

	/**
	 * 删除部门
	 *
	 * @param ids 部门id
	 * @return 状态
	 */
	boolean removeDept(String ids);

	/**
	 * 提交
	 *
	 * @param dept 参数
	 * @return 状态
	 */
	boolean submit(Dept dept);

	/**
	 * 部门信息查询
	 *
	 * @param deptName 部门名称
	 * @param parentId 父id
	 * @return 列表
	 */
	List<DeptVO> search(String deptName, Long parentId);

}
