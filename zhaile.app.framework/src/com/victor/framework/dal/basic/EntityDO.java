package com.victor.framework.dal.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.tools.BigDecimalTools;
import com.victor.framework.common.tools.DateTools;

public class EntityDO {
	private Long	id;				//ID
	private Date 	gmtCreate; 		//创建日期
	private Date 	gmtModify;		//修改日期
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	
	@Override
	public String toString(){
		Field[] fields = this.getClass().getDeclaredFields();
		Method[] methods = this.getClass().getDeclaredMethods();
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()+"{");
		sb.append("id="+this.getId());
		sb.append("|gmtCreate="+DateTools.DateToString(this.getGmtCreate()));
		sb.append("|gmtModify="+DateTools.DateToString(this.getGmtModify()));
		for(Field field : fields){
			sb.append("|"+field.getName()+"=");
			boolean find = false;
			for(Method method : methods){
				if(method.getName().equalsIgnoreCase("get"+field.getName())){
					try {
						find = true;
						if(method.getReturnType().equals(Date.class)){
							sb.append(DateTools.DateToString((Date)method.invoke(this, new Object[0])));
							break;
						}
						if(method.getReturnType().equals(BigDecimal.class)){
							sb.append(BigDecimalTools.toString((BigDecimal)method.invoke(this, new Object[0])));
							break;
						}
						else{
							sb.append(method.invoke(this, new Object[0]).toString());
							break;
						}
					} catch (Exception e) {
						sb.append("unkown");
					}
				}	
			}
			if(!find){
				sb.append("unkown");
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	public Map<String,Object> toMap(){
		Map<String, Object> myMap = Maps.newHashMap();
		Field[] fields = this.getClass().getDeclaredFields();
		Method[] methods = this.getClass().getDeclaredMethods();
		for(Field field : fields){
			for(Method method : methods){
				if(method.getName().equalsIgnoreCase("get"+field.getName())){
					try {
						myMap.put(field.getName(), method.invoke(this, new Object[0]));
					} catch (Exception e) {
						myMap.put(field.getName(), null);
					} 
				}
			}
		
		}
		return myMap;
	}
}
