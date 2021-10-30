package com.beletech.system.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.constant.BeletechConstant;
import com.beletech.core.tool.node.ForestNodeMerger;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.system.cache.DictBizCache;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.vo.DictBizVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class DictBizWrapper extends BaseEntityWrapper<DictBiz, DictBizVO> {

	public static DictBizWrapper build() {
		return new DictBizWrapper();
	}

	@Override
	public DictBizVO entityVO(DictBiz dict) {
		DictBizVO dictVO = Objects.requireNonNull(BeanUtil.copy(dict, DictBizVO.class));
		if (Func.equals(dict.getParentId(), BeletechConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(BeletechConstant.TOP_PARENT_NAME);
		} else {
			DictBiz parent = DictBizCache.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<DictBizVO> listNodeVO(List<DictBiz> list) {
		List<DictBizVO> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictBizVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
