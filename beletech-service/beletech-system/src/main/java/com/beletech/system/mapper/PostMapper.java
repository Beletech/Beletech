package com.beletech.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beletech.system.entity.Post;
import com.beletech.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 Mapper 接口
 *
 * @author XueBing
 */
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页
	 * @param post 参数
	 * @return 列表
	 */
	List<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * 获取岗位名
	 *
	 * @param ids id
	 * @return 岗位集合
	 */
	List<String> getPostNames(Long[] ids);

}
