package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.LogSearchDAO;
import com.zhaile.dal.model.LogSearchDO;
import com.zhaile.dal.model.vo.LogSearchStatistic;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class LogSearchDAOImpl extends EntityDAO<LogSearchDO> implements LogSearchDAO {

	public LogSearchDAOImpl() {
		super(LogSearchDO.class.getSimpleName());
	}

	@Override
	public Result<List<LogSearchStatistic>> getPage(LogQueryCondition condition) {
		try {
			Object obj = getSqlMapClient().queryForList(super.getNamespace()+".getPage", condition.getQueryMap());
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<LogSearchStatistic> entity = (List<LogSearchStatistic>)obj;
				return Result.newInstance(entity, "获取分页数据成功", true);
			} else {
				return Result.newInstance(null, "获取分页数据失败", false);
			}
		} catch (SQLException e) {
			return Result.newInstance(null, "获取分页数据失败", false);
		}
	}

	@Override
	public Result<List<LogSearchDO>> getByLog(Long logId) {
		return super.queryForList("getByLog", "logId", logId);
	}

	@Override
	public Result<Integer> getCount(LogQueryCondition condition) {
		return super.getCount(condition);
	}

	@Override
	public Result<List<String>> getTop(Long limit) {
		Result<List<Object>> result = super.queryForList(LogSearchDO.class.getSimpleName(), "getTop", "limit", limit);
		if(result.isSuccess()){
			List<String> list = Lists.newArrayList();
			for(Object obj : result.getDataObject()){
				list.add(obj.toString());
			}
			return Result.newInstance(list, "获取数据成功", true);
		}
		return Result.newInstance(null, "获取数据失败", false);
	}

	@Override
	public Result<List<LogSearchStatistic>> getStatistic(Long limit) {
		Result<List<Object>> result = super.queryForList(LogSearchDO.class.getSimpleName(), "getStatistic", "limit", limit);
		if(result.isSuccess()){
			List<LogSearchStatistic> list = Lists.newArrayList();
			for(Object obj : result.getDataObject()){
				list.add((LogSearchStatistic)obj);
			}
			return Result.newInstance(list, "获取数据成功", true);
		}
		return Result.newInstance(null, "获取数据失败", false);
	}

}
