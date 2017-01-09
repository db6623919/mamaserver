package com.mmzb.house.app.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.order.ShopOwnerVo;
import com.mmzb.house.app.vo.order.ShuaDanOrderVo;
import com.mmzb.house.app.vo.order.ShuaDanOrdersVo;
import com.netfinworks.authorize.ws.dataobject.MemberToken;
import com.netfinworks.authorize.ws.response.impl.GetMemberTokenResponse;

/** 客栈老板刷单相关的入口 */
@Controller
public class ShopOwnerAction {
	private Logger logger = LoggerFactory.getLogger(ShopOwnerAction.class);
	
	//配置文件
	@Resource(name = "appProperties")
	private AppProperties appProperties;
		
	@Autowired
	private MemberTokenService memberTokenService;
	
	/** 检测是否是客栈老板 */
	@RequestMapping(value = "/app/my/chekShopOwerStatus", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<ShopOwnerVo> chekShopOwerStatus(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/chekShopOwerStatus");
		}
		try {
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			String mobile = queryMobileByAccessToken(accessToken);
//			String mobile = "15986488784";
			
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mobile", mobile);
			
			Response<ShopOwnerVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"chekShopOwnerStatus", ShopOwnerVo.class);
			if (result.getCode().equals("0")) {
				ShopOwnerVo shopOwnerVo = result.getData();
				return new ResponseOut<ShopOwnerVo>(Constants.APP_SUCCESSED,"检测是否是客栈老板成功.", shopOwnerVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/confirmOrder:订单确认失败.",e);
		}
		return new ResponseOut<ShopOwnerVo>(Constants.APP_FAILED,"检测是否是客栈老板失败.", null);
	}
	
	/** 查询刷单列表 */
	@RequestMapping(value = "/app/my/shuaDanOrderList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<ShuaDanOrdersVo> shuaDanOrderList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/shuaDanOrderList");
		}
		ShuaDanOrdersVo resultVo = new ShuaDanOrdersVo();
		try {
			String type = request.getParameter("type");//订单类型：0：未完成  1：已完成
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			String mobile = queryMobileByAccessToken(accessToken);
//			String mobile = "15986488784";
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("mobile", mobile);
			postData.put("type", type);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<ShuaDanOrdersVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"getShuaDanOrders", ShuaDanOrdersVo.class);
			if (result.getCode().equals("0")) {
				resultVo = result.getData();
				List<ShuaDanOrderVo> orders = resultVo.getOrders();
				if(orders != null && orders.size() > 0) {
					for(ShuaDanOrderVo vo : orders) {
						String memberId = vo.getUid();
		    			String orderManPhone = memberTokenService.queryMobileByMemberId(memberId);
//		    			if(orderManPhone != null && orderManPhone.length() > 7) {
//		    				orderManPhone = orderManPhone.substring(0, 3) + "****" + 
//		    								orderManPhone.substring(7, orderManPhone.length());
//		    			}
						vo.setOrderManPhone(orderManPhone);
						vo.setUid("");
					}
					logger.info("/app/my/shuaDanOrderList返回结果：" + JsonUtil.objectToString(resultVo));
				}
			}
		} catch (Exception e ) {
			logger.error("/app/my/shuaDanOrderList：刷单订单查询失败.",e);
			return new ResponseOut<ShuaDanOrdersVo>(Constants.APP_FAILED,"客栈订单查询失败.", resultVo);
		}
		return new ResponseOut<ShuaDanOrdersVo>(Constants.APP_SUCCESSED,"客栈订单查询成功", resultVo);
	}
		
	/** 根据accessToken查询手机号码信息 */
	public String queryMobileByAccessToken(String accessToken) {
		String mobile = "";
		try {
	    	GetMemberTokenResponse memberTokenResponse = memberTokenService.selectTokenRecord(accessToken);
	    	if(memberTokenResponse != null) {
	    		MemberToken memberToken = memberTokenResponse.getMemberToken();
	    		if(memberToken != null) {
	    			String memberId = memberToken.getMemberId();
	    			mobile = memberTokenService.queryMobileByMemberId(memberId);
	    		}
	    	}
    	} catch(Exception e) {
    		logger.error("系统异常！", e);
    	}
		return mobile;
	}
}
