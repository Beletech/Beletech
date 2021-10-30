package com.beletech.resource.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.resource.entity.Attach;

/**
 * 附件表视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AttachVO对象", description = "附件表")
public class AttachVO extends Attach {
	private static final long serialVersionUID = 1L;

}
