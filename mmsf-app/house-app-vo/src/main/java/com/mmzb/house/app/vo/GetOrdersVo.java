package com.mmzb.house.app.vo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 订单列表
 * @author whl
 *
 */
public class GetOrdersVo {
	/** 订单列表 */
	private ArrayList<HashMap<String, Object>> orders;
	private int num;
	private int totalPage;
	private int totalNum;
	
	public ArrayList<HashMap<String, Object>> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<HashMap<String, Object>> orders) {
		this.orders = orders;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
}
