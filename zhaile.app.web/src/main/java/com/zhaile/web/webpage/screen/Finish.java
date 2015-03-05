package com.zhaile.web.webpage.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.web.common.screen.DefaultLayout;

public class Finish extends DefaultLayout {
	
	public void execute(@Param("success")String success ,@Param("msg") String msg,Context context) {
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-"+msg);
    	context.put("msg", msg);
    	context.put("success", success);
    }
}
