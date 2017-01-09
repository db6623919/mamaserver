package com.mmzb.house.web.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.constant.DataResponse;
import com.mmzb.house.web.constant.H5Constants;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.util.JsonUtil;
import com.mmzb.house.web.model.WeixinPayBean;
import com.mmzb.house.web.util.pay.PayConstants;

/**
 * 微信支付action 
 * @author loomz
 *
 */
@Controller
public class WeixinPayAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(WeixinPayAction.class);

	@Resource(name="appProperties")
	private AppProperties appProperties;
	/**
	 * getCode
	 */
	@RequestMapping(value = "/my/weixin_pay/getCode.htm", method = { RequestMethod.GET })
	public ModelAndView getCode(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/weixin_pay/getCode"); 
	}
	
	/**
	 * 接受微信返回的code
	 */
	@RequestMapping(value = "/my/weixin_pay/orderInfo.htm", method = { RequestMethod.GET })
	public ModelAndView notifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		String payType = request.getParameter("payType");
		String weixinCode = request.getParameter("code");
		logger.info("weixin notify code: "  + weixinCode);
		
		if(null != weixinCode){//获取用户的微信code失败，重新获取
			String openid = getOpenid(weixinCode);
		    model.addAttribute("openid", openid);
    	} else {
    		// return new ModelAndView("redirect:/my/weixin_pay/getCode.htm");
    	}
	    
	    WeixinPayBean weixinPayBean = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, WeixinPayBean.class);
	    model.addAttribute("weixinPayBean", weixinPayBean);
	    
	    if (weixinPayBean == null) {
	    	logger.error("notifyCode weixinPayBean error: "  + weixinPayBean);
	    	return new ModelAndView("redirect:/my/getOrders.htm?page=1&type=0");
	    }
	    model.addAttribute("payType", payType);
	    return new ModelAndView(Contants.URL_PREFIX + "/weixin_pay/order_pay");
	}
	
	/** 妈妈送房前后台分离版本获取openId */
	@RequestMapping(value = "/my/weixin_pay/queryopenid.htm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView queryopenid(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		String weixinCode = request.getParameter("code");
		logger.info("weixin notify code: "  + weixinCode);
		
		if(null != weixinCode){//获取用户的微信code失败，重新获取
			String openid = getOpenid(weixinCode);
		    model.addAttribute("openid", openid);
    	}
	    
		//1、基本请求参数校验
		String  money          =  request.getParameter("money"); //金额
		String orderId = request.getParameter("orderId"); //订单号
		String shopId = request.getParameter("shopId");
		WeixinPayBean weixinPayBean = new WeixinPayBean();
		weixinPayBean.setOrderNo(orderId);
		weixinPayBean.setPayAmt(money);
		super.setJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
	    model.addAttribute("weixinPayBean", weixinPayBean);
	    model.addAttribute("isShuaDan", "1");
	    model.addAttribute("weixinCode", weixinCode);
	    model.addAttribute("shopId", shopId);
	    return new ModelAndView(Contants.URL_PREFIX + "/weixin_pay/order_pay");
	}
	
	/**
	 * 接受微信返回的code
	 */
	@RequestMapping(value = "/my/weixin_pay/unifiedorder.htm", method = { RequestMethod.POST })
	@ResponseBody
	public DataResponse unifiedorder(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		try{
		    UserInfo loginUser = getLoginUserInfo(request);
		    WeixinPayBean weixinPayBean = super.getJsonAttribute(request.getSession(),
	                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, WeixinPayBean.class);
		    
			//1、基本请求参数校验
			String  money          =  request.getParameter("money"); //金额
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			String  uid            =  loginUser.getUid();//会员编号
			String  userName       =  loginUser.getName();//会员姓名
			
			//2、请求核心支付系统
	        HessianProxyFactory factory = new HessianProxyFactory();  
	        ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, appProperties.getChargeCenterUrl());
	        
	        ChargeRequest chargeRequest = new ChargeRequest();
	        chargeRequest.setProductCode(productCode);
	        chargeRequest.setMemberID(uid);
	        chargeRequest.setMoney(money);
	        chargeRequest.setOrderId(weixinPayBean.getOrderNo());
	        
	        /** 微信支付:APP、第一版本的支付方式;JSAPI、h5支付方式； */
			String trade_type = request.getParameter("trade_type");	// 微信统一下单贯口，trade_type
			/** 订单支付来源：ios：ios客户端；android：android客户端；h5：h5客户端； */
			String source = "h5";
			if("APP".equals(trade_type)) {
				source = "android";//此种方式默认保存为android
			}
			//扩展请求参数
	        Map<String,String> extension = new HashMap<String , String>();
	        extension.put("source", source);
			if("alipay".equals(productCode)) {
				/** 支付宝支付 */
				chargeRequest.setExtension(extension);
				ChargeResponse chargeResponse = facade.alipayOrderHandle(chargeRequest);
	        	if(chargeResponse.isSuccess()) {
	        		String out_trade_no = chargeResponse.getData().get("out_trade_no");
	        		try {
	        			sendAliPayRequest(request, response, out_trade_no, money);
	        			return null;
	        		} catch(Exception e) {
	        			DataResponse dataResponse = new DataResponse();
		        		dataResponse.setCode(H5Constants.H5_FAILED);
		        		dataResponse.setMessage("支付请求发送失败！");
		    			return dataResponse;
	        		}
	        	} else {
	        		DataResponse dataResponse = new DataResponse();
	        		dataResponse.setCode(H5Constants.H5_FAILED);
	        		dataResponse.setMessage("订单处理失败失败！");
	    			return dataResponse;
	        	}
			} else {
				String  openid         =  request.getParameter("jsopenid");//微信ID
				logger.info("============money=="+money+"====openid===="+openid+"===uid=="+uid);
		        extension.put("spbill_create_ip",request.getRemoteAddr());
		        extension.put("openid", openid);
		        extension.put("trade_type", trade_type);
		        chargeRequest.setExtension(extension);
		        ChargeResponse chargeResponse = facade.charge(chargeRequest);
		        logger.info("=========充值结果为========="+chargeResponse);
		        
	            //对象转换
		        DataResponse returnData = this.chargeResponseConvert(chargeResponse);
		        
		        
		        return returnData;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("=======【充值】出现异常===========",e);
			return this.chargeResponseConvert(null);
		}
	}
	
	/** 提交支付宝请求 
	 * @throws Exception */
	private void sendAliPayRequest(HttpServletRequest request, HttpServletResponse httpResponse, 
			String out_trade_no, String money) throws Exception {
		money = "0.01";
		AlipayClient alipayClient = PayConstants.getAlipayClient();//获得初始化的AlipayClient
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
	    alipayRequest.setReturnUrl(appProperties.getMmsfWapWebUrl() + PayConstants.ALIPAY_RETURL_URL);//返回回调地址
	    alipayRequest.setNotifyUrl(appProperties.getMmsfWapWebUrl() + PayConstants.ALIPAY_ASYNC_NOTIFY_URL);//在公共参数中设置回跳和通知地址
	    Map<String, String> bizContentMap = new HashMap<String, String>();
		bizContentMap.put("body", "妈妈送房订单支付信息");
		bizContentMap.put("subject", "妈妈送房订单支付信息");
		bizContentMap.put("out_trade_no", out_trade_no);//商户网站唯一订单号
		bizContentMap.put("timeout_express", "24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。
		bizContentMap.put("total_amount", money);//金额
		bizContentMap.put("seller_id", PayConstants.ALIPAY_SELLER_ID);
		bizContentMap.put("product_code", "QUICK_WAP_PAY");
		String bizContent = JsonUtil.objectToString(bizContentMap);
	    alipayRequest.setBizContent(bizContent);//填充业务参数
	    String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
	    httpResponse.setContentType("text/html;charset=utf-8");
	    httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
	    httpResponse.getWriter().flush();
	}
	
	
	
	/** 微信订单状态查询 */
	@RequestMapping(value = "/my/weixin_pay/queryorderstatus.htm", method = { RequestMethod.POST })
	public @ResponseBody DataResponse queryOrderStatus(HttpServletRequest request) {
		try {
		    UserInfo loginUser = getLoginUserInfo(request);
		    
			//1、基本请求参数校验
		    String  uid            =  loginUser.getUid();//会员编号
			String  orderId          =  request.getParameter("orderId"); //业务订单号
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			String  trade_type	   =  request.getParameter("trade_type");	// 微信统一下单贯口，trade_type
			
			Map<String, Object> postData = new HashMap<String, Object>();
		    postData.put("uid", uid);
		    postData.put("orderId", orderId);
		    postData.put("productCode", productCode);
		    postData.put("trade_type", trade_type);
		        
			logger.info("============orderId==" + orderId + "====uid====" + uid);
			
			//2、请求妈妈送房后台查询
			JSONObject result =	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"queryOrderStatus");
			logger.info("=========订单查询结果为result=" + result);
    		String code = result.getString("code");
    		String msg = result.get("msg").toString();
    		String resultCode = "";
    		String resultMsg = "";
    		JSONObject dataObj = null;
    		if("0".equals(code)){
    			JSONObject json = new JSONObject(); 
    			if(result.get("data") != null) {
    				String data = result.get("data").toString();
        			dataObj = json.parseObject(data);
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
			
			if(dataObj != null) {
				if(dataResponse.getData() == null) {
					dataResponse.setData(new HashMap<String, String>());
				}
				if(dataObj.get("payAmt") != null) {
					dataResponse.getData().put("payAmt", (String)dataObj.get("payAmt"));
    			}
				if(dataObj.get("shopId") != null) {
					dataResponse.getData().put("shopId", (String)dataObj.get("shopId"));
    			}
				if(dataObj.get("houseId") != null) {
					dataResponse.getData().put("houseId", (String)dataObj.get("houseId"));
    			}
				if(dataObj.get("orderType") != null) {
					dataResponse.getData().put("orderType", (String)dataObj.get("orderType"));
    			}
				if(dataObj.get("payType") != null) {
					dataResponse.getData().put("payType", (String)dataObj.get("payType"));
    			}
			}
			logger.info("=========订单查询结果为=========" + dataResponse);
			return dataResponse;
		} catch(Exception e) {
			logger.error("=======【订单查询】出现异常===========", e);
			return this.queryOrderResponseConvert(null, null, null, null);
		}
	}
	
	/** 跳转到支付订单确认页面 */
	@RequestMapping(value = "/my/weixin_pay/order_confirm.htm", method = { RequestMethod.GET })
	public ModelAndView orderConfirm(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String  productCode    =  request.getParameter("productCode");//支付方式编码
		String  trade_type	   =  request.getParameter("trade_type");	// 微信统一下单贯口，trade_type
//		orderId="10207852";
//		productCode = "wxpay";
//		trade_type = "APP";
		model.addAttribute("orderId", orderId);
		model.addAttribute("productCode", productCode);
		model.addAttribute("tradeType", trade_type);
		return new ModelAndView(Contants.URL_PREFIX + "/weixin_pay/order_confirm");
	}
	
	/** 支付宝完成支付后返回支付结果的跳转页面 */
	@RequestMapping(value = "/pay/alipayReturnUrl.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView alipayReturnUrl(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		logger.info("支付宝支付返回跳转地址参数：" + request.getQueryString());
		String app_id = request.getParameter("app_id");//支付宝分配给开发者的应用ID
		String method = request.getParameter("method");//接口名称:alipay.trade.wap.pay.return
		String sign_type = request.getParameter("sign_type");//签名算法类型，目前支持RSA
		String sign = request.getParameter("sign");//支付宝对本次支付结果的签名，开发者必须使用支付宝公钥验证签名
		String charset = request.getParameter("charset");//编码格式，如utf-8,gbk,gb2312等
		String timestamp = request.getParameter("timestamp");//前台回跳的时间，格式"yyyy-MM-dd HH:mm:ss"
		String version = request.getParameter("version");//调用的接口版本，固定为：1.0
		String out_trade_no = request.getParameter("out_trade_no");//商户网站唯一订单号
		String trade_no = request.getParameter("trade_no");//该交易在支付宝系统中的交易流水号。最长64位。
		/** 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。 */
		String total_amount = request.getParameter("total_amount");
		String seller_id = request.getParameter("seller_id");//收款支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字
		WeixinPayBean weixinPayBean = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, WeixinPayBean.class);
		String orderNo = weixinPayBean.getOrderNo();
		model.addAttribute("orderId", orderNo);
		model.addAttribute("alipayResult", "success");//支付成功
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex");
	}
	
	@RequestMapping(value = "/my/weixin_pay/pay_success.htm", method = { RequestMethod.GET })
	public ModelAndView success(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		 WeixinPayBean weixinPayBean = super.getJsonAttribute(request.getSession(),
	                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, WeixinPayBean.class);
		    
	    if (weixinPayBean == null) {	// 如果会话不存在返回订单页，不继续操作，避免重复发验证码
	    	logger.error("success weixinPayBean error: "  + weixinPayBean);
	    	return new ModelAndView("redirect:/my/getOrders.htm?page=1&type=0");
	    }
	    
		// 删除会话中的订单信息
		request.getSession().removeAttribute(Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE);
		//支付系统发送短信
		/*try{
		   //发送充值成功短信给用户
           SmsUtil.sendSms(loginUser.getPhone(), SmsUtil.genRechargeSms(loginUser.getPhone(), rechargeBean.getMoney(), rechargeBean.getRewardMoney()));
		}catch(Exception e){
			logger.error("客户发送短信异常",e);
		}*/
		String verifyCode="";
		Map<String, Object> postData = new HashMap<String, Object>();
		UserInfo userinfo = getLoginUserInfo(request);
		postData.put("uid", userinfo.getUid());
		postData.put("orderId", weixinPayBean.getOrderNo());
		try{
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"sendOrderPayCode");
			int code = result.getIntValue("code");
			if(code==0){
				verifyCode = result.getJSONObject("data").getString("verifyCode");
			}
		} catch (ClientProtocolException e) {
			logger.error("客户发送短信异常", e);
		} catch (IOException e) {
			logger.error("客户发送短信异常", e);
		}
		
        model.addAttribute("houseCode", verifyCode);
        
		//判断是否登陆
		model.addAttribute("isLogin", 1);
		
		//订单id
		String orderNo = weixinPayBean.getOrderNo();
		model.addAttribute("orderId", orderNo);
		
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfIndex");
	}
	
	private String getOpenid(String weixinCode) {
		try {
	        DefaultHttpClient httpClient = new DefaultHttpClient();  
	  
	        String getOpenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9edd5171a7a05a0a&secret=ef79ef4f9fa8db00c996cea7a4bfdca3&code="+weixinCode+"&grant_type=authorization_code";
	        logger.info("getOpenidUrl: " + getOpenUrl);
			HttpResponse result = httpClient.execute(new HttpGet(getOpenUrl));
			// 请求结束，返回结果
			String resData = EntityUtils.toString(result.getEntity());
			logger.info("获取微信openid接收后端数据包内容: " + resData);
			
			JSONObject resJson = JSONObject.parseObject(resData);
			String openid = resJson.getString("openid");
			return openid;
		} catch (Exception ex) {
			logger.info("获取openid异常!", ex.getMessage());
			logger.error("获取openid异常!", ex.getMessage());
		}
		
		return "";
	}
	
	/**
	 * chenmeiyang
	 * 对象转换
	 */
	private DataResponse chargeResponseConvert(ChargeResponse chargeResponse){
		DataResponse response = new DataResponse();
		String webchatNotInstallUrl = appProperties.getWebchatNotInstallUrl();
		response.getData().put("webchatNotInstallUrl", webchatNotInstallUrl);//不支持微信时跳转到订单url
		if(null == chargeResponse){
			response.setCode("-1");
			response.setSuccess(false);
			response.setMessage("调用充值失败");
			return response;
		}
		response.setCode("0");
		response.setSuccess(chargeResponse.isSuccess());
		response.setMessage(chargeResponse.getMsg());
		response.setData(chargeResponse.getData());
		return response;
	}
	
	/** 查询订单结果返回对象组装 */
	private DataResponse queryOrderResponseConvert(String code, String msg, String resultCode, String resultMsg) {
		DataResponse response = new DataResponse();
		if(!"0".equals(code)){
			response.setSuccess(false);
			response.setMessage("调用订单查询失败");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage(msg);
		Map<String,String> data = new HashMap<String,String>();
		data.put("resultCode", resultCode);
		data.put("resultMsg", resultMsg);
		response.setData(data);
		return response;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(URLEncoder.encode("http://m.mmsfang.com/my/weixin_pay/orderInfo.htm", "UTF-8"));
	}
}
