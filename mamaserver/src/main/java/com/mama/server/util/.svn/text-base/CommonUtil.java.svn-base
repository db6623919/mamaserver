package com.mama.server.util;

/**
 * 公共
 * @author whl
 *
 */
public class CommonUtil {

	/** 
	 * 判断价格范围 
	 * 【200以下】、【200到300】、【300到500】和【500以上】
	 * */
	public static int getPriceRange(double price) {
		if(price<200) {
			return 1;
		}
		
		if(price >= 200 && price < 300) {
			return 2;
		}
		
		if(price >= 300 && price < 500) {
			return 3;
		}
		
		if(price >= 500) {
			return 4;
		}
		return 0;
	}
}
