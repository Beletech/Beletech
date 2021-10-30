package com.beletech.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.system.entity.Dict;

/**
 * 数据传输对象实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDTO extends Dict {
	private static final long serialVersionUID = 1L;

}
