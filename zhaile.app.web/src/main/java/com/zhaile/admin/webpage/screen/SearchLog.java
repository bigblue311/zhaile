package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.SearchLogQueryFormBean;
import com.zhaile.dal.dao.LogSearchDAO;
import com.zhaile.dal.model.vo.LogSearchStatistic;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class SearchLog extends AdminLoginFilter {
	
	@Autowired
	private LogSearchDAO logSearchDAO;
	
	public void execute(@Params SearchLogQueryFormBean searchLogQuery,
						Context context,Navigator nav) throws IOException{
		boolean access = super.doFilter("searchLog",context,nav);
		if(!access){
			return;
		}
		LogQueryCondition queryCondition = searchLogQuery.getLogQueryCondition();
		Paging<LogSearchStatistic> logSearchPage = querySearchLog(queryCondition);
		context.put("paging", logSearchPage);
		context.put("searchLogQuery", searchLogQuery);
		context.put("logSearchList", JSONObject.toJSONString(logSearchPage.getData()));
	}
	
	private Paging<LogSearchStatistic> querySearchLog(LogQueryCondition queryCondition) {
		int totalSize = logSearchDAO.getCount(queryCondition).getDataObject();
		@SuppressWarnings("unchecked")
		Paging<LogSearchStatistic> logSearchPage = queryCondition.getPaging(totalSize, 5);
		List<LogSearchStatistic> list = logSearchDAO.getPage(queryCondition).getDataObject();
		logSearchPage.setData(list);
		return logSearchPage;
	}
}
