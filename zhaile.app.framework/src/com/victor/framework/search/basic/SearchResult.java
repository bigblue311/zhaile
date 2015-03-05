package com.victor.framework.search.basic;

import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.EntityDO;
import com.victor.framework.dal.basic.Paging;
import com.victor.framework.search.enumerate.SearchOrderEnum;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SearchResult<Entity extends EntityDO> extends Result<List<Entity>> {

	private Date timestamp;
	private int totalSize;
	private ResultAttribute attr;

	protected SearchResult(){
		timestamp = DateTools.today();
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public int getTotalSize() {
		return totalSize;
	}
	
	public ResultAttribute getAttr() {
		return attr;
	}

	public static <T extends EntityDO> SearchResult<T> newInstance(List<T> dataObject,ResultAttribute attr,int totalSize,
																	String message,boolean isSuccess){
		final SearchResult<T> result = new SearchResult<T>();
		result.totalSize = totalSize;
		result.attr = attr;
		result.setDataObject(dataObject);
		result.setMessage(message);
		result.setSuccess(isSuccess);
		return result;
	}
	
	public Paging<Entity> getPage(SearchQuery query){
		int start = (Integer)query.getQueryMap().get("start");
		int pageSize = (Integer)query.getQueryMap().get("pageSize");
		List<Entity> listFrom = super.getDataObject();
		List<Entity> listTo = Lists.newArrayList();
		if(listFrom.size()>start){
			for(int i=start;i<(pageSize+start)&&i<listFrom.size();i++){
				listTo.add(listFrom.get(i));
			}
		}
		Paging<Entity> page = new Paging<Entity>(start,pageSize,totalSize,5,query.getQueryMap());
		page.setData(listTo);
		return page;
	}
	
	public void sort(Field field,SearchOrderEnum searchOrder){
		List<Entity> list = super.getDataObject();
		Collections.sort(list, new EntityDOComparable(field,searchOrder));
	}
	
	class EntityDOComparable implements Comparator<Entity>{
		private Field field;
		private SearchOrderEnum searchOrder;
		
		EntityDOComparable(Field field,SearchOrderEnum searchOrder){
			this.field = field;
			this.searchOrder = searchOrder;
		}
		
		@Override
		public int compare(EntityDO o1, EntityDO o2) {
			if(SearchOrderEnum.逆序.equals(searchOrder)){
				return -CollectionTools.compare(o1, o2, field);
			}
			return CollectionTools.compare(o1, o2, field);
		}
	} 
}
