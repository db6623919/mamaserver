package com.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.console.dto.HouseCardDto;
import com.console.framework.dao.Pagination;
import com.console.util.HttpClientPostMethod;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.mama.server.common.errorCode.ReturnCode;
import com.meidusa.fastjson.JSONObject;

/**
 * 房券管理
 * @author whl
 *
 */
@Controller
@RequestMapping("/ticket")
public class ShopTicketAction extends BaseController  {

	/**
	 * 房券列表查询
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toExchange")
	public ModelAndView toExchange(HttpSession session,HttpServletRequest request) throws  Exception {
		logger.info("start to run toExchange.");
		
		ModelAndView mv = new ModelAndView();
		List<HouseCardDto> houseCardList = new ArrayList<HouseCardDto>();
	    int current_page;// 当前页
	    if (null == request.getParameter("current_page") || ("").equals(request.getParameter("current_page"))) {
			current_page = 0;
		}else {
			current_page = Integer.parseInt(request.getParameter("current_page"));
		}
		int totalCount = 0;
		String buyPhone = "";
		String cardNo = "";
		try {
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("currentPage", current_page);
			postData.put("pageSize", MsgPropertiesUtils.getPageSize());
			
			//购买人电话
			if (null != request.getParameter("buyPhone") && !("").equals(request.getParameter("buyPhone"))) {
				buyPhone = request.getParameter("buyPhone");
			    postData.put("buyPhone", buyPhone);
			}
			//房券编号
			if (null != request.getParameter("cardNo") && !("").equals(request.getParameter("cardNo"))) {
				cardNo = request.getParameter("cardNo");
			    postData.put("cardNo", cardNo);
			}
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "getShopTicketList");
			int code = result.getInteger("code");
			if(code == ReturnCode.OK) {
				net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
			
				net.sf.json.JSONObject pageData = dataJson.getJSONObject("page");
				totalCount = pageData.getInt("total");
				JSONArray arrJson = pageData.getJSONArray("result");
				for(int i = 0 ; i < arrJson.size() ; i ++ ) {
					net.sf.json.JSONObject houseCardObject = arrJson.getJSONObject(i);
					HouseCardDto houseCard = new HouseCardDto();
					houseCard.setId(houseCardObject.getInt("id"));
					houseCard.setCardNo(houseCardObject.getString("cardNo"));
					houseCard.setOrderNo(houseCardObject.getString("orderNo"));
					houseCard.setBuyPhone(houseCardObject.getString("buyPhone"));
					houseCard.setBuyTime(houseCardObject.getString("buyTime"));
					if (houseCardObject.containsKey("cardPrice")) {
						houseCard.setCardPrice(houseCardObject.getString("cardPrice"));
					}
					if (houseCardObject.containsKey("exchangeTime")) {
						houseCard.setExchangeTime(houseCardObject.getString("exchangeTime"));
					}
					if (houseCardObject.containsKey("exchangeName")) {
						houseCard.setExchangeName(houseCardObject.getString("exchangeName"));
					}
					if (houseCardObject.containsKey("exchangePhoneNo")) {
						houseCard.setExchangePhoneNo(houseCardObject.getString("exchangePhoneNo"));
					}
					if (houseCardObject.containsKey("shopName")) {
						houseCard.setShopName(houseCardObject.getString("shopName"));
					}
					if (houseCardObject.containsKey("bossName")) {
						houseCard.setBossName(houseCardObject.getString("bossName"));
					}
					if (houseCardObject.containsKey("bossPhone")) {
						houseCard.setBossPhone(houseCardObject.getString("bossPhone"));
					}
					houseCard.setUseStatus(houseCardObject.getInt("useStatus"));
					houseCard.setHouseName(houseCardObject.getString("houseName"));
					houseCardList.add(houseCard);
				}
			}
		} catch (Exception e) {
			logger.error("toExchange：房券列表查询失败.",e);
		} 
		Pagination pager = new Pagination();
		pager.paging(current_page, MsgPropertiesUtils.getPageSize(), totalCount);
		mv.addObject("cardNo", cardNo);
		mv.addObject("buyPhone", buyPhone);
		mv.addObject("pager", pager);
		mv.addObject("list", houseCardList);
		mv.setViewName("/n99/list");
		return mv;
	}
	
	/**
	 * 房券兑换
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doExchange")
	@ResponseBody
	public RestResponse doExchange(HttpSession session,HttpServletRequest request) throws  Exception {
		
		RestResponse restP = new RestResponse();
		try {
			int  id  =  Integer.parseInt(request.getParameter("id")); //业务订单号
			String exchangeName = request.getParameter("exchangeName"); //兑换人
			String exchangePhoneNo = request.getParameter("exchangePhoneNo"); //联系方式
			String cardNo = request.getParameter("cardNo"); //房券编号
			Map<String, Object> postData = new HashMap<String, Object>();
		    postData.put("id", id);
		    postData.put("exchangeName", exchangeName);
		    postData.put("exchangePhoneNo", exchangePhoneNo);
		    postData.put("cardNo", cardNo);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, "doExchangeHouseCard");
    		String code = result.getString("code");
    		if(ReturnCode.OK == Integer.parseInt(code)){
    			restP.setSuccess(true);
    		} else {
    			restP.setSuccess(false);
    		}
			logger.info("end to run /settleOrder.");
		} catch (Exception e) {
			restP.setSuccess(false);
			logger.error("settleOrder：订单结算失败.",e);
			e.printStackTrace();
		}
		return restP;
	}
	
}
