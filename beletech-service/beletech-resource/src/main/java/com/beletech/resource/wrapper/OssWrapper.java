package com.beletech.resource.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.resource.entity.Oss;
import com.beletech.resource.vo.OssVO;
import com.beletech.system.cache.DictCache;
import com.beletech.system.enums.DictEnum;

import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Beletech
 */
public class OssWrapper extends BaseEntityWrapper<Oss, OssVO> {

	public static OssWrapper build() {
		return new OssWrapper();
	}

	@Override
	public OssVO entityVO(Oss oss) {
		OssVO ossVO = Objects.requireNonNull(BeanUtil.copy(oss, OssVO.class));
		String categoryName = DictCache.getValue(DictEnum.OSS, oss.getCategory());
		String statusName = DictCache.getValue(DictEnum.YES_NO, oss.getStatus());
		ossVO.setCategoryName(categoryName);
		ossVO.setStatusName(statusName);
		return ossVO;
	}

}
