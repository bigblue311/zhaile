package com.victor.framework.common.tools;

import java.math.BigDecimal;

public class BigDecimalTools {
	
	public static BigDecimal getBigDecimal(BigDecimal bigDecimal){
		return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal newBigDecimal(Object obj){
		return new BigDecimal(obj.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static String toString(BigDecimal bigDecimal){
		return getBigDecimal(bigDecimal).toPlainString();
	}
}
