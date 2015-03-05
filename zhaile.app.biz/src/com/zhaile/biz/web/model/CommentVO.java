package com.zhaile.biz.web.model;

import java.io.Serializable;

import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.model.CustomerDO;

public class CommentVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5408524517162331807L;
	private CustomerCommentDO comment;
	private CustomerDO customer;
	
	public CustomerCommentDO getComment() {
		return comment;
	}
	public void setComment(CustomerCommentDO comment) {
		this.comment = comment;
	}
	public CustomerDO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDO customer) {
		this.customer = customer;
	}
}
