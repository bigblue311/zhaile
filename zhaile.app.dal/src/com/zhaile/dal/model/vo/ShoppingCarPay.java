package com.zhaile.dal.model.vo;

import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShoppingCarDO;

public class ShoppingCarPay {
	private ShoppingCarDO shopCar;
	private ProductDO productDO;
	
	public ShoppingCarDO getShopCar() {
		return shopCar;
	}
	public void setShopCar(ShoppingCarDO shopCar) {
		this.shopCar = shopCar;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
}
