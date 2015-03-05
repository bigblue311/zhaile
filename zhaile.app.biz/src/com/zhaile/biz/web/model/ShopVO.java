package com.zhaile.biz.web.model;

import java.io.Serializable;
import java.util.List;

import com.zhaile.dal.model.CategoryDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ShopCategoryDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShopTagDO;

public class ShopVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 596517280286230099L;
	private ShopDO shopDO;
	private CustomerDO customerDO;
	private PeopleContactDO contactDO;
	private List<CategoryDO> categoryList;
	private List<ShopCategoryDO> shopCateList;
	private List<ShopTagDO> tagList;
	private List<CommentVO> commentList;
	private int totalComment;
	private int totalSold;
	private boolean isFav = false;
	
	public ShopDO getShopDO() {
		return shopDO;
	}
	public void setShopDO(ShopDO shopDO) {
		this.shopDO = shopDO;
	}
	public PeopleContactDO getContactDO() {
		return contactDO;
	}
	public void setContactDO(PeopleContactDO contactDO) {
		this.contactDO = contactDO;
	}
	public List<ShopTagDO> getTagList() {
		return tagList;
	}
	public void setTagList(List<ShopTagDO> tagList) {
		this.tagList = tagList;
	}
	public List<CategoryDO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryDO> categoryList) {
		this.categoryList = categoryList;
	}
	public CustomerDO getCustomerDO() {
		return customerDO;
	}
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}
	public List<CommentVO> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentVO> commentList) {
		this.commentList = commentList;
	}
	public int getTotalComment() {
		return totalComment;
	}
	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}
	public int getTotalSold() {
		return totalSold;
	}
	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}
	public boolean getIsFav() {
		return isFav;
	}
	public void setIsFav(boolean isFav) {
		this.isFav = isFav;
	}
	public List<ShopCategoryDO> getShopCateList() {
		return shopCateList;
	}
	public void setShopCateList(List<ShopCategoryDO> shopCateList) {
		this.shopCateList = shopCateList;
	}
}
