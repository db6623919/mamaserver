package com.mmzb.house.web.action.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;
import com.mmzb.house.web.constant.DataResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientGetMethod;
import com.mmzb.house.web.service.WeChatMessageDispatcher;

/**
 * @author chenmeiyang
 * 充值测试使用
 */
@Controller
@RequestMapping("/test")
public class TestAction {
	
	private static Logger logger = LoggerFactory.getLogger(TestAction.class);
	
	@Resource(name="appProperties")
    private AppProperties appProperties;
	
	@Resource(name="weChatMessageDispatcher")
	private WeChatMessageDispatcher weChatMessageDispatcher;
	
	/**
	 * chenmeiyang
	 * 用户点击  ”微信支付“ 按钮 ，执行充值动作
	 */
	@RequestMapping(value = "/charge.htm", method = { RequestMethod.POST})
	public @ResponseBody DataResponse  charge(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
	         
		
		try{
			//1、基本请求参数校验
			String  money          =  request.getParameter("money"); //金额
			String  productCode    =  request.getParameter("productCode");//支付方式编码
			String  openid         =  request.getParameter("openid");//微信ID
			String  uid            =  "a97a2408b9f23433f47bfef696d19d25";//陈美洋的会员编号
			
			logger.info("============money=="+money+"====openid===="+openid);
			
			
			//2、请求核心支付系统
	        HessianProxyFactory factory = new HessianProxyFactory();  
	        ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class,appProperties.getChargeCenterUrl());
	        
	        ChargeRequest chargeRequest = new ChargeRequest();
	        chargeRequest.setProductCode(productCode);
	        chargeRequest.setMemberID(uid);
	        chargeRequest.setMoney(money);
	        
	        //扩展请求参数
	        Map<String,String> extension = new HashMap<String , String>();
	        extension.put("spbill_create_ip",request.getRemoteAddr());
	        extension.put("openid", openid);
	        
	        chargeRequest.setExtension(extension);
	        
	        ChargeResponse chargeResponse = facade.charge(chargeRequest);
	        
	        logger.info("=========充值结果为========="+chargeResponse);
	        
            //对象转换
	        DataResponse returnData = this.chargeResponseConvert(chargeResponse);
	        
	        return returnData;
		}catch(Exception e){
			logger.error("=======【充值】出现异常===========",e);
			return this.chargeResponseConvert(null);
		}
	}
	
	
	/**
	 * chenmeiyang
	 * 对象转换
	 */
	private DataResponse chargeResponseConvert(ChargeResponse chargeResponse){
		DataResponse response = new DataResponse();
		if(null == chargeResponse){
			response.setSuccess(false);
			response.setMessage("调用充值失败");
			return response;
		}
		
		response.setSuccess(chargeResponse.isSuccess());
		response.setMessage(chargeResponse.getMsg());
		response.setData(chargeResponse.getData());
		return response;
	}
	
    /**
     * chenmeiyang
     * 跳转到用户充值页面
     */
    @RequestMapping(value = "/toChargeWeixin.htm", method = { RequestMethod.POST , RequestMethod.GET})
    public ModelAndView toChargeWeixin(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
         return new ModelAndView(Contants.URL_PREFIX+"/test/toChargeWeixin");
    }
    
	
    /**
     * chenmeiyang
     * 跳转到用户充值页面
     */
    @RequestMapping(value = "/toCharge.htm", method = { RequestMethod.POST , RequestMethod.GET})
    public ModelAndView toCharge(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        
    	//获取微信code
    	String weixinCode = request.getParameter("code");
    	
    	try {
    		JSONObject result = HttpClientGetMethod.httpReqUrl(null, weixinCode);
    		logger.info("=========获取微信code的相应内容为======微信code为====="+weixinCode +"=====结果集为=="+result);
    		
    		String  openid = result.getString("openid");
    		model.addAttribute("openid", openid);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("===========获取微信Code异常=============",e);
		}
    	
    	return new ModelAndView(Contants.URL_PREFIX+"/test/toCharge");
    }
    
    /**
     * chenmeiyang
     * 微信回调接口
     */
    @RequestMapping(value = "/weixinCallback.htm", method = RequestMethod.GET)
    public void weixinGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("signature===>" + signature);
		logger.info("timestamp===>" + timestamp);
		logger.info("nonce===>" + nonce);
		logger.info("echostr===>" + echostr);
		// 加密/校验流程：
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		String result = echostr;
		logger.info("result===>" + result);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();

    }
    
    /**
     * 微信回调接口
     */
    @RequestMapping(value = "/weixinCallback.htm", method = RequestMethod.POST)
    public void weixinPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	long startTime = System.currentTimeMillis();
		String result = "";
		try {
			response.getWriter().write(result);
			response.flushBuffer();
			weChatMessageDispatcher.handleMessage(request.getInputStream());
		} catch (IOException e) {
			logger.error("{}", e);
		} finally {
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("回复微信异常返回数据：{}", result);
		}
		logger.info("响应微信时长:{} 毫秒", System.currentTimeMillis() - startTime);
    }
    
    public static void main(String[] args){
    	
    	String s = URLEncoder.encode("http://test.mmsfang.com/house-web/test/toCharge.htm");
    	
    	System.out.println("==========="+s);
    }
	

}
