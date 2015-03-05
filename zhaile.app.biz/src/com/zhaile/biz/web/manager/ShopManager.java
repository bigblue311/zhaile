package com.zhaile.biz.web.manager;

import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.admin.model.ShopDetailVO;
import com.zhaile.biz.web.enumerate.ShopVOSortEnum;
import com.zhaile.biz.web.model.ProductVO;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

import java.util.List;
import java.util.Map;

public interface ShopManager {
	/**
	 * 是否是收藏店铺
	 * @param shopId
	 * @param customerId
	 * @return
	 */
	boolean isFav(Long shopId, Long customerId);
	
	/**
	 * 更新/新增店铺
	 * @param shopDetailVO
	 * @return
	 */
	Map<String,Long> updateShopDetail(ShopDetailVO shopDetailVO);
	
	/**
	 * 获取店铺
	 * @param shop
	 * @return
	 */
	ShopVO getShopVO(ShopDO shop);
	
	/**
	 * 根据ID获取店铺
	 * @param shopId
	 * @return
	 */
	ShopVO getShopVOById(Long shopId);
	
	/**
	 * 获取所有店铺
	 * @return
	 */
	List<ShopVO> getAll();
	
	/**
	 * 根据坐标获取店铺
	 * @param lng
	 * @param lat
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Paging<ShopVO> getAllByDistance(Double lng,Double lat, int page, int pageSize);
	
	/**
	 * 根据分类获取店铺
	 * @param shopCateId
	 * @param lng
	 * @param lat
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Paging<ShopVO> getShopVOByCateId(Long shopCateId,Double lng, Double lat,int page, int pageSize);
	
	/**
	 * 排序
	 * @param list
	 * @param sortEnum
	 * @param lng
	 * @param lat
	 */
	void sort(List<ShopVO> list, ShopVOSortEnum sortEnum, Double lng, Double lat);
	
	/**
	 * 根据商品ID获取商品
	 * @param prodId
	 * @return
	 */
	ProductVO getProdVOById(Long prodId);
	
	/**
	 * 根据商品获取店铺
	 * @param prodId
	 * @return
	 */
	ShopDO getShopByProdId(Long prodId);
	
	/**
	 * 获取店铺
	 * @param shopId
	 * @return
	 */
	ShopDO getShopById(Long shopId);
	
	/**
	 * 根据客户ID获取店铺
	 * @param customerId
	 * @return
	 */
	List<ShopDO> getShopsByCustomerID(Long customerId);
	
	/**
	 * 根据ID获取商品
	 * @param id
	 * @return
	 */
	List<ProductDO> getProdByIds(Long[] id);
	
	/**
	 * 清除店铺绑定信息
	 * @param shopId
	 * @param pos
	 * @return
	 */
	boolean clearShopBinding(Long shopId, int pos);
	
	/**
	 * 获取所有店铺列表
	 * @param queryCondition
	 * @return
	 */
	Paging<ShopDO> getFull(ShopQueryCondition queryCondition);
}
