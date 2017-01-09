package com.mama.server.main.controller.handler.clickFarming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.controller.handler.main.SendOrderPayCodeHandler;
import com.mama.server.main.dao.OrderPayDO;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.AppProperties;
import com.mama.server.util.JsonUtil;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.pay.PayConstants;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;

/** 刷单查询订单状态 */
@Component
public class CFOrderStatusHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(CFOrderStatusHandler.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	@Autowired
	private SendOrderPayCodeHandler sendOrderPayCodeHandler;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		if (logger.isInfoEnabled()) {
			logger.info("Start to update order status, param is {}.", param.toString());
		}
		try {
			String orderId = (String) param.get("orderId");//业务订单号
			String  uid            =  (String) param.get("uid");//会员编号
			OrderPo op = new OrderPo();
			op.setOrderId(orderId);
			List<OrderPo> orderList = mainService.getOrderByAllParam(op);
			if(orderList != null && orderList.size() > 0) {
				OrderPo houseOrder = orderList.get(0);
				int status = houseOrder.getStatus();
				if(status == 11) {//11、待入住，支付成功后即为待入住状态
					dataMap.put("code", "1");
					dataMap.put("msg", "该订单已支付成功！");
				} else {//9、审核通过的才可以进行支付
					List<OrderPayDO> payOrderList = mainService.queryPayOrderListByOrderId(orderId);
					if(payOrderList != null && payOrderList.size() > 0) {
						for(OrderPayDO orderPayDO : payOrderList) {
							boolean payFlag = false;
							boolean hasSuccessFlag = false;
							/** 支付方式编码:wxpay:微信支付；alipay:支付宝支付 */
							String productCode = orderPayDO.getPayType();
							/** 检查是否存在未支付成功的记录 */
							if(!"PAY_SUCCESS".equals(orderPayDO.getStatus())) {
								if("wxpay".equals(productCode)) {//检查微信支付接口
									payFlag = queryWeiXinOrderStatus(orderPayDO,uid,productCode);
							    } else if("alipay".equals(productCode)) {//检查支付宝支付接口
							    	payFlag = queryAliPayOrderStatus(uid,orderPayDO);
							    }
							} else {
								hasSuccessFlag = true;
							}
							
							/** 保存在支付成功的记录 */
							if(payFlag || hasSuccessFlag) {
								/** 修改房源订单状态 */
								updateOrderStatus(orderPayDO.getOrderId(),uid);
								dataMap.put("resultCode", "0");
							}
						}
					} else {
						/** 未查询到相关订单，前段提示 */
						dataMap.put("resultCode", "7");
						dataMap.put("resultMsg", "未查询到对应订单号，请联系管理员！");
					}
					
				}
			} else {
				dataMap.put("resultCode", "4");
				dataMap.put("resultMsg", "未查询到对应的房源订单信息！");
			}
			genSuccOutputMap();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询订单状态接口错误：", e);
			outputMap.put("exception", e.getMessage());
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
	
	//微信支付接口检查
	public boolean queryWeiXinOrderStatus(OrderPayDO orderPay,String uid,String productCode) {
			boolean payFlag = false;
			/** 订单支付来源：ios：ios客户端；android：android客户端；h5：h5客户端； */
			String  trade_type	   =  orderPay.getSource();	// 微信统一下单贯口，trade_type
			if (trade_type.equals("ios") || trade_type.equals("android")) {
				trade_type = "APP";
			}
			Map<String,String> extension = new HashMap<String , String>();
			extension.put("trade_type", trade_type);
			try {
				//2、请求核心支付系统
		        HessianProxyFactory factory = new HessianProxyFactory();  
		        ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, PropertiesUtils.getChargeCenterUrl());
		        /** 尚未支付成功 */
		        String out_trade_no = orderPay.getPayId();//商户订单号
		        ChargeRequest chargeRequest = new ChargeRequest();
		        chargeRequest.setOrderId(out_trade_no);
		        chargeRequest.setProductCode(productCode);
		        chargeRequest.setMemberID(uid);
		        chargeRequest.setExtension(extension);
		        ChargeResponse chargeResponse = facade.queryOrderStatus(chargeRequest);
		        logger.info("=========订单查询结果为=========" + chargeResponse);
		        //查询订单状态
		        String tradeState = "";
				if (true == chargeResponse.isSuccess()) {
					Map<String, String> data = chargeResponse.getData();
					/** 交易状态:SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 
					REVOKED—已撤销（刷卡支付）USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败) */
					tradeState = data.get("trade_state");
					if("SUCCESS".equals(tradeState)) {
						/** 修改torder的状态为11，修改支付订单状态为PAY_SUCCESS */
						mainService.updateHouseOrderAnaPayStatus(uid, orderPay.getOrderId(), out_trade_no);
						payFlag = true;
					} 
				} 
			} catch (Exception e) {
				logger.error("微信：查询订单状态接口错误：", e);
				outputMap.put("exception", e.getMessage());
			}
			return payFlag;
	}
	
	/** 处理支付宝相关订单状态  */
	public boolean queryAliPayOrderStatus(String uid, OrderPayDO orderPayDO) {
		boolean payFlag = false;
		try {
			String out_trade_no = orderPayDO.getPayId();//商户订单号
			AlipayClient alipayClient = PayConstants.getAlipayClient();//获得初始化的AlipayClient
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
			Map<String, String> params = new HashMap<String, String>();
			params.put("out_trade_no", out_trade_no);
			String bizContent = JsonUtil.objectToString(params);
			request.setBizContent(bizContent);//设置业务参数
			AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
			logger.info(out_trade_no + "支付宝查询接口调用返回信息：" + JsonUtil.objectToString(response));
			// TODO 根据response中的结果继续业务逻辑处理
			String code = response.getCode();
			if("10000".equals(code)) {
				/** 业务处理成功 */
				/** 修改torder的状态为11，修改支付订单状态为PAY_SUCCESS */
				mainService.updateHouseOrderAnaPayStatus(uid, orderPayDO.getOrderId(), out_trade_no);
				payFlag = true;
			} 
		} catch (Exception e) {
			logger.error("支付宝：查询订单状态接口错误：", e);
			outputMap.put("exception", e.getMessage());
		}
		return payFlag;
	}
	
	//修改订单状态
	public void updateOrderStatus(String orderId,String uid) {
		OrderPo orderPo = new OrderPo();
		orderPo.setOrderId(orderId);
		orderPo.setStatus(ConstValue.ORDER_MERCHANT_VERIFIED);
		orderPo.setUid(uid);
		mainService.updateOrder(orderPo);
		logger.info("queryOrderStatusHandler修改状态，uid:" + uid + ",orderId=" + orderId);
	}
}
