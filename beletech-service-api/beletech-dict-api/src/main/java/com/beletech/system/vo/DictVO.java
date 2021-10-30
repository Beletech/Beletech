package com.beletech.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tool.node.INode;
import com.beletech.system.entity.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DictVO对象", description = "DictVO对象")
public class DictVO extends Dict implements INode<DictVO> {
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DictVO> children;

	@Override
	public List<DictVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	private String parentName;
}
