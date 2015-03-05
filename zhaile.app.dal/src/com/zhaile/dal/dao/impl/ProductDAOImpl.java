package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.query.condition.ProductQueryCondition;

public class ProductDAOImpl extends EntityDAO<ProductDO> implements ProductDAO {

	public ProductDAOImpl() {
		super(ProductDO.class.getSimpleName());
	}

	@Override
	public Result<List<ProductDO>> getPage(ProductQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(ProductQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

	@Override
	public Result<List<ProductDO>> getBestSellerTop10() {
		return super.queryForList("getBestSell10");
	}

	@Override
	public Result<List<ProductDO>> getRandom(int count,Long... categoryId) {
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
	public Result<List<ProductDO>> getAll() {
		return super.queryForList("getAll");
	}

	@Override
	public Result<List<ProductDO>> getFull() {
		return super.queryForList("getFull");
	}
	
	@Override
	public Result<List<ProductDO>> getFullByShopId(Long shopId){
		return super.queryForList("getFullByShopId","shopId", shopId);
	}

	@Override
	public Result<Boolean> updateStatusByShopId(String enable,Long shopId) {
		ProductDO product = new ProductDO();
		product.setEnable(enable);
		product.setShopId(shopId);
		return super.updateBySID("updateStatusByShopId", product);
	}

	@Override
	public Result<List<Long>> getAllDisabled() {
		try {
			Object obj = getSqlMapClient().queryForList(getNamespace()+".getAllDisabled");
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
	public Result<List<Long>> getAnyEnabled() {
		try {
			Object obj = getSqlMapClient().queryForList(getNamespace()+".getAnyEnabled");
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
}
