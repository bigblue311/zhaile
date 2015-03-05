package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ServiceTypeEnum;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.SourceEnum;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.enumerate.PaymentTypeEnum;
import com.zhaile.dal.enumerate.PaymentStatusEnum;

public class PaymentDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = -503869978227704704L;
	private Long customerId;	//客户ID
	private Double netpay;		//总价格
	private Double charge;		//服务费
	private String serviceType;	//服务方式
	private String status;		//状态
	private Double received;	//实际收到金额
	private String comment;		//评论
	private String contact;		//联系方式
	private Long employeeId;	//员工ID
	private String source;		//渠道来源
	private String smsStatus;	//短信状态
	private String smsMobile;	//短信对象
	private String mapAdd;		//地图上的地址
	private Double lng;			//地图坐标lng
	private Double lat;			//地图坐标lat
	private String ip;			//用户的IP
	private String paymentType;	//支付方式
	private String paymentStatus;//支付状态
	private String paymentCode;	//第三方交易号
	private String paymentResp;	//第三方交易状态
	
	public void translate(){
		this.serviceType = ServiceTypeEnum.getByCode(this.serviceType)==null?this.serviceType:ServiceTypeEnum.getByCode(this.serviceType).getDesc();
		this.status = DeliveryStatusEnum.getByCode(this.status)==null?this.status:DeliveryStatusEnum.getByCode(this.status).getDesc();
		this.source = SourceEnum.getByCode(this.source)==null?this.status:SourceEnum.getByCode(this.source).getDesc();
		this.smsStatus = SmsStatusEnum.getByCode(this.smsStatus)==null?this.smsStatus:SmsStatusEnum.getByCode(this.smsStatus).getDesc();
		this.paymentType = PaymentTypeEnum.getByCode(this.paymentType)==null?this.paymentType:PaymentTypeEnum.getByCode(this.paymentType).getDesc();
		this.paymentStatus = PaymentStatusEnum.getByCode(this.paymentStatus)==null?this.paymentStatus:PaymentStatusEnum.getByCode(this.paymentStatus).getDesc();
	}
	
	public void format(){
		this.serviceType = ServiceTypeEnum.getByDesc(this.serviceType)==null?this.serviceType:ServiceTypeEnum.getByDesc(this.serviceType).getCode();
		this.status = DeliveryStatusEnum.getByDesc(this.status)==null?this.status:DeliveryStatusEnum.getByDesc(this.status).getCode();
		this.source = SourceEnum.getByDesc(this.source)==null?this.status:SourceEnum.getByDesc(this.source).getCode();
		this.smsStatus = SmsStatusEnum.getByDesc(this.smsStatus)==null?this.smsStatus:SmsStatusEnum.getByDesc(this.smsStatus).getCode();
		this.paymentType = PaymentTypeEnum.getByDesc(this.paymentType)==null?this.paymentType:PaymentTypeEnum.getByDesc(this.paymentType).getCode();
		this.paymentStatus = PaymentStatusEnum.getByDesc(this.paymentStatus)==null?this.paymentStatus:PaymentStatusEnum.getByDesc(this.paymentStatus).getCode();
	}
	
	public String getSmsText(){
		StringBuilder sb = new StringBuilder();
		sb.append("【宅乐网】您的订单已经确认");
		sb.append(",总价");
		sb.append(this.received);
		sb.append("元,订餐咨询:63106676。【www.fyzhaile.com】");
		return sb.toString();
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Double getNetpay() {
		return netpay;
	}
	public void setNetpay(Double netpay) {
		this.netpay = netpay;
	}
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getReceived() {
		return received;
	}
	public void setReceived(Double received) {
		this.received = received;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	public String getSmsMobile() {
		return smsMobile;
	}
	public void setSmsMobile(String smsMobile) {
		this.smsMobile = smsMobile;
	}
	public String getMapAdd() {
		return mapAdd;
	}
	public void setMapAdd(String mapAdd) {
		this.mapAdd = mapAdd;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentResp() {
		return paymentResp;
	}
	public void setPaymentResp(String paymentResp) {
		this.paymentResp = paymentResp;
	}
}
