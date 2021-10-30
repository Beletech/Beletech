package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.constant.BeletechConstant;
import com.beletech.core.tool.node.ForestNodeMerger;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.cache.SysCache;
import com.beletech.system.entity.Role;
import com.beletech.system.vo.RoleVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = Objects.requireNonNull(BeanUtil.copy(role, RoleVO.class));
		if (Func.equals(role.getParentId(), BeletechConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(BeletechConstant.TOP_PARENT_NAME);
		} else {
			Role parent = SysCache.getRole(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		return roleVO;
	}


	public List<RoleVO> listNodeVO(List<Role> list) {
		List<RoleVO> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
