package com.mmzb.house.web.util;

/**
 * 积分工具类
 */
public class PointUtil {
	
	/**
	 * 根据邀请人数来获取对应的积分
	 * 
	 * @param invitedNumber 邀请人数
	 * @return
	 */
	public static int getPoint(int invitedNumber) {
		int point = 0;
		if (invitedNumber <= 10) {
			point = 11;
		} else if (invitedNumber <= 20) {
			point = 15;
		} else {
			point = 20;
		}
		return point;
	}
	
}
