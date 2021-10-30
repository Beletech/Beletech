package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.system.cache.DictCache;
import com.beletech.system.entity.Post;
import com.beletech.system.enums.DictEnum;
import com.beletech.system.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = DictCache.getValue(DictEnum.POST_CATEGORY, post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
