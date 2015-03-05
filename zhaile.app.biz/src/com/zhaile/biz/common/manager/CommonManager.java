package com.zhaile.biz.common.manager;

import com.victor.framework.common.shared.Result;

public class CommonManager {
	protected Object getObject(Result<?> result){
		if(result==null || !result.isSuccess()){
			return null;
		} else {
			return result.getDataObject();
		}
	}
}
