package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.mmzb.house.web.action.dto.ContactInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.JsonGeneratorUtils;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

@Controller
public class ContactAction extends BaseAction {
	
    @Resource(name="appProperties")
	private AppProperties appProperties;
    private static Logger logger = LoggerFactory.getLogger(ContactAction.class);
    @RequestMapping(value = "/my/toModifyContact.htm", method = { RequestMethod.GET })
    public ModelAndView toModifyContact(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", "e4219adeb64af83cf9ba9be5d8f8f071");
    	postData.put("contactId", 19);
		ContactInfo contactInfo=new ContactInfo();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getContactById");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
            if("0".equals(code)){
       			JSONArray array = JSONArray.fromObject(result.get("data")); 
	       		for(int i = 0; i < array.size(); i++){
	       			net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
	    	    	String contactId= jsonObject.get("contactId").toString();
	    	    	String contactName= jsonObject.get("contactName").toString();
	    	    	String contactIdCard= jsonObject.get("contactIdCard").toString();
	    	    	String contactPhone= jsonObject.get("contactPhone").toString();
	    	    	contactInfo.setContactId(contactId);
	    	    	contactInfo.setContactIdCard(contactIdCard);
	    	    	contactInfo.setContactName(contactName);
	    	    	contactInfo.setContactPhone(contactPhone);
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
    	map.put("contactInfo", contactInfo);
   		restP.setData(map);
    	return new ModelAndView(Contants.URL_PREFIX + "/contact/modifyContact", "response",restP);
    }
    @RequestMapping(value = "/my/modifyContact.htm", method = { RequestMethod.POST })
    public ModelAndView modifyContact(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", "e4219adeb64af83cf9ba9be5d8f8f071");
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	String contactName=request.getParameter("contactName");
    	String contactIdCard=request.getParameter("contactIdCard");
    	String contactPhone=request.getParameter("contactPhone");
    	try {
			contactName= URLDecoder.decode(contactName,"UTF-8");;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	postData.put("contactName", contactName);
    	postData.put("contactIdCard", contactIdCard);
    	postData.put("contactPhone", contactPhone);
    	postData.put("contactId", 19);
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"modifyContact");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       		if("0".equals(code)){
       		 
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
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/rewardNotActivatedActivityRecordList", "response",restP);
    }
    @RequestMapping(value = "/my/toAddContact.htm", method = { RequestMethod.GET })
    public ModelAndView toAddContact(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	RestResponse restP = new RestResponse();
    	return new ModelAndView(Contants.URL_PREFIX + "/contact/addCollect", "response",restP);
    }
    @RequestMapping(value = "/my/addContact.htm", method = { RequestMethod.POST })
    public ModelAndView addContact(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", "e4219adeb64af83cf9ba9be5d8f8f071");
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	String contactName=request.getParameter("contactName");
    	try {
    		contactName= URLDecoder.decode(contactName,"UTF-8");;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String contactIdCard=request.getParameter("contactIdCard");
    	String contactPhone=request.getParameter("contactPhone");
    	postData.put("contactName", contactName);
    	postData.put("contactIdCard", contactIdCard);
    	postData.put("contactPhone", contactPhone);
    	postData.put("contactId", 0);
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"modifyContact");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       		if("0".equals(code)){
       		 
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
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/rewardNotActivatedActivityRecordList", "response",restP);
    }
    
    @RequestMapping(value = "/my/removeContact.htm", method = { RequestMethod.POST })
    public ModelAndView removeContact(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", "e4219adeb64af83cf9ba9be5d8f8f071");
    	postData.put("contactId", 13);
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"removeContact");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/person/rewardNotActivatedActivityRecordList", "response",restP);
    }
    
    @RequestMapping(value = "/my/getContacts.htm", method = { RequestMethod.GET })
    public ModelAndView getContacts(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
    	
    	UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
    	RestResponse restP = new RestResponse();
    	Map<String, Object> postData=new HashMap<String, Object>();
    	postData.put("uid", "e4219adeb64af83cf9ba9be5d8f8f071");
    	//postData.put("contactId", 1);
    	List<ContactInfo> cardInfoList=new ArrayList<ContactInfo>();
    	try {
    		JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getContacts");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
            if("0".equals(code)){
       			JSONArray array = JSONArray.fromObject(result.get("data")); 
	       		for(int i = 0; i < array.size(); i++){
	       			net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
	       			JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("contacts").toString());
	       			for(int j = 0; j < arrayShowDetail.size(); j++){
		       			ContactInfo contactInfo=new ContactInfo();
		       			net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(i);
		    	    	String contactId= jsonObject1.get("contactId").toString();
		    	    	String contactName= jsonObject1.get("contactName").toString();
		    	    	String contactIdCard= jsonObject1.get("contactIdCard").toString();
		    	    	String contactPhone= jsonObject1.get("contactPhone").toString();
		    	    	contactInfo.setContactId(contactId);
		    	    	contactInfo.setContactIdCard(contactIdCard);
		    	    	contactInfo.setContactName(contactName);
		    	    	contactInfo.setContactPhone(contactPhone);
		    	    	cardInfoList.add(contactInfo);
	       			}
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
    	map.put("cardInfoList", cardInfoList);
    	map.put("num", cardInfoList.size());
   		restP.setData(map);
   		return new ModelAndView(Contants.URL_PREFIX + "/contacts/showContacts", "response",restP);
    }

}
