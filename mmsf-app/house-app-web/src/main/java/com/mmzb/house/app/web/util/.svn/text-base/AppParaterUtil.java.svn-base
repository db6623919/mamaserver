package com.mmzb.house.app.web.util;

import javax.servlet.http.HttpServletRequest;

/** app参数处理公共类 */
public class AppParaterUtil {
	/** 获取客户端产品类型
	 * 	x-client-device ：iOS： 2  安卓：Android
	 * */
	public static String getProductType(HttpServletRequest request) {
		String productType = request.getHeader("x-client-device");//iOS： 2  安卓：Android
		if("2".equals(productType) || "iOS".equals(productType)) {
			productType = "ios";
		} else if("Android".equals(productType)) {
			productType = "android";
		} else if("h5".equals(productType)) {
			productType = "h5";
		}
		return productType;
	}
	
	/** 获取客户端来源
	 * 	x-client-bundle-id
	 * 来源：financecollege、金融学院；mmsfang、妈妈送房；wallet、妈妈钱包；
	 *  */
	public static String getClientSource(HttpServletRequest request) {
		String source = request.getHeader("x-client-bundle-id");//来源：financecollege、金融学院；mmsfang、妈妈送房；wallet、妈妈钱包；
		if("com.mmmoney.mamawallet".equals(source) || "com.mmmoney.app".equals(source)) {
			source = "wallet";
		} else if("com.mmsfang.trip".equals(source)) {
			source = "mmsfang";
		}
		return source;
	}
}
