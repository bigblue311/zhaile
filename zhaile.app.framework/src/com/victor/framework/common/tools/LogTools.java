package com.victor.framework.common.tools;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTools implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7082389130497302292L;
	private Logger slf4jLogger;

	public LogTools(Class<?> caller){
		slf4jLogger = LoggerFactory.getLogger(caller);
	}
	
	private String getStackInfo() {
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		StackTraceElement ste = stack[2];
		return "at "+ste.getClassName()+"."+ste.getMethodName()+"("+ste.getFileName()+":"+ste.getLineNumber()+"):";
	}
	
	public void info(String message){
		slf4jLogger.info(getStackInfo()+message);
	}
	
	public void info(String message,Throwable t){
		slf4jLogger.info(getStackInfo()+message,t);
	}
	
	public void info(String message,Object... bean){
		slf4jLogger.info(getStackInfo()+message);
		for(Object obj : bean){
			slf4jLogger.info(obj.toString());
		}
	}
	
	public void info(String message,Throwable t, Object... bean){
		slf4jLogger.info(getStackInfo()+message,t);
		for(Object obj : bean){
			slf4jLogger.info(obj.toString());
		}
	}
	
	public void warn(String message){
		slf4jLogger.warn(getStackInfo()+message);
	}
	
	public void warn(String message,Throwable t){
		slf4jLogger.warn(getStackInfo()+message,t);
	}
	
	public void warn(String message,Object... bean){
		slf4jLogger.warn(getStackInfo()+message);
		for(Object obj : bean){
			slf4jLogger.warn(obj.toString());
		}
	}
	
	public void warn(String message,Throwable t, Object... bean){
		slf4jLogger.warn(getStackInfo()+message,t);
		for(Object obj : bean){
			slf4jLogger.warn(obj.toString());
		}
	}
	
	public void error(String message){
		slf4jLogger.warn(getStackInfo()+message);
	}
	
	public void error(String message,Throwable t){
		slf4jLogger.warn(getStackInfo()+message,t);
	}
	
	public void error(String message,Object... bean){
		slf4jLogger.warn(getStackInfo()+message);
		for(Object obj : bean){
			slf4jLogger.warn(obj.toString());
		}
	}
	
	public void error(String message,Throwable t, Object... bean){
		slf4jLogger.warn(getStackInfo()+message,t);
		for(Object obj : bean){
			slf4jLogger.warn(obj.toString());
		}
	}
}
