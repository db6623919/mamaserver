package com.mama.server.main.controller.handler.merchant;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.JsonUtil;
import com.mama.server.util.pay.PayConstants;

/** 支付宝支付异步通知相关 */
@Component
public class AliPayAsyncNotifyHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(AliPayAsyncNotifyHandler.class);

	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		try {
			logger.info("Start to aliPayAsyncNotify, param is {}.", param.toString());
			// TODO 验签成功后
			//按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验
			//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号--原支付请求的商户订单号
			String out_trade_no = (String)param.get("out_trade_no");
			//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）--本次交易支付的订单金额，单位为人民币（元）
			String total_amount = (String)param.get("total_amount");
			//3、校验通知中的seller_id（或者seller_email) 
			//是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			String seller_id = (String)param.get("seller_id");
			//4、验证app_id是否为该商户本身。
			String app_id = (String)param.get("app_id");
			
			boolean validFlag = false;
			//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号--原支付请求的商户订单号
			OrderPayDO orderPay = mainService.selectOrderPayByPayId(out_trade_no);
			if(orderPay != null) {
				String fee = orderPay.getFee();
				long realFee = new BigDecimal(fee).longValue();
				long alipayFee = new BigDecimal(total_amount).longValue();
				//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）--本次交易支付的订单金额，单位为人民币（元）
				if(realFee - alipayFee == 0) {
					//3、校验通知中的seller_id（或者seller_email) 
					if(PayConstants.ALIPAY_SELLER_ID.equals(seller_id)) {
						//4、验证app_id是否为该商户本身。
						if(PayConstants.APP_ID.equals(app_id)) {
							validFlag = true;
						}
					}
				}
			}
			
			if(validFlag) {
				//在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功
				/** WAIT_BUYER_PAY:交易创建，等待买家付款;TRADE_CLOSED:未付款交易超时关闭，或支付完成后全额退款
				 *  TRADE_SUCCESS:交易支付成功;TRADE_FINISHED:交易结束，不可退款
				 *  */
				String trade_status = (String)param.get("trade_status");
				String orderId = orderPay.getOrderId();
				OrderPo orderPo = mainService.queryOrderPoByOrderId(orderId);
				String uid = orderPo.getUid();
				String payId = orderPay.getPayId();
				String pay_type = orderPay.getPayType();
				//校验成功后在response中返回success，校验失败返回failure
				if("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
					mainService.updateAlipayAsyncPayStatus(uid, orderId, payId, pay_type);
					genSuccOutputMap();
					return outputMap;
				} else if("TRADE_CLOSED".equals(trade_status)) {
					mainService.updateAlipayAsyncPayStatusForFail(uid, orderId, payId, pay_type);
					genSuccOutputMap();
					return outputMap;
				}
			}
			genErrOutputMap("interface error");
			logger.info("支付宝异步通知处理返回结果：" + JsonUtil.objectToString(outputMap));
		} catch(Exception e) {
			logger.error("aliPayAsyncNotify过程中出现异常！", e);
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
}