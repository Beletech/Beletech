package com.beletech.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * GrantTreeVO
 *
 * @author XueBing
 */
@Data
public class GrantTreeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<MenuVO> menu;

	private List<MenuVO> dataScope;

	private List<MenuVO> apiScope;

}
