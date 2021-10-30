package com.beletech.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.Post;

/**
 * 岗位表数据传输对象实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends Post {
	private static final long serialVersionUID = 1L;

}
