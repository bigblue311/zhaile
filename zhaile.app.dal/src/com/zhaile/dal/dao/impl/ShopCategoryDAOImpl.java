package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.ShopCategoryDAO;
import com.zhaile.dal.model.ShopCategoryDO;

public class ShopCategoryDAOImpl extends EntityDAO<ShopCategoryDO> implements ShopCategoryDAO {

	public ShopCategoryDAOImpl() {
		super(ShopCategoryDO.class.getSimpleName());
	}

	@Override
	public List<Long> getShopIdByCateId(Long shopCateId,Double lng,Double lat,int start,int pageSize) {
		try {
			Map<String,Object> queryMap = Maps.newHashMap();
			queryMap.put("cateId", shopCateId);
			queryMap.put("lng", lng);
			queryMap.put("lat", lat);
			queryMap.put("start", start);
			queryMap.put("pageSize", pageSize);
			Object obj = getSqlMapClient().queryForList(getNamespace()+".getByCateId",queryMap);
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<Long> list = (List<Long>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
	}


	@Override
	public Integer getByCateIdCount(Long shopCateId) {
		Integer totalSize;
		try {
			totalSize = (Integer)getSqlMapClient().queryForObject(getNamespace()+".getByCateIdCount",shopCateId);
		} catch (SQLException e) {
			totalSize = null;
		}
		return totalSize;
	}

	@Override
	public void deleteByShopId(Long shopId) {
		super.deleteBySID("deleteByShopId", shopId);
	}

	@Override
	public List<ShopCategoryDO> getByShopId(Long shopId) {
		ShopCategoryDO forSearch = new ShopCategoryDO();
		forSearch.setShopId(shopId);
		return super.queryForList("getByShopId", forSearch).getDataObject();
	}

}
