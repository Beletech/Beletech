package com.beletech.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.core.mp.base.BaseService;
import com.beletech.system.entity.Post;
import com.beletech.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 服务类
 *
 * @author XueBing
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页
	 * @param post 参数
	 * @return 分页
	 */
	IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * 获取岗位ID
	 *
	 * @param tenantId  租户id
	 * @param postNames 参数
	 * @return id
	 */
	String getPostIds(String tenantId, String postNames);

	/**
	 * 获取岗位ID
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名称
	 * @return id
	 */
	String getPostIdsByFuzzy(String tenantId, String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 岗位id
	 * @return 名称集合
	 */
	List<String> getPostNames(String postIds);

}
