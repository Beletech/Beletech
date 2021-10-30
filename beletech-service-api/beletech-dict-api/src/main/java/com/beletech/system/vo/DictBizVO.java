package com.beletech.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tool.node.INode;
import com.beletech.system.entity.DictBiz;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DictBizVO对象", description = "DictBizVO对象")
public class DictBizVO extends DictBiz implements INode<DictBizVO> {
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DictBizVO> children;

	@Override
	public List<DictBizVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	private String parentName;
}
