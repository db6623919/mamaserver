package com.mama.server.main.controller.handler.merchant;

import java.net.MalformedURLException;
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
import com.mama.server.main.dao.model.HouseCardPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.util.AppProperties;
import com.mama.server.util.JsonUtil;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.pay.PayConstants;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;

/** 查询订单状态 */
@Component
public class QueryOrderStatusHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(QueryOrderStatusHandler.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	@Autowired
	private SendOrderPayCodeHandler sendOrderPayCodeHandler;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		if (logger.isInfoEnabled()) {
			logger.info("Start to update order status, param is {}.", param.toString());
		}
		String orderId = (String) param.get("orderId");//业务订单号
		String uid = (String) param.get("uid");//会员编号
		OrderPo op = new OrderPo();
		op.setOrderId(orderId);
//		op.setUid(uid);
		List<OrderPo> orderList = mainService.getOrderByAllParam(op);
		if(orderList != null && orderList.size() > 0) {
			OrderPo houseOrder = orderList.get(0);
			int status = houseOrder.getStatus();
			int ordType = houseOrder.getOrderType();
			if(status == 11) {//11、待入住，支付成功后即为待入住状态
				dataMap.put("resultCode", "1");
				dataMap.put("resultMsg", "该订单已支付成功！");
			} else {//9、审核通过的才可以进行支付
				List<OrderPayDO> payOrderList = mainService.queryPayOrderListByOrderId(orderId);
				if(payOrderList != null && payOrderList.size() > 0) {
					boolean payFlag = false;
					boolean hasSuccessFlag = false;
					/** 检查是否存在支付成功的记录 */
					for(OrderPayDO orderPayDO : payOrderList) {
						/** 支付方式编码:wxpay:微信支付；alipay:支付宝支付 */
						String productCode = orderPayDO.getPayType();
						/** 尚未支付成功 */
						if(!"PAY_SUCCESS".equals(orderPayDO.getStatus())) {
							if("wxpay".equals(productCode)) {
								String source = orderPayDO.getSource();// 微信统一下单贯口，trade_type
								/** 订单支付来源：ios：ios客户端；android：android客户端；h5：h5客户端； */
								payFlag = queryWeiXinOrderStatus(orderId, uid, orderPayDO, source);
						    } else if("alipay".equals(productCode)) {
						    	payFlag = queryAliPayOrderStatus(orderId, uid, orderPayDO);
						    }
						} else {
							hasSuccessFlag = true;
						}
					}
					/** 保存在支付成功的记录 */
					if(payFlag || hasSuccessFlag) {
						/** 修改房源订单状态 */
						OrderPo orderPo = new OrderPo();
						orderPo.setOrderId(orderId);
						orderPo.setStatus(ConstValue.ORDER_MERCHANT_VERIFIED);
						orderPo.setUid(uid);
						mainService.updateOrder(orderPo);
						logger.info("queryOrderStatusHandler修改状态，uid:" + uid + ",orderId=" + orderId);
						
						/** 已支付成功，则前端直接提示成功 */
						dataMap.put("resultCode", "1");
						dataMap.put("resultMsg", "该订单已支付成功！");
							
						sendPaySuccessSms(uid, orderId);
					}
				} else {
					/** 未查询到相关订单，前段提示 */
					dataMap.put("resultCode", "7");
					dataMap.put("resultMsg", "未查询到对应订单号，请联系管理员！");
				}
			}
			
			//money 金额 
			int payAmt = houseOrder.getPayAmt();
			int shopId = houseOrder.getShopId() == null ? 0 : houseOrder.getShopId();
			int houseId = houseOrder.getHouseId();
			int orderType = houseOrder.getOrderType();  // 订单类型：0-常规订单，1-线下扫码支付，2-n99
			String payType = houseOrder.getPay_type(); // 支付类型：wxpay-微信，alipay-支付宝
			if(payType == null || payType.trim().length() < 1) {
				OrderPayDO orderPayDO = mainService.queryLatestOrderPayByOrderId(orderId);
				if(orderPayDO != null) {
					payType = orderPayDO.getPayType();
					OrderPo orderPo = new OrderPo();
					orderPo.setOrderId(orderId);
					orderPo.setPay_type(payType);;
					mainService.updateOrder(orderPo);
				}
			}
			
			toAddHouseCard(op);//添加房券信息
			
			dataMap.put("payAmt", "" + payAmt);
			dataMap.put("shopId", "" + shopId);
			dataMap.put("houseId", "" + houseId);
			dataMap.put("orderType", "" + orderType);
			dataMap.put("payType", payType);
		} else {
			dataMap.put("resultCode", "4");
			dataMap.put("resultMsg", "未查询到对应的房源订单信息！");
		}
		if(dataMap.containsKey("resultCode")) {
			genSuccOutputMap();
		} else {
			genErrOutputMap("interface error");
		}
		return outputMap;
	}
	
	private void toAddHouseCard(OrderPo op){
		List<OrderPo> orderList = mainService.getOrderByAllParam(op);
		if(orderList != null && orderList.size() > 0) {
			logger.info("start to addHouseCard!");
			OrderPo houseOrder = orderList.get(0);
			int ordType = houseOrder.getOrderType();
			if (ordType == 3 && houseOrder.getStatus() == 11) {//房券订单
				int houseId = houseOrder.getHouseId();//房源ID
				HousePo house = new HousePo();
				String orderId = houseOrder.getOrderId();
				String uid = houseOrder.getUid();
				house.setHouseId(houseId);
				List<HousePo> hList = mainService.getHouseByAllParam(house);
				int shopId = houseOrder.getShopId();//shopId
				if (hList!=null) {
//					int cardPrice = hList.get(0).getMemTotalAmt();//房券价格
					String liveDetail = houseOrder.getLiveDetail();
					JSONObject jsonObject = JSONObject.parseObject(liveDetail);
					String phone = jsonObject.getString("userPhone");
					int totalRoomNum = houseOrder.getTotalRoomNum();
					
					//添加房券信息
					addHouseCard(houseId,shopId,orderId,uid,phone,totalRoomNum);
					
					op.setRetentionTime("2017-03-31");
					mainService.updateOrder(op);//更新有限时间
				}else {
					logger.error("添加房券信息时,找不到对应房源信息!");
				}
				
			}
		}

	}
	
	private void addHouseCard(int houseId,int shopId,String orderId, String uid, String phone,
			int totalRoomNum) {
    	
    	HousePo hp = new HousePo();
    	hp.setHouseId(houseId);
    	List<HousePo> houseList = mainService.getHouseByAllParam(hp);
    	int price = 0;
    	if (houseList!= null ) {
    		price = houseList.get(0).getMemTotalAmt();
		}
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderNo", orderId);
    	List<HouseCardPo> list = mainService.getHouseCardListByOrderId(map);
    	
    	if (list.size()==0) {
        	for (int i = 0; i < totalRoomNum; i++) {
        		HouseCardPo houseCardPo = new HouseCardPo();
        		houseCardPo.setHouseId(houseId);
        		houseCardPo.setBuyId(uid);
        		houseCardPo.setBuyPhone(phone);
        		houseCardPo.setCardNo(orderId + (i+1));
        		houseCardPo.setOrderNo(orderId);
        		houseCardPo.setShopId(shopId);
        		houseCardPo.setUseStatus(0);//未使用
        		houseCardPo.setEndDate("2017-03-31");//有效期 暂定 
        		houseCardPo.setCardPrice(price);
        		mainService.addHouseCard(houseCardPo);
			}
        	logger.info("addHouseCard添加房券信息成功！");
		}
	}
	
	/** 处理微信订单相关查询 
	 * @throws MalformedURLException */
	private boolean queryWeiXinOrderStatus(String orderId, String uid, OrderPayDO orderPayDO, String trade_type) {
		boolean payFlag = false;
		try {
			/** 支付方式编码:wxpay:微信支付；alipay:支付宝支付 */
			String productCode = orderPayDO.getPayType();
			String out_trade_no = orderPayDO.getPayId();//商户订单号
			Map<String,String> extension = new HashMap<String , String>();
			extension.put("trade_type", trade_type);
			ChargeRequest chargeRequest = new ChargeRequest();
	        chargeRequest.setOrderId(out_trade_no);
	        chargeRequest.setProductCode(productCode);
	        chargeRequest.setMemberID(uid);
	        chargeRequest.setExtension(extension);
	        
			//2、请求核心支付系统
	        HessianProxyFactory factory = new HessianProxyFactory();  
	        ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, PropertiesUtils.getChargeCenterUrl());
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
					mainService.updateHouseOrderAnaPayStatus(uid, orderId, out_trade_no);
					payFlag = true;
					dataMap.put("resultCode", "3");
					dataMap.put("resultMsg", "订单支付成功！");
				} else {
					String msg = "支付失败!";
					String code = "5";
					if("REFUND".equals(tradeState)) {
						msg = "转入退款";
					} else if("NOTPAY".equals(tradeState)) {
						code = "6";
						msg = "未支付";
					} else if("CLOSED".equals(tradeState)) {
						msg = "已关闭";
					} else if("REVOKED".equals(tradeState)) {
						msg = "已撤销";
					} else if("USERPAYING".equals(tradeState)) {
						msg = "用户支付中";
					} else if("PAYERROR".equals(tradeState)) {
						msg = "支付失败";
					}
					dataMap.put("resultCode", code);
					dataMap.put("resultMsg", msg);
				}
			} else {
				dataMap.put("resultCode", "2");
				dataMap.put("resultMsg", "调用微信订单查询接口失败！");
			}
		} catch (Exception e) {
			logger.error("查询订单状态接口错误：", e);
			outputMap.put("exception", e.getMessage());
			genErrOutputMap("interface error");
		}
		return payFlag;
	}
	
	/** 处理支付宝相关订单状态  */
	public boolean queryAliPayOrderStatus(String orderId, String uid, OrderPayDO orderPayDO) {
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
				mainService.updateHouseOrderAnaPayStatus(uid, orderId, out_trade_no);
				payFlag = true;
				dataMap.put("resultCode", "3");
				dataMap.put("resultMsg", "订单支付成功！");
			} else {
				String msg = "支付失败!";
				String resultCode = "5";
				if("20000".equals(code)) {
				/** 服务不可用 */
				} else if("20001".equals(code)) {
					/** 授权权限不足 */
					logger.info(out_trade_no + "支付宝查询授权权限不足！");
				} else if("40001".equals(code)) {
					/** 缺少必选参数 */
					logger.info(out_trade_no + "支付宝查询授权权限不足！");
				} else if("40002".equals(code)) {
					/** 非法的参数 */
					logger.info(out_trade_no + "支付宝查询非法的参数！");
				} else if("40004".equals(code)) {
					/** 业务处理失败:对应业务错误码，明细错误码和解决方案请参见具体的API接口文档 */
					logger.info(out_trade_no + "支付宝查询业务处理失败！");
				} else if("40006".equals(code)) {
					/** 权限不足 */
					logger.info(out_trade_no + "支付宝查询权限不足！");
				}
				dataMap.put("resultCode", resultCode);
				dataMap.put("resultMsg", msg);
			}
		} catch (Exception e) {
			logger.error("查询订单状态接口错误：", e);
			outputMap.put("exception", e.getMessage());
			genErrOutputMap("interface error");
		}
		return payFlag;
	}
	
	/** 根据uid和orderId发送短信 */
	private void sendPaySuccessSms(String uid, String orderId) {
		try {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("uid", uid);
			param.put("orderId", orderId);
			sendOrderPayCodeHandler.handle(param);
			logger.info("uid=" + uid + ",orderId=" + orderId + "支付成功后发送短信成功！");
		} catch (Exception e) {
			logger.error("uid=" + uid + ",orderId=" + orderId + "支付查询成功后发送短信失败！", e);
		}
	}

}
