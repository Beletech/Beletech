package com.beletech.desk.wrapper;

import com.beletech.core.mp.support.BaseEntityWrapper;
import com.beletech.core.tool.utils.BeanUtil;
import com.beletech.desk.entity.Notice;
import com.beletech.desk.vo.NoticeVO;
import com.beletech.system.cache.DictCache;
import com.beletech.system.enums.DictEnum;

import java.util.Objects;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author XueBing
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = Objects.requireNonNull(BeanUtil.copy(notice, NoticeVO.class));
		String dictValue = DictCache.getValue(DictEnum.NOTICE, noticeVO.getCategory());
		noticeVO.setCategoryName(dictValue);
		return noticeVO;
	}

}
