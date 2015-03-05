package com.zhaile.biz.web.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.PinYinTools;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.biz.web.admin.model.ShopDetailVO;
import com.zhaile.biz.web.enumerate.ShopVOSortEnum;
import com.zhaile.biz.web.model.CommentVO;
import com.zhaile.biz.web.model.ProductVO;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.dal.cache.CategoryCache;
import com.zhaile.dal.dao.CustomerCommentDAO;
import com.zhaile.dal.dao.CustomerDAO;
import com.zhaile.dal.dao.CustomerFavDAO;
import com.zhaile.dal.dao.OrderDAO;
import com.zhaile.dal.dao.PeopleContactDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopCategoryDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.dao.ShopTagDAO;
import com.zhaile.dal.model.CategoryDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.model.CustomerFavDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopCategoryDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShopTagDO;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;
import com.zhaile.dal.query.condition.CustomerFavQueryCondition;
import com.zhaile.dal.query.condition.OrderQueryCondition;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class ShopManagerImpl extends CommonManager implements ShopManager {
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private ShopTagDAO shopTagDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private PeopleContactDAO peopleContactDAO;
	
	@Autowired
	private CustomerFavDAO customerFavDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CustomerCommentDAO customerCommentDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ShopCategoryDAO shopCategoryDAO;
	
	@Autowired
	private CategoryCache categoryCache;
	
	@Override
	public ShopVO getShopVOById(Long shopId) {
		ShopDO shop = (ShopDO)getObject(shopDAO.getById(shopId));
		return getShopVO(shop);
	}
	
	@Override
	public ShopVO getShopVO(ShopDO shop){
		ShopVO shopVO = new ShopVO();
		shopVO.setShopDO(shop);
		if(shop!=null){
			if(shop.getCustomerId()!=null) {
				CustomerDO customer = (CustomerDO)getObject(customerDAO.getById(shop.getCustomerId()));
				shopVO.setCustomerDO(customer);
			}
			@SuppressWarnings("unchecked")
			List<PeopleContactDO> contactList = (List<PeopleContactDO>)getObject(peopleContactDAO.getByShopId(shop.getId()));
			if(CollectionTools.isNotEmpty(contactList)){
				shopVO.setContactDO(contactList.get(0));
			}	
			@SuppressWarnings("unchecked")
			List<ShopTagDO> tagList = (List<ShopTagDO>)getObject(shopTagDAO.getAllByShopId(shop.getId()));
			shopVO.setTagList(tagList);
			
			List<CategoryDO> categoryList = categoryCache.getByShopId(shop.getId());
			//categoryCache.reload();
			shopVO.setCategoryList(categoryList);
			
			List<ShopCategoryDO> shopCateList = shopCategoryDAO.getByShopId(shop.getId());
			shopVO.setShopCateList(shopCateList);
			
			shopVO.setCommentList(getComment(null,shop.getId()));
			shopVO.setTotalComment(getTotalComment(null,shop.getId()));
			shopVO.setTotalSold(getTotalSold(shop.getId()));
		}
		return shopVO;
	}
	
	@Override
	public void sort(List<ShopVO> list,ShopVOSortEnum sortEnum,Double lng, Double lat){
		try {
			if(sortEnum.getCode().equals(ShopVOSortEnum.成交量最高.getCode())){
				CollectionTools.sort(list, ShopVO.class.getDeclaredField("totalSold"));
			}
			if(sortEnum.getCode().equals(ShopVOSortEnum.最受关注.getCode())){
				CollectionTools.sort(list, ShopVO.class.getDeclaredField("totalComment"));
			}
			if(sortEnum.getCode().equals(ShopVOSortEnum.好评最多.getCode())){
				CollectionTools.sort(list, ShopVO.class.getDeclaredField("goodComment"));
			}
			if(sortEnum.getCode().equals(ShopVOSortEnum.字母顺序.getCode())){
				Collections.sort(list, new AlphabetComparable());
			}
			if(sortEnum.getCode().equals(ShopVOSortEnum.距离.getCode())){
				Collections.sort(list, new DistanceComparable(lng,lat));
			}
		} catch (Exception e) {
			Collections.sort(list, new DistanceComparable(lng,lat));
		}
	}
	
	static class AlphabetComparable implements Comparator<ShopVO>{
		@Override
	    public int compare(ShopVO o1, ShopVO o2) {
			if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
	        try {
				return (o1.getShopDO().getAlphabet().compareTo(o2.getShopDO()
						.getAlphabet()));
			} catch (Exception e) {
				return -1;
			}
	    }
	}
	
	static class DistanceComparable implements Comparator<ShopVO>{
		
		double lng;
		double lat;
		
		public DistanceComparable(Double lng, Double lat){
			this.lng = lng;
			this.lat = lat;
		}
		
		@Override
		public int compare(ShopVO o1, ShopVO o2) {
			try {
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 != null && o2 == null) {
					return 1;
				}
				if (o1 == null && o2 != null) {
					return -1;
				}
				Double distanceShop1 = getDistance(lng, lat, o1.getShopDO()
						.getLng(), o1.getShopDO().getLat());
				Double distanceShop2 = getDistance(lng, lat, o2.getShopDO()
						.getLng(), o2.getShopDO().getLat());
				return distanceShop1.compareTo(distanceShop2);
			} catch (Exception e) {
				return -1;
			}
		}
		
		private Double getDistance(Double myLng, Double myLat, Double shopLng, Double shopLat){
			if(myLng == null || myLng <=0) return 99999d;
			if(myLat == null || myLat <=0) return 99999d;
			if(shopLng == null || shopLng <=0) return 99999d;
			if(shopLat == null || shopLat <=0) return 99999d;
			
			Double x = Math.abs(myLng - shopLng);
			Double y = Math.abs(myLat - shopLat);
			Double z = Math.sqrt(Math.pow(x, 2d)+Math.pow(y, 2d));
			return z;
		}
	}
	
	@Override
	public ProductVO getProdVOById(Long prodId) {
		ProductVO prodVO = new ProductVO();
		ProductDO product = (ProductDO)getObject(productDAO.getById(prodId));
		prodVO.setProductDO(product);
		if(product!=null && product.getShopId()!=null){
			prodVO.setShopVO(getShopVOById(product.getShopId()));
			CustomerCommentQueryCondition query = new CustomerCommentQueryCondition();
			query.prodId(product.getId());
			prodVO.setCommentList(getComment(product.getId(),product.getShopId()));
		}
		if(product!=null && product.getCategoryId()!=null) {
			CategoryDO category = categoryCache.getCate(product.getCategoryId().toString());
			prodVO.setCategoryDO(category);
		}
		return prodVO;
	}
	
	private List<CommentVO> getComment(Long prodId,Long shopId) {
		List<CommentVO> cmtList = Lists.newArrayList();
		CustomerCommentQueryCondition query = new CustomerCommentQueryCondition();
		query.prodId(prodId).shopId(shopId);
		@SuppressWarnings("unchecked")
		List<CustomerCommentDO> commentList = (List<CustomerCommentDO>)getObject(customerCommentDAO.getPage(query));
		for(CustomerCommentDO comment : commentList){
			CommentVO commentVO = new CommentVO();
			commentVO.setComment(comment);
			CustomerDO customer = (CustomerDO)getObject(customerDAO.getById(comment.getCustomerId()));
			if(customer!=null){
				commentVO.setCustomer(customer);
			}
			cmtList.add(commentVO);
		}
		return cmtList;
	}

	private int getTotalComment(Long prodId, Long shopId) {
		CustomerCommentQueryCondition queryCondition = new CustomerCommentQueryCondition();
		queryCondition.prodId(prodId).shopId(shopId);
		return customerCommentDAO.getCount(queryCondition).getDataObject();
	}

	/*
	private int getGoodComment(Long prodId, Long shopId) {
		CustomerCommentQueryCondition queryCondition = new CustomerCommentQueryCondition();
		queryCondition.prodId(prodId).shopId(shopId).good();
		return commentDAO.getCount(queryCondition).getDataObject();
	}

	private int getBadComment(Long prodId, Long shopId) {
		CustomerCommentQueryCondition queryCondition = new CustomerCommentQueryCondition();
		queryCondition.prodId(prodId).shopId(shopId).bad();
		return commentDAO.getCount(queryCondition).getDataObject();
	}*/
	
	private int getTotalSold(Long shopId) {
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.shopId(shopId);
		return orderDAO.getCount(queryCondition).getDataObject();
	}

	@Override
	public List<ShopDO> getShopsByCustomerID(Long customerId) {
		if(customerId == null) {
			return new ArrayList<ShopDO>();
		}
		ShopQueryCondition queryCondition = new ShopQueryCondition();
		queryCondition.customerId(customerId);
		Result<List<ShopDO>> result = shopDAO.getPage(queryCondition);
		if(result!=null && result.isSuccess()){
			return result.getDataObject();
		} else {
			return new ArrayList<ShopDO>();
		}
	}


	@Override
	public ShopDO getShopByProdId(Long prodId) {
		Result<ProductDO> result = productDAO.getById(prodId);
		if(result!=null && result.isSuccess()) {
			ProductDO product = result.getDataObject();
			if(product!=null) {
				return shopDAO.getById(product.getShopId()).getDataObject();
			}
		}
		return null;
	}


	@Override
	public List<ProductDO> getProdByIds(Long[] id) {
		Result<List<ProductDO>> result = productDAO.getByIds(id);
		if(result!=null && result.isSuccess()) {
			return result.getDataObject();
		}
		return Lists.newArrayList();
	}


	@Override
	public Map<String,Long> updateShopDetail(ShopDetailVO shopDetailVO) {
		Map<String,Long> resultMap = Maps.newHashMap();
		ShopDO shopDO = shopDetailVO.getShopDO();
		CustomerDO customer = shopDetailVO.getCustomerDO();
		if(customer.getId()==null){
			Result<Long> result = customerDAO.insert(customer);
			if(result!=null && result.isSuccess()) {
				shopDO.setCustomerId(result.getDataObject());
				shopDetailVO.setCustomerId(result.getDataObject());
			}
		} else {
			customerDAO.update(customer);
		}
		resultMap.put("customerId", shopDetailVO.getCustomerId());
		
		String shopName = shopDO.getName();
		String pinyin = PinYinTools.getPinYin(shopName);
		shopDO.setPinyin(pinyin);
		shopDO.setAlphabet(pinyin.substring(0,1).toUpperCase());
		if(shopDO.getId() == null){
			Long shopId = shopDAO.insert(shopDO).getDataObject();
			shopDetailVO.setId(shopId);
		} else {
			shopDAO.update(shopDO);
		}
		
		PeopleContactDO peopleContact = shopDetailVO.getPeopleContactDO();
		if(peopleContact.getId()==null){
			peopleContact.setForeignId(shopDetailVO.getId());
			Result<Long> result = peopleContactDAO.insert(peopleContact);
			if(result!=null && result.isSuccess()) {
				shopDetailVO.setContactId(result.getDataObject());
			}
		} else {
			peopleContactDAO.update(peopleContact);
		}
		resultMap.put("contactId", shopDetailVO.getContactId());
		
		Long shopId = shopDO.getId();
		shopCategoryDAO.deleteByShopId(shopId);
		for(Long cateId : shopDetailVO.getShopCates()){
			ShopCategoryDO forInsert = new ShopCategoryDO();
			forInsert.setCateId(cateId);
			forInsert.setShopId(shopId);
			shopCategoryDAO.insert(forInsert);
		}
		
		resultMap.put("shopId", shopDetailVO.getId());
		return resultMap;
	}

	@Override
	public boolean isFav(Long shopId, Long customerId) {
		CustomerFavQueryCondition queryCondition = new CustomerFavQueryCondition();
		queryCondition.customerId(customerId).shopId(shopId);
		List<CustomerFavDO> favList = customerFavDAO.getPage(queryCondition).getDataObject();
		return CollectionTools.isNotEmpty(favList);
	}

	@Override
	public Paging<ShopVO> getShopVOByCateId(Long shopCateId,Double lng, Double lat,int page, int pageSize) {
		int start = (page-1) * pageSize;
		int totalSize = shopCategoryDAO.getByCateIdCount(shopCateId);
		Paging<ShopVO> shopPage = new Paging<ShopVO>(start, pageSize, totalSize, 5, null);
		
		List<Long> shopIdList = shopCategoryDAO.getShopIdByCateId(shopCateId,lng,lat,start,pageSize);
		List<ShopVO> resultList = Lists.newArrayList();
		for(Long id : shopIdList){
			resultList.add(getShopVOById(id));
		}
		shopPage.setData(resultList);
		return shopPage;
	}

	@Override
	public ShopDO getShopById(Long shopId) {
		return shopDAO.getById(shopId).getDataObject();
	}

	@Override
	public List<ShopVO> getAll() {
		Result<List<ShopDO>> shops = shopDAO.getAll();
		if(shops.isSuccess() && CollectionTools.isNotEmpty(shops.getDataObject())){
			List<ShopDO> list = shops.getDataObject();
			List<ShopVO> resultList = Lists.newArrayList();
			for(ShopDO shop : list){
				resultList.add(getShopVO(shop));
			}
			return resultList;
		}
		return Lists.newArrayList();
	}

	@Override
	public Paging<ShopVO> getAllByDistance(Double lng, Double lat,int page, int pageSize) {
		int start = (page-1) * pageSize;
		int totalSize = shopDAO.getAllByDistanceCount();
		Paging<ShopVO> shopPage = new Paging<ShopVO>(start, pageSize, totalSize, 5, null);
		Result<List<ShopDO>> shops = shopDAO.getAllByDistance(lng, lat, start, pageSize);
		if(shops.isSuccess() && CollectionTools.isNotEmpty(shops.getDataObject())){
			List<ShopDO> list = shops.getDataObject();
			List<ShopVO> resultList = Lists.newArrayList();
			for(ShopDO shop : list){
				resultList.add(getShopVO(shop));
			}
			shopPage.setData(resultList);
		} else {
			shopPage.setData(new ArrayList<ShopVO>());
		}
		return shopPage;
	}

	@Override
	public boolean clearShopBinding(Long shopId, int pos) {
		ShopDO shop = new ShopDO();
		shop.setId(shopId);
		if(pos == 1) {
			shop.setCid1("DISMISS");
		}
		if(pos == 2) {
			shop.setCid2("DISMISS");
		}
		if(pos == 3) {
			shop.setCid3("DISMISS");
		}
		if(pos == 4) {
			shop.setCid4("DISMISS");
		}
		if(pos == 5) {
			shop.setCid5("DISMISS");
		}
		return shopDAO.clearShopBinding(shop).getDataObject();
	}

	
	@Override
	public Paging<ShopDO> getFull(ShopQueryCondition queryCondition) {
		int totalSize = shopDAO.getCount(queryCondition).getDataObject();
		@SuppressWarnings("unchecked")
		Paging<ShopDO> shopPage = queryCondition.getPaging(totalSize, 5);
		List<ShopDO> list = shopDAO.getPage(queryCondition).getDataObject();
		shopPage.setData(list);
		return shopPage;
	}
}
