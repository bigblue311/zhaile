package com.zhaile.biz.web.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.model.FlashGoVO;
import com.zhaile.dal.dao.FlashGoDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public class FlashGoManagerImpl implements FlashGoManager{

	@Autowired
	private FlashGoDAO flashGoDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Override
	public Long addFlashGoEvent(FlashGoDO flashGoDO) {
		return flashGoDAO.insert(flashGoDO).getDataObject();
	}

	@Override
	public Boolean updateFlashGoEvent(FlashGoDO flashGoDo) {
		return flashGoDAO.update(flashGoDo).getDataObject();
	}

	@Override
	public void sold(Long id) {
		flashGoDAO.sold(id);
	}

	@Override
	public FlashGoDO getById(Long id) {
		return flashGoDAO.getById(id).getDataObject();
	}

	@Override
	public Map<String, String> getThisWeek() {
		List<Date> thisWeek = DateTools.getThisWeek();
		Map<String, String> map = Maps.newLinkedHashMap();
		int todayWeek = DateTools.weekDay();
		for(int i = 1; i <= 7; i++){
			Date thisDay = thisWeek.get(i-1);
			FlashGoDO flashGoDO = getByDate(thisDay);
			String key = DateTools.getWeekDayName(i);
			String value = "before";
			if(flashGoDO == null){
				key += "(没有活动)";
			} else {
				DateFormat timeMinute = new SimpleDateFormat("HH:mm");
				key += "("+timeMinute.format(flashGoDO.getGmtOpen())+"-"+timeMinute.format(flashGoDO.getGmtClose())+")";
			}
			if(i<todayWeek){
				value = "before";
			}
			if(i == todayWeek){
				value = "current";
			}
			if(i > todayWeek){
				value = "after";
			}
			map.put(key, value);
		}
		return map;
	}
	
	@Override
	public FlashGoDO getToday() {
		return getByDate(DateTools.today());
	}
	
	private FlashGoDO getByDate(Date date){
		Date from = DateTools.getDayBegin(date);
		Date to = DateTools.getDayEnd(date);
		List<FlashGoDO> list = flashGoDAO.getByOpenDate(from, to);
		if(CollectionTools.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<FlashGoVO> getRecent() {
		FlashGoDO yesterday = getByDate(DateTools.getDate(-1));
		FlashGoDO today = getByDate(DateTools.today());
		FlashGoDO tommorrow = getByDate(DateTools.getDate(1));
		FlashGoDO dayAfter = getByDate(DateTools.getDate(2));
		List<FlashGoDO> list = Lists.newArrayList(yesterday,today,tommorrow,dayAfter);
		return Lists.transform(list, this);
	}
	
	@Override
	public Paging<FlashGoDO> getPage(FlashGoQueryCondition queryCondition) {
		return flashGoDAO.getByOpenDate(queryCondition);
	}

	@Override
	public FlashGoVO apply(FlashGoDO flashGoDO) {
		if(flashGoDO == null) {
			return null;
		}
		FlashGoVO flashGoVO = new FlashGoVO();
		flashGoVO.setFlashGo(flashGoDO);
		ProductDO product = productDAO.getById(flashGoDO.getProdId()).getDataObject();
		if(product != null){
			ShopDO shop = shopDAO.getById(product.getShopId()).getDataObject();
			flashGoVO.setShop(shop);
		}
		flashGoVO.setProd(product);
		return flashGoVO;
	}
}
