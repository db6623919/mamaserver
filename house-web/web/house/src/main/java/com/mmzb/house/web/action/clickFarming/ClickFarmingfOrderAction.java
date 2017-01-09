package com.mmzb.house.web.action.clickFarming;

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

import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.OrderParam;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ResultSet;
import com.mmzb.house.web.model.WeixinPayBean;
import com.netfinworks.ma.service.response.PersonalMemberInfo;

/**
 * 订单
 * @author whl
 *
 */
@Controller
@RequestMapping("/my")
public class ClickFarmingfOrderAction extends BaseAction{
	
	//参数配置
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	//日志打印
	private static final Logger logger = LoggerFactory.getLogger(ClickFarmingfOrderAction.class);
	
	/** 跳转到刷单首页 */
	@RequestMapping(value = "/clickfarm/index.htm", method = { RequestMethod.GET })
	public ModelAndView getCode(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		
	    //支付成功 刷单发送短信
		String url = request.getQueryString();
		if (url.contains("result=success")) {
			UserInfo loginUser = getLoginUserInfo(request);
			String orderId = request.getParameter("orderId");
			sengOrder(loginUser.getName(),orderId);
		}
		 
		return new ModelAndView(Contants.URL_PREFIX + "/clickfarming/clickFarmingIndex"); 
	}
	
	//刷单发短信
	public void sengOrder(String userName,String orderId) {
		try {
			Map<String, Object> msgMap = new HashMap<String, Object>();
	        msgMap.put("userName", userName);
	        msgMap.put("orderId", orderId);
	        JSONObject msgObject = HttpClientPostMethod.httpReqUrl(msgMap, appProperties.getRequestRoot(), "sendOrderMsg");
	        int code = Integer.parseInt(msgObject.getString("code"));
	        if (code != ReturnCode.OK) {
	        	logger.error("===[" + orderId + "]刷单发动短信接口异常.");
	        }
		} catch (Exception e) {
			logger.error("===[" + orderId + "]刷单发动短信接口异常.",e);
		}
	}
	/**
	 * 订单提交
	 * @param request
	 * @param response
	 * @param orderParam 提交参数
	 * @return
	 */
	@RequestMapping(value = "/clickFarmingCreateOrder.htm", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> clickFarmingCreateOrder(HttpServletRequest request, HttpServletResponse response)  {
		
		logger.info("run start to /clickFarmingCreateOrder.");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo loginUser = getLoginUserInfo(request);
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", loginUser.getUid());
			postData.put("houseId", Integer.parseInt(request.getParameter("houseId")));
			postData.put("pay_type", ""); //支付方式
			postData.put("payPrice",Integer.parseInt(request.getParameter("payPrice"))); //支付金额
			postData.put("shopId",Integer.parseInt(request.getParameter("shopId"))); //客栈ID
			postData.put("act",request.getParameter("act")); //订单修改状态
			postData.put("orderId",request.getParameter("orderId")); //订单ID
			postData.put("userName",loginUser.getName()); //订单ID
			//获取登陆人手机号
			PersonalMemberInfo personalMemberInfo = getMemberInfo(request.getSession());
			postData.put("memberId", personalMemberInfo.getMemberId());
			postData.put("userPhone",getMemberPhone(personalMemberInfo)); //用户手机号
			
			
			//提交订单
			JSONObject resultObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "clickFarmingOrder");
			int code = Integer.parseInt(resultObject.getString("code"));
			result.put("code", code);
			if (code == ReturnCode.OK) {
				JSONObject dataJson = resultObject.getJSONObject("data");
				String orderId = dataJson.getString("orderId");
				String isClickFarming = dataJson.getString("isClickFarming");
				int payAmt = dataJson.getInteger("payAmt");
				result.put("orderId", orderId);
				result.put("isClickFarming", isClickFarming);
				result.put("payAmt", payAmt);
				WeixinPayBean weixinPayBean = new WeixinPayBean();
				weixinPayBean.setOrderNo(orderId);
				weixinPayBean.setPayAmt("" + payAmt);
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
			} 
			logger.info("run end to /clickFarmingCreateOrder.",result.toString());
			return result;
		} catch (Exception e) {
			logger.error("订单(刷单)提交失败.",e);
			result.put("code", ReturnCode.INTERFACE_ERROR);
			result.put("message", "订单(刷单)提交失败.");
			return result;
		} 
	}
	
	/**
	 * 跳转到支付页面
	 * @param orderParam
	 * @return
	 */
	@RequestMapping("/my/clickFarming/toPay")
	@ResponseBody
	public ResultSet toPay(OrderParam orderParam)
	{
		logger.info("start to run toPay(), param is {}.", orderParam.toString());
		
		ResultSet resultSet = new ResultSet();
		resultSet.setCode(ReturnCode.OK);
		resultSet.setMessage("SUCCESS");
		resultSet.setResult(orderParam);
		
		logger.info("return the response:", resultSet.toString());
		
		return resultSet;
		
	}
	
	/**
	 * 修改订单价格接口
	 * @param orderParam
	 * @return
	 */
	@RequestMapping("/updateorderinfo.htm")
	@ResponseBody
	public ResultSet updateorderinfo(OrderParam orderParam)
	{
		ResultSet resultSet = new ResultSet();
		try {
			logger.info("start to run updateorderinfo(), param is {}.", orderParam.toString());
			Map<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("orderId", orderParam.getOrderId());
			paramData.put("payAmt", orderParam.getPayPrice());
			//每日价格查询
			JSONObject result = HttpClientPostMethod.httpReqUrl(paramData, appProperties.getRequestRoot(), "updateOrderPrice");
			String code = result.getString("code");
			String msg = result.getString("msg");
			resultSet.setCode(Integer.valueOf(code));
			resultSet.setMessage(msg);
			
			logger.info("return the response:", resultSet.toString());
		} catch (Exception e) {
			logger.error("订单(刷单)提交失败.", e);
			resultSet.setCode(ReturnCode.INTERFACE_ERROR);
			resultSet.setMessage("调用后台接口异常！");
		} 
		return resultSet;
	}
	
	/**
	 * n99下单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/createN99Order.htm", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> createN99Order(HttpServletRequest request, HttpServletResponse response)  {
		
		logger.info("run start to /createN99Order.");
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo loginUser = getLoginUserInfo(request);
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", loginUser.getUid());
			postData.put("houseId", Integer.parseInt(request.getParameter("houseId")));
			postData.put("payPrice",Integer.parseInt(request.getParameter("payPrice"))); //支付金额
			postData.put("userName",loginUser.getName()); //订单ID
			postData.put("totalRoomNum",Integer.parseInt(request.getParameter("totalRoomNum"))); //房券数
			//获取登陆人手机号
			PersonalMemberInfo personalMemberInfo = getMemberInfo(request.getSession());
			postData.put("userPhone",getMemberPhone(personalMemberInfo)); //用户手机号
			
			//提交订单
			JSONObject resultObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "n99Order");
			int code = Integer.parseInt(resultObject.getString("code"));
			result.put("code", code);
			if (code == ReturnCode.OK) {
				JSONObject dataJson = resultObject.getJSONObject("data");
				String orderId = dataJson.getString("orderId");
				int payAmt = dataJson.getInteger("payAmt");
				WeixinPayBean weixinPayBean = new WeixinPayBean();
				weixinPayBean.setOrderNo(orderId);
				weixinPayBean.setPayAmt("" + payAmt);
				setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, weixinPayBean);
				result.put("orderId", orderId);
				result.put("payAmt", payAmt);
			} 
			logger.info("run end to /createN99Order.",result.toString());
			return result;
		} catch (Exception e) {
			logger.error("订单(n99)提交失败.",e);
			result.put("code", ReturnCode.INTERFACE_ERROR);
			result.put("message", "订单(n99)提交失败.");
			return result;
		} 
	}
}
