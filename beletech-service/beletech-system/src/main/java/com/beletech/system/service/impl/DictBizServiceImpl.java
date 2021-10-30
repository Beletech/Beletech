package com.beletech.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beletech.common.constant.CommonConstant;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.log.exception.ServiceException;
import com.beletech.core.mp.support.Condition;
import com.beletech.core.mp.support.Query;
import com.beletech.core.tool.constant.BeletechConstant;
import com.beletech.core.tool.node.ForestNodeMerger;
import com.beletech.core.tool.utils.Func;
import com.beletech.core.tool.utils.StringPool;
import com.beletech.system.cache.DictBizCache;
import com.beletech.system.entity.DictBiz;
import com.beletech.system.mapper.DictBizMapper;
import com.beletech.system.service.IDictBizService;
import com.beletech.system.vo.DictBizVO;
import com.beletech.system.wrapper.DictBizWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.beletech.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 服务实现类
 *
 * @author XueBing
 */
@Service
public class DictBizServiceImpl extends ServiceImpl<DictBizMapper, DictBiz> implements IDictBizService {

	@Override
	public List<DictBizVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<DictBizVO> parentTree() {
		return ForestNodeMerger.merge(baseMapper.parentTree());
	}

	@Override
	public String getValue(String code, String dictKey) {
		return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
	}

	@Override
	public List<DictBiz> getList(String code) {
		return baseMapper.getList(code);
	}

	@Override
	public boolean submit(DictBiz dict) {
		LambdaQueryWrapper<DictBiz> lqw = Wrappers.<DictBiz>query().lambda().eq(DictBiz::getCode, dict.getCode()).eq(DictBiz::getDictKey, dict.getDictKey());
		Integer cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(DictBiz::getId, dict.getId()));
		if (cnt > 0) {
			throw new ServiceException("当前字典键值已存在!");
		}
		// 修改顶级字典后同步更新下属字典的编号
		if (Func.isNotEmpty(dict.getId()) && dict.getParentId().longValue() == BeletechConstant.TOP_PARENT_ID) {
			DictBiz parent = DictBizCache.getById(dict.getId());
			this.update(Wrappers.<DictBiz>update().lambda().set(DictBiz::getCode, dict.getCode()).eq(DictBiz::getCode, parent.getCode()).ne(DictBiz::getParentId, BeletechConstant.TOP_PARENT_ID));
		}
		if (Func.isEmpty(dict.getParentId())) {
			dict.setParentId(BeletechConstant.TOP_PARENT_ID);
		}
		dict.setIsDeleted(BeletechConstant.DB_NOT_DELETED);
		CacheUtil.clear(DICT_CACHE);
		return saveOrUpdate(dict);
	}

	@Override
	public boolean removeDict(String ids) {
		Integer cnt = baseMapper.selectCount(Wrappers.<DictBiz>query().lambda().in(DictBiz::getParentId, Func.toLongList(ids)));
		if (cnt > 0) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	@Override
	public IPage<DictBizVO> parentList(Map<String, Object> dict, Query query) {
		IPage<DictBiz> page = this.page(Condition.getPage(query), Condition.getQueryWrapper(dict, DictBiz.class).lambda().eq(DictBiz::getParentId, CommonConstant.TOP_PARENT_ID).orderByAsc(DictBiz::getSort));
		return DictBizWrapper.build().pageVO(page);
	}

	@Override
	public List<DictBizVO> childList(Map<String, Object> dict, Long parentId) {
		if (parentId < 0) {
			return new ArrayList<>();
		}
		dict.remove("parentId");
		DictBiz parentDict = DictBizCache.getById(parentId);
		List<DictBiz> list = this.list(Condition.getQueryWrapper(dict, DictBiz.class).lambda().ne(DictBiz::getId, parentId).eq(DictBiz::getCode, parentDict.getCode()).orderByAsc(DictBiz::getSort));
		return DictBizWrapper.build().listNodeVO(list);
	}
}
