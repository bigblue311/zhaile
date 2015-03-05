package com.zhaile.biz.web.model;

import java.io.Serializable;
import java.util.List;

import com.zhaile.dal.model.CategoryDO;
import com.zhaile.dal.model.ProductDO;

public class ProductVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2752039074651763407L;
	private ShopVO shopVO;
	private ProductDO productDO;
	private CategoryDO categoryDO;
	private List<CommentVO> commentList;
	
	public ShopVO getShopVO() {
		return shopVO;
	}
	public void setShopVO(ShopVO shopVO) {
		this.shopVO = shopVO;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public List<CommentVO> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentVO> commentList) {
		this.commentList = commentList;
	}
	public CategoryDO getCategoryDO() {
		return categoryDO;
	}
	public void setCategoryDO(CategoryDO categoryDO) {
		this.categoryDO = categoryDO;
	}
}
