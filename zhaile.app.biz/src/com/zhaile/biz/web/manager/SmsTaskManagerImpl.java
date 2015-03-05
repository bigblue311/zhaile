package com.zhaile.biz.web.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.common.tools.TuiSongTools;
import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.dao.SmsTaskDAO;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.enumerate.SmsTypeEnum;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public class SmsTaskManagerImpl extends CommonManager implements SmsTaskManager {
	
	@Autowired
	private SmsTaskDAO smsTaskDAO;
	
	@Override
	public void prepareSmsTask(PaymentQueryJson paymentJson){
		//先准备给客户发送的短信
		SmsTaskDO smsTaskDO = new SmsTaskDO();
		smsTaskDO.setMobile(paymentJson.getSmsMobile());
		smsTaskDO.setText(paymentJson.getSmsText());
		smsTaskDO.setType(SmsTypeEnum.客户订单短信.getCode());
		smsTaskDO.setCallBackUrl("admin/json/updateSmsStatus.json?paymentId="+paymentJson.getId()+"&smsStatus=[status]");
		createSmsTask(smsTaskDO);
	}
	
	@Override
	public void prepareShopReceipt(List<ShoppingCarVO> list,Collection<ShopDO> shopList,String contact,String comment){
		//准备店铺短信
		if(CollectionTools.isEmpty(list)){
			return;
		}
		if(CollectionTools.isEmpty(shopList)){
			return;
		}
		for(ShopDO shopDO : shopList){
			StringBuilder smsText = new StringBuilder();
			Double totalPrice = 0d;
			smsText.append("客户：");
			smsText.append(contact);
			smsText.append("\n");
			for(ShoppingCarVO shoppingCarVO : list){
				ShopDO shop =  shoppingCarVO.getProductVO().getShopVO().getShopDO();
				ProductDO prod = shoppingCarVO.getProductVO().getProductDO();
				ShoppingCarDO shoppingCar = shoppingCarVO.getShopCar();
				if(shop.getId() == shopDO.getId()){
					smsText.append(prod.getName());
					smsText.append("(");
					smsText.append(prod.getPrice());
					smsText.append("元*");
					smsText.append(shoppingCar.getQuantity());
					smsText.append("份=");
					smsText.append(shoppingCarVO.getTotalPrice());
					smsText.append("元)");
					smsText.append("\n");
					totalPrice += shoppingCarVO.getTotalPrice();
				}
			}
			smsText.append("合计：");
			smsText.append(totalPrice);
			smsText.append("元(不含外卖费)");
			smsText.append("\n");
			smsText.append("备注:");
			smsText.append(comment);
			if(totalPrice>0d){
				List<String> mobileList = getMobileList(shopDO);
				for(String mobile:mobileList){
					SmsTaskDO smsTaskDO = new SmsTaskDO();
					smsTaskDO.setMobile(mobile);
					smsTaskDO.setText(smsText.toString());
					smsTaskDO.setType(SmsTypeEnum.商家小票短信.getCode());
					smsTaskDO.setCallBackUrl("admin/json/updateSmsStatus.json?smsStatus=[status]");
					createSmsTask(smsTaskDO);
				}
				
				List<String> cidList = getCidList(shopDO);
				for(String cid:cidList){
					TuiSongTools.push(cid, smsText.toString());
				}
				
			}
		}
	}
	
	private List<String> getMobileList(ShopDO shop){
		List<String> mobileList = Lists.newArrayList();
		if(shop==null) return mobileList;
		if(StringTools.isNotEmpty(shop.getCid1()) && StringTools.isMobile(shop.getCid1())) mobileList.add(shop.getCid1());
		if(StringTools.isNotEmpty(shop.getCid2()) && StringTools.isMobile(shop.getCid2())) mobileList.add(shop.getCid2());
		if(StringTools.isNotEmpty(shop.getCid3()) && StringTools.isMobile(shop.getCid3())) mobileList.add(shop.getCid3());
		if(StringTools.isNotEmpty(shop.getCid4()) && StringTools.isMobile(shop.getCid4())) mobileList.add(shop.getCid4());
		if(StringTools.isNotEmpty(shop.getCid5()) && StringTools.isMobile(shop.getCid5())) mobileList.add(shop.getCid5());
		return mobileList;
	}
	
	private List<String> getCidList(ShopDO shop){
		List<String> cidList = Lists.newArrayList();
		if(shop==null) return cidList;
		if(StringTools.isNotEmpty(shop.getCid1()) && !StringTools.isMobile(shop.getCid1())) cidList.add(shop.getCid1());
		if(StringTools.isNotEmpty(shop.getCid2()) && !StringTools.isMobile(shop.getCid2())) cidList.add(shop.getCid2());
		if(StringTools.isNotEmpty(shop.getCid3()) && !StringTools.isMobile(shop.getCid3())) cidList.add(shop.getCid3());
		if(StringTools.isNotEmpty(shop.getCid4()) && !StringTools.isMobile(shop.getCid4())) cidList.add(shop.getCid4());
		if(StringTools.isNotEmpty(shop.getCid5()) && !StringTools.isMobile(shop.getCid5())) cidList.add(shop.getCid5());
		return cidList;
	}
	
	private void createSmsTask(SmsTaskDO smsTaskDO) {
		smsTaskDO.setStatus(SmsStatusEnum.未发送.getCode());
		long id = smsTaskDAO.insert(smsTaskDO).getDataObject();
		smsTaskDO.setId(id);
		smsTaskDO.setRetry(0);
		String callbackUrl = smsTaskDO.getCallBackUrl();
		if(StringTools.isNotEmpty(callbackUrl)){
			if(callbackUrl.contains("?")){
				callbackUrl += "&id="+id;
			} else {
				callbackUrl += "?id="+id;
			}
		}
		smsTaskDO.setCallBackUrl(callbackUrl);
		smsTaskDAO.update(smsTaskDO);
	}

	@Override
	public boolean updateSmsStatus(Long id, String status) {
		SmsTaskDO smsTaskDO = new SmsTaskDO();
		smsTaskDO.setId(id);
		smsTaskDO.setStatus(status);
		return smsTaskDAO.update(smsTaskDO).getDataObject();
	}

	@Override
	public boolean retry(Long id) {
		return smsTaskDAO.retry(id).getDataObject();
	}

	@Override
	public List<SmsTaskDO> querySmsTaskPending() {
		smsTaskDAO.expireSms();
		SmsTaskQueryCondition queryCondition = new SmsTaskQueryCondition();
		queryCondition.status(SmsStatusEnum.未发送.getCode());
		return smsTaskDAO.getPage(queryCondition).getDataObject();
	}

	@Override
	public List<SmsTaskDO> querySmsTasks(SmsTaskQueryCondition queryCondition) {
		return smsTaskDAO.getPage(queryCondition).getDataObject();
	}
}
