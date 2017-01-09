package com.mmzb.house.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.CardInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

@Controller
public class MemberAction extends BaseAction {
	
    @Resource(name="appProperties")
	private AppProperties appProperties;
    private static Logger logger = LoggerFactory.getLogger(MemberAction.class);
    //获取vip会员卡信息
    @RequestMapping(value = "/my/getCardInfo.htm", method = { RequestMethod.POST })
    public ModelAndView getCardInfo(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", loginUser.getUid());
    	CardInfo cardInfo=new CardInfo();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCardInfo");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
              if("0".equals(code)){
       			
       			JSONArray array = JSONArray.fromObject(result.get("data")); 
	       		for(int i = 0; i < array.size(); i++){
	       			net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
	    	    	String cardId= jsonObject.get("cardId").toString();
	    	    	String cardPassword= jsonObject.get("cardPassword").toString();
	    	    	String totalAmt= jsonObject.get("totalAmt").toString();
	    	    	String freezeAmt= jsonObject.get("freezeAmt").toString();
	    	    	String availAmt= jsonObject.get("availAmt").toString();
	    	    	Integer status= Integer.parseInt(jsonObject.get("status").toString());
	    	    	Integer level=Integer.parseInt(jsonObject.get("uid").toString()) ;
	    	    	cardInfo.setAvailAmt(availAmt);
	    	    	cardInfo.setCardId(cardId);
	    	    	cardInfo.setCardPassword(cardPassword);
	    	    	cardInfo.setFreezeAmt(freezeAmt);
	    	    	cardInfo.setLevel(level);
	    	    	cardInfo.setStatus(status);
	    	    	cardInfo.setTotalAmt(totalAmt);
	       		}
       		}else{
    			try {
	  				JsonGeneratorUtils.createRetMaptJSONObject(response, code,msg);
	  			} catch (IOException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
    	        return null; 
    		}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
    	map.put("cardInfo", cardInfo);
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/showCardInfo", "response",restP);
    }
    //验证vip会员
    @RequestMapping(value = "/my/checkCardInfo.htm", method = { RequestMethod.POST })
    public ModelAndView checkCardInfo(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", loginUser.getUid());
    	String name=request.getParameter("name");
    	String phone=request.getParameter("phone");
    	String idCard=request.getParameter("idCard");
    	postData.put("name", name);
    	postData.put("phone", phone);
    	postData.put("idCard", idCard);
    	
    	CardInfo cardInfo=new CardInfo();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"checkCardInfo");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		if("0".equals(restP.getCode())){
       		 
     		}else{
     			try {
	  				JsonGeneratorUtils.createRetMaptJSONObject(response, code,msg);
	  			} catch (IOException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
     	        return null; 
     		}
       		map.put("code", code);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
    	map.put("cardInfo", cardInfo);
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/rewardNotActivatedActivityRecordList", "response",restP);
    }

}
