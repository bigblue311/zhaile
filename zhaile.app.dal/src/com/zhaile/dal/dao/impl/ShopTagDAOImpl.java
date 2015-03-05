package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.ShopTagDAO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.ShopTagDO;

public class ShopTagDAOImpl extends EntityDAO<ShopTagDO> implements ShopTagDAO {

	@Autowired
	private DbLogger dblogger;
	
	public ShopTagDAOImpl() {
		super(ShopTagDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(ShopTagDO shopTagDO){
		Result<Long> result = super.insert(shopTagDO);
		if(result.isSuccess() && ForeignKeyEnum.用户.getCode().equals(shopTagDO.getCreatedByType())){
			dblogger.write(LogOpEnum.贴标签, shopTagDO.getCreatedById());
		}
		return result;
	}

	@Override
	public Result<Boolean> updateContent(Long id, String content) {
		ShopTagDO tag = new ShopTagDO();
		tag.setId(id);
		tag.setContent(content);
		return super.update(tag);
	}

	@Override
	public Result<Boolean> SearchCountIncrease(Long id) {
		ShopTagDO tag = new ShopTagDO();
		tag.setId(id);
		tag.setSearchCount(1l); //count + 1
		return super.update(tag);
	}

	@Override
	public Result<List<ShopTagDO>> getOnlyByProdId(Long prodId) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignId(prodId);
		query.setForeignKeyType(ForeignKeyEnum.商品.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}
	
	@Override
	public Result<List<ShopTagDO>> getOnlyProdTag(String key) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignKeyType(ForeignKeyEnum.商品.getCode());
		query.setContent(key);
		return super.queryForList("getByForeignId", query.toMap());
	}

	@Override
	public Result<List<ShopTagDO>> getOnlyByShopId(Long shopId) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignId(shopId);
		query.setForeignKeyType(ForeignKeyEnum.商家.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}
	
	@Override
	public Result<List<ShopTagDO>> getOnlyShopTag(String key) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignKeyType(ForeignKeyEnum.商家.getCode());
		query.setContent(key);
		return super.queryForList("getByForeignId", query.toMap());
	}

	@Override
	public Result<List<ShopTagDO>> getAllByShopId(Long shopId) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignId(shopId);
		return super.queryForList("getAllByShopId", query.toMap());
	}

	@Override
	public Result<List<ShopTagDO>> getAllByCategoryId(Long categoryId) {
		ShopTagDO query = new ShopTagDO();
		query.setForeignId(categoryId);
		return super.queryForList("getAllByCateId", query.toMap());
	}

	@Override
	public Result<List<ShopTagDO>> getOnlyProdTag() {
		ShopTagDO query = new ShopTagDO();
		query.setForeignKeyType(ForeignKeyEnum.商品.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}

	@Override
	public Result<List<ShopTagDO>> getOnlyShopTag() {
		ShopTagDO query = new ShopTagDO();
		query.setForeignKeyType(ForeignKeyEnum.商家.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}
}
