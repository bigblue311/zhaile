package com.zhaile.biz.web.json;

import java.util.List;

public class PaymentStatisticJson {
	private int totalCount;
	private int totalComment;
	private int mapMarkerTaskCount;
	private List<PaymentAlertJson> statusList;
	private Double totalNetPay;
	private Double totalCharge;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<PaymentAlertJson> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<PaymentAlertJson> statusList) {
		this.statusList = statusList;
	}
	public Double getTotalNetPay() {
		return totalNetPay;
	}
	public void setTotalNetPay(Double totalNetPay) {
		this.totalNetPay = totalNetPay;
	}
	public Double getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}
	public int getTotalComment() {
		return totalComment;
	}
	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}
	public int getMapMarkerTaskCount() {
		return mapMarkerTaskCount;
	}
	public void setMapMarkerTaskCount(int mapMarkerTaskCount) {
		this.mapMarkerTaskCount = mapMarkerTaskCount;
	}
}
