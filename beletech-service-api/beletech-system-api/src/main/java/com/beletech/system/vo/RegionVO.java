package com.beletech.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beletech.core.tool.node.INode;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.entity.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政区划表视图实体类
 *
 * @author XueBing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RegionVO对象", description = "行政区划表")
public class RegionVO extends Region implements INode<RegionVO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 父节点名称
	 */
	private String parentName;

	/**
	 * 是否有子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Boolean hasChildren;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<RegionVO> children;

	@Override
	public Long getId() {
		return Func.toLong(this.getCode());
	}

	@Override
	public Long getParentId() {
		return Func.toLong(this.getParentCode());
	}

	@Override
	public List<RegionVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
}
