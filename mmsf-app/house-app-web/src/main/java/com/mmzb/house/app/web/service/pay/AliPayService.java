package com.mmzb.house.app.web.service.pay;

import com.mmzb.house.app.vo.pay.DataResponse;

/** 支付宝支付相关 */
public interface AliPayService {
	/** 根据订单信息和当前登录用户信息处理订单相关信息 */
	public DataResponse handleByAliPayOrderInfo(String out_trade_no, String  money);
	
}