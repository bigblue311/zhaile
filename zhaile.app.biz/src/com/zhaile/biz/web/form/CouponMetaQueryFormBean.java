package com.zhaile.biz.web.form;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public class CouponMetaQueryFormBean extends QueryFormBean{
	private String enable;		//是否有效 0：有效1：无效
	private String valid;		//是否有效切在有效期内 0：有效1：无效
	private Long   shopId;		//店铺ID
	private String chargable;	//是否找零 0：找零1：不找零
	private String topupable;	//是否可充值 0：可充值1：不可充值
	private String refundable;	//是否支持退款 0：可退款1：不可退款
	private String deductable;	//是否支持抵外卖费 0：可抵用1：不可抵用
	private Double fullsent;	//满就送 >0 就是满就送
	private Double sales;		//出售价格 >0 就可以通过购买获得
	private String couponName;	//卡的名称
	private String keyword;		//店铺关键字
	private int page = 1;
	private int pageSize = 5;
	
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getChargable() {
		return chargable;
	}
	public void setChargable(String chargable) {
		this.chargable = chargable;
	}
	public String getTopupable() {
		return topupable;
	}
	public void setTopupable(String topupable) {
		this.topupable = topupable;
	}
	public String getRefundable() {
		return refundable;
	}
	public void setRefundable(String refundable) {
		this.refundable = refundable;
	}
	public String getDeductable() {
		return deductable;
	}
	public void setDeductable(String deductable) {
		this.deductable = deductable;
	}
	public Double getFullsent() {
		return fullsent;
	}
	public void setFullsent(Double fullsent) {
		this.fullsent = fullsent;
	}
	public Double getSales() {
		return sales;
	}
	public void setSales(Double sales) {
		this.sales = sales;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public CouponMetaQueryCondition getCouponMetaQueryCondition(){
		CouponMetaQueryCondition queryCondition = new CouponMetaQueryCondition();
		queryCondition.clearEnable();
		queryCondition.shopId(shopId).keyword(keyword);
		queryCondition.enable(enable).valid(valid);
		queryCondition.chargable(chargable).topupable(topupable).refundable(refundable).deductable(deductable);
		queryCondition.fullsent(fullsent).sales(sales);
		if(StringTools.isNotEmpty(couponName)){
			queryCondition.name(couponName);
		}
		if(modifyStart!=null){
			queryCondition.gmtModifyStart(modifyStart);
		}
		if(modifyEnd!=null){
			queryCondition.gmtModifyEnd(modifyEnd);
		}
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		return queryCondition;
	}
}
