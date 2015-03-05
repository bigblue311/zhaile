package com.victor.framework.dal.basic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Paging<Entity> implements Serializable{
	private static final long serialVersionUID = 7626354075834659275L;
	
	private int pageSize = 20; 			 //默认分页大小
	private int start = 0; 				 //默认从零个开始
	private int end = 20;				 //默认20条
	private int count;					 //共展示多少条记录
	private int currentPage = 1; 		 //当前页
	private final int firstPage = 1; 	 //第一页
	private int lastPage;				 //最后一页
	private int prePage;	 			 //上一页
	private int nextPage; 				 //下一页
	private List<Integer> prePages = Lists.newArrayList();	 //前几页
	private List<Integer> nextPages = Lists.newArrayList();	 //后几页
	private int barSize;				 //多少页后省去
	private int totalSize;				 //总个多少条数据
	private int totalPage;				 //总共多少
	private List<Entity> data;		 	 //默认数据
	private Map<String,Object> query = Maps.newHashMap(); //查询条件
	
	@Override
	public String toString(){
		Field[] fields = this.getClass().getDeclaredFields();
		Method[] methods = this.getClass().getDeclaredMethods();
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()+"{");
		for(Field field : fields){
			sb.append("|"+field.getName()+"=");
			for(Method method : methods){
				if(method.getName().equalsIgnoreCase("get"+field.getName())){
					try {
						sb.append(method.invoke(this, new Object[0]).toString());
					}catch (Exception e) {
						sb.append("unkown");
					}
				}	
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	public Paging(int start, int pageSize, int totalSize, int barSize, Map<String,Object> query){
		this.pageSize = pageSize;
		this.start = start;
		this.end = start + pageSize > totalSize? totalSize: start + pageSize;
		this.count = end - start;
		this.currentPage = start / pageSize + 1;
		this.lastPage = totalSize/pageSize + (totalSize%pageSize==0?0:1);
		this.prePage = currentPage - 1  < firstPage?firstPage:currentPage - 1;
		this.nextPage = currentPage + 1 > lastPage?lastPage:currentPage + 1;
		for(int i = 1; i <= barSize; i++) {
			if(currentPage - i  < firstPage){
				break;
			}
			prePages.add(currentPage - i);
		}
		for(int i = 1; i <= barSize; i++) {
			if(currentPage + i > lastPage){
				break;
			}
			nextPages.add(currentPage + i);
		}
		prePages = Lists.reverse(prePages);
		this.barSize = barSize;
		this.totalSize = totalSize;
		this.totalPage = lastPage;
		if(query == null) {
			this.query = new HashMap<String,Object>();
		} else {
			this.query = query;
		}
	}
	
	public static <Entity> Paging<Entity> emptyPage(){
		return new Paging<Entity>(0,20,0,5,null);
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getCount() {
		return count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public List<Integer> getPrePages() {
		return prePages;
	}

	public List<Integer> getNextPages() {
		return nextPages;
	}

	public int getBarSize() {
		return barSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<Entity> getData() {
		return data;
	}

	public void setData(List<Entity> data) {
		this.data = data;
	}

	public Map<String, Object> getQuery() {
		query.put("start", start);
		query.put("pageSize", pageSize);
		return query;
	}
}
