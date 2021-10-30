package com.beletech.system.vo;

import lombok.Data;

import java.util.List;

/**
 * CheckedTreeVO
 *
 * @author XueBing
 */
@Data
public class CheckedTreeVO {

	private List<String> menu;

	private List<String> dataScope;

	private List<String> apiScope;

}
