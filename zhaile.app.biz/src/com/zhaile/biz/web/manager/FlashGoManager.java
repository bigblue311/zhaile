package com.zhaile.biz.web.manager;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.model.FlashGoVO;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public interface FlashGoManager extends Function<FlashGoDO,FlashGoVO> {
	/**
	 * 添加一个活动
	 * @param flashGoDo
	 * @return
	 */
	Long addFlashGoEvent(FlashGoDO flashGoDo);
	
	/**
	 * 更新活动内容
	 * @param Boolean
	 * @return
	 */
	Boolean updateFlashGoEvent(FlashGoDO flashGoDo);
	
	/**
	 * 售出一份
	 * @param id
	 */
	void sold(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	FlashGoDO getById(Long id);
	
	/**
	 * 获取这周的活动计划
	 * @return
	 */
	Map<String,String> getThisWeek();
	
	/**
	 * 获取今天的活动
	 * @return
	 */
	FlashGoDO getToday();
	
	/**
	 * 获取昨天，今天，明天和后天的活动
	 * @return
	 */
	List<FlashGoVO> getRecent();
	
	/**
	 * 获取分页数据
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	Paging<FlashGoDO> getPage(FlashGoQueryCondition queryCondition);
}
