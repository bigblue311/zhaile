package com.zhaile.web.webpage.screen;

import com.alibaba.citrus.turbine.Context;
import com.zhaile.web.common.screen.DefaultLayout;

public class Info extends DefaultLayout {
	
	public void execute(Context context) {
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-关于我们");
    }
}
