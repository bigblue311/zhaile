package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.common.tools.DateTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.CommentQueryFormBean;
import com.zhaile.biz.web.manager.CustomerManager;

public class Comment extends AdminLoginFilter {
	
	@Autowired
	private CustomerManager customerManager;
	
	public void execute(@Params CommentQueryFormBean commentQueryFormBean,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("comment",context,nav);
		if(!access){
			return;
		}
		
		Date begin = commentQueryFormBean.getModifyStart();
		Date end = commentQueryFormBean.getModifyEnd();
		Long[] shopIds = null;
		if(!super.isZhaile() && super.getShopIds()!=null){
			shopIds = super.getShopIds();
		}

		context.put("commentList", JSONObject.toJSONString(customerManager.getCommentJson(begin, end, shopIds)));
		context.put("queryText", DateTools.DateToString(begin)+" - "+ DateTools.DateToString(end));
		context.put("commentQueryFormBean", commentQueryFormBean);
	}
}
