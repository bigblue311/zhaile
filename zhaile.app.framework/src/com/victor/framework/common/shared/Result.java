package com.victor.framework.common.shared;

public class Result<T extends Object> {	
	private boolean success;
	private String message;
	private T dataObject;
	
	protected Result(){
		
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	protected void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	protected void setMessage(String message) {
		this.message = message;
	}
	
	public T getDataObject() {
		if(this!=null && this.success){
			return dataObject;
		}
		return null;
	}
	
	protected void setDataObject(T dataObject) {
		this.dataObject = dataObject;
	}	
	
	public String toString(){
		return "{message:"+message+",dataObject:"+dataObject+"isSuccess:"+success+"}";
	}
	
	public static <T extends Object> Result<T> newInstance(T dataObject,String message,boolean isSuccess){
		final Result<T> result = new Result<T>();
		result.setDataObject(dataObject);
		result.setMessage(message);
		result.setSuccess(isSuccess);
		return result;
	}
}
