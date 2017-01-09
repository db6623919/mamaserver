package com.mmzb.house.web.model;

import java.io.Serializable;

public class WeixinPayBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510100677013220155L;
	
	/**
	 *  业务订单号
	 */
	private String orderNo;
	
	/**
	 *  支付金额
	 */
	private String payAmt;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	
}
