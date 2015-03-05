package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class ShopDAOImpl extends EntityDAO<ShopDO> implements ShopDAO {
	
	public ShopDAOImpl() {
		super(ShopDO.class.getSimpleName());
	}

	@Override
	public Result<List<ShopDO>> getPage(ShopQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(ShopQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

	@Override
	public Result<List<ShopDO>> getAll() {
		return super.queryForList("getAll");
	}

	@Override
	public Result<List<ShopDO>> getBestSellerTop10() {
		return super.queryForList("getBestSell10");
	}

	@Override
	public Result<List<ShopDO>> getRandom(int count,Long... categoryId) {
		Map<String,Object> query = Maps.newHashMap();
		if(categoryId.length == 1) {
			query.put("categoryId", categoryId[0]);
		} else {
			List<Long> aList = Lists.newArrayList(categoryId);
			query.put("categoryIds", aList);
		}
		query.put("count", count);
		return super.queryForList("getRandom",query);
	}

	@Override
	public Result<List<ShopDO>> getFull() {
		return super.queryForList("getFull");
	}

	@Override
	public Result<Boolean> autoDisable(Long[] id) {
		try {
			getSqlMapClient().update(super.getNamespace()+".autoDisable",id);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			return Result.newInstance(false, "更新数据失败", false);
		}
	}

	@Override
	public Result<Boolean> autoEnable(Long[] id) {
		try {
			getSqlMapClient().update(super.getNamespace()+".autoEnable",id);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			return Result.newInstance(false, "更新数据失败", false);
		}
	}

	@Override
	public Result<List<Long>> getDisabledList() {
		try {
			Object obj = getSqlMapClient().queryForList(getNamespace()+".getDisabledList");
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<Long> list = (List<Long>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@Override
	public Integer getAllByDistanceCount(){
		Integer totalSize;
		try {
			totalSize = (Integer)getSqlMapClient().queryForObject(getNamespace()+".getAllByDistanceCount");
		} catch (SQLException e) {
			totalSize = null;
		}
		return totalSize;
	}

	@Override
	public Result<List<ShopDO>> getAllByDistance(Double lng, Double lat,int start, int pageSize) {
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put("lng", lng);
		queryMap.put("lat", lat);
		queryMap.put("pageSize", pageSize);
		queryMap.put("start", start);
		try {
			Object obj = getSqlMapClient().queryForList(getNamespace()+".getAllByDistance",queryMap);
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<ShopDO> list = (List<ShopDO>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			return Result.newInstance(null, "获取数据失败", false);
		}
	}

	@Override
	public Result<Boolean> clearShopBinding(ShopDO shopDO) {
		try {
			getSqlMapClient().update(super.getNamespace()+".clearShopBinding",shopDO);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			return Result.newInstance(false, "更新数据失败", false);
		}
	}
}
