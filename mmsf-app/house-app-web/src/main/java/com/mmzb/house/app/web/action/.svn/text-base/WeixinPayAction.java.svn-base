package com.mmzb.house.app.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.UserInfoVo;
import com.mmzb.house.app.vo.pay.DataResponse;
import com.mmzb.house.app.web.service.pay.AliPayService;
import com.mmzb.house.app.web.util.AppParaterUtil;

/** 微信支付相关接口 */
@Controller
public class WeixinPayAction {
	private static Logger logger = LoggerFactory.getLogger(WeixinPayAction.class);
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	@Autowired
	private MemberTokenService memberTokenService;
	
	@Resource(name="aliPayService")
	private AliPayService aliPayService;

	/** 微信支付--获取预支付交易会话标识 */
	@RequestMapping(value = "/app/my/pay/unifiedorder", method = { RequestMethod.POST})
	@ResponseBody
	public DataResponse unifiedorder(HttpServletRequest request, ModelMap model) {
		try{
			String accessToken = request.getParameter("accessToken");
			String orderNo = request.getParameter("orderId");//业务订单号
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    
			//1、基本请求参数校验
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			DataResponse orderStatusResponse = queryOrderPayStatus(uid, orderNo);
			String resultCode = "";
			if(Constants.APP_SUCCESSED.equals(orderStatusResponse.getCode())) {
				/** 1、已支付成功；2、调用微信订单查询接口失败！；3、查询微信确认支付成功！；
				 *  4、未查询到对应的房源订单信息！；5、支付失败!；6、未支付；；7、未查询到对应支付订单号，请联系管理员！ */
				resultCode = orderStatusResponse.getResult().get("resultCode");
			} else {
				return orderStatusResponse;
			}
			/** 已支付 */
			if("1".equals(resultCode) || "3".equals(resultCode)) {
				DataResponse response = new DataResponse();
				response.setCode(Constants.APP_REPEATED_PAY);
				response.setMessage("该订单已支付成功，不能重复支付！");
				return response;
			}
			/** 根据订单id查询订单价格 */
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("orderId", orderNo);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrderInfo");
			String code = result.getString("code");
			if ("0".equals(code)) {
				JSONObject object = result.getJSONObject("data");
				Object payAmt = object.get("payAmt");
				if(payAmt != null) {
					String  money = String.valueOf(payAmt);//金额
					logger.info("============money=="+money+"===uid=="+uid);
					
					//2、请求核心支付系统
			        HessianProxyFactory factory = new HessianProxyFactory();  
			        ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, appProperties.getChargeCenterUrl());
			        
			        ChargeRequest chargeRequest = new ChargeRequest();
			        chargeRequest.setProductCode(productCode);
			        chargeRequest.setMemberID(uid);
			        chargeRequest.setMoney(money);
			        chargeRequest.setOrderId(orderNo);
			        /** 订单支付来源：ios：ios客户端；android：android客户端；h5：h5客户端； */
					String source = AppParaterUtil.getProductType(request);
					
					//扩展请求参数
			        Map<String,String> extension = new HashMap<String , String>();
			        extension.put("source", source);
			        if("alipay".equals(productCode)) {//支付宝支付
			        	chargeRequest.setExtension(extension);
			        	ChargeResponse chargeResponse = facade.alipayOrderHandle(chargeRequest);
			        	if(chargeResponse.isSuccess()) {
			        		String out_trade_no = chargeResponse.getData().get("out_trade_no");
			        		return aliPayService.handleByAliPayOrderInfo(out_trade_no, money);
			        	} else {
			        		DataResponse response = new DataResponse();
			    			response.setCode(Constants.APP_FAILED);
			    			response.setMessage("订单处理失败失败！");
			    			return response;
			        	}
			        } else {//微信支付
				        extension.put("spbill_create_ip",request.getRemoteAddr());
				        extension.put("trade_type", "APP");
				        
				        chargeRequest.setExtension(extension);
				        
				        ChargeResponse chargeResponse = facade.charge(chargeRequest);
				        
				        logger.info("=========充值结果为========="+chargeResponse);
				        
			            //对象转换
				        DataResponse returnData = this.chargeResponseConvert(chargeResponse);
				        
				        return returnData;
			        }
				} else {
					DataResponse response = new DataResponse();
					response.setCode(Constants.APP_FAILED);
					response.setMessage("未查到支付金额信息");
					return response;
				}
			} else {
				DataResponse response = new DataResponse();
				response.setCode(Constants.APP_FAILED);
				response.setMessage("查询订单信息失败！");
				return response;
			}
		}catch(Exception e){
			logger.error("=======【充值】出现异常===========",e);
			return this.chargeResponseConvert(null);
		}
	}
	
	/** 微信订单状态查询 */
	@RequestMapping(value = "/app/my/pay/queryorderstatus", method = { RequestMethod.POST})
	@ResponseBody
	public DataResponse queryOrderStatus(HttpServletRequest request) {
		try {
			String accessToken = request.getParameter("accessToken");
			UserInfoVo loginUser = memberTokenService.getLoginInfoFromRedis(accessToken);
		    
			//1、基本请求参数校验
		    String  uid            =  loginUser.getMemberId();//会员编号
			String  orderId        =  request.getParameter("orderId"); //业务订单号
			DataResponse dataResponse = queryOrderPayStatus(uid, orderId);
			logger.info("=========订单查询结果为=========" + dataResponse);
			
			return dataResponse;
		} catch(Exception e) {
			logger.error("=======【订单查询】出现异常===========", e);
			return this.queryOrderResponseConvert(null, null, null, null);
		}
	}
	
	private Map consove2Map(String orderId, String uid, int houseId,int shopId, String phone,int totalRoomNum,int cardPrice) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("uid", uid);
		map.put("houseId", houseId);
		map.put("shopId", shopId);
		map.put("phone", phone);
		map.put("totalRoomNum", totalRoomNum);
		map.put("cardPrice", cardPrice);
		return map;
	}

	/** 查询支付结果相关接口封装 */
	private DataResponse queryOrderPayStatus(String uid, String orderId) 
			throws Exception {
		Map<String, Object> postData = new HashMap<String, Object>();
	    postData.put("uid", uid);
	    postData.put("orderId", orderId);
	        
		logger.info("============orderId==" + orderId + "====uid====" + uid);
		
		//2、请求妈妈送房后台查询
		JSONObject result =	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"queryOrderStatus");
		logger.info("=========订单查询结果为result=" + result);
		String code = result.getString("code");
		String msg = result.get("msg").toString();
		String resultCode = "";
		String resultMsg = "";
		if("0".equals(code)){
			JSONObject json = new JSONObject(); 
			if(result.get("data") != null) {
				String data = result.get("data").toString();
    			JSONObject dataObj = json.parseObject(data);
    			if(dataObj.get("resultCode") != null) {
    				resultCode = dataObj.get("resultCode").toString();
    			}
    			if(dataObj.get("resultMsg") != null) {
    				resultMsg = dataObj.get("resultMsg").toString();
    			}
    			if(dataObj.get("code") != null) {
    				resultCode = dataObj.get("code").toString();
    			}
    			if(dataObj.get("msg") != null) {
    				resultMsg = dataObj.get("msg").toString();
    			}
			}
		}
		DataResponse dataResponse = queryOrderResponseConvert(code, msg, resultCode, resultMsg);
		return dataResponse;
	}
	
	/** 充值返回对象组装 */
	private DataResponse chargeResponseConvert(ChargeResponse chargeResponse){
		DataResponse response = new DataResponse();
		if(null == chargeResponse){
			response.setCode(Constants.APP_FAILED);
			response.setMessage("调用充值失败");
			return response;
		}
		Map<String,String> data = chargeResponse.getData();
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("appId", data.get("appId"));
		resultMap.put("partnerId", data.get("partnerid"));
		resultMap.put("prepayId", data.get("prepayId"));
		resultMap.put("packageValue", data.get("package"));
		resultMap.put("nonceStr", data.get("nonceStr"));
		resultMap.put("timeStamp", data.get("timeStamp"));
		resultMap.put("paySign", data.get("paySign"));
		response.setCode(Constants.APP_SUCCESSED);
		response.setMessage(chargeResponse.getMsg());
		response.setResult(resultMap);
		return response;
	}
	
	/** 查询订单结果返回对象组装 */
	private DataResponse queryOrderResponseConvert(String code, String msg, String resultCode, String resultMsg) {
		DataResponse response = new DataResponse();
		if(!"0".equals(code)){
			response.setCode(Constants.APP_FAILED);
			response.setMessage("调用订单查询失败");
			return response;
		}
		
		response.setCode(Constants.APP_SUCCESSED);
		response.setMessage(msg);
		Map<String,String> data = new HashMap<String,String>();
		data.put("resultCode", resultCode);
		data.put("resultMsg", resultMsg);
		response.setResult(data);
		return response;
	}
}
