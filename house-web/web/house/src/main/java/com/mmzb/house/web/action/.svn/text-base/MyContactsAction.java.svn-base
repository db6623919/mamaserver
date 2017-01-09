package com.mmzb.house.web.action; 

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.ContactsInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;

/** 
 * @author  zfm
 * @date 创建时间：2016年6月21日 下午3:40:44
 */
@Controller
public class MyContactsAction extends BaseAction{
	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	@Autowired
	public GerneralMethod gerneralMethod;
	
	private static Logger logger = LoggerFactory.getLogger(MyContactsAction.class);
	
	/**
	 * 查询联系人页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/getMyContacts.htm", method = { RequestMethod.GET })
	public String getMyContacts(HttpServletRequest request, HttpServletResponse response, Model model) {
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		//判断是否登陆
		model.addAttribute("isLogin", 1);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("imgurl", imgurl);
		
		return Contants.URL_PREFIX + "/mycontacts/showMyContacts";
	}

	/**
	 * 查询联系人
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/getMyContactsJson.htm", method = { RequestMethod.GET })
	@ResponseBody
    public RestResponse getMyContactsJson(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		List<ContactsInfo> contactsList = new ArrayList<ContactsInfo>();
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getMyContacts");
			String msg = result.get("msg").toString();
			String code = result.get("code").toString();
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data")); 
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					int num = jsonObject.getInt("num");
					if (num > 0) {
	       			JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("contacts").toString());
	       			for (int j = 0; j < arrayShowDetail.size(); j++) {
	       				ContactsInfo contactsInfo = new ContactsInfo();
	       				net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
	       				String contactsId = jsonObject1.getString("contactsId").toString();
	       				String contactsName = jsonObject1.getString("contactsName").toString();
	       				String contactsPhone = jsonObject1.getString("contactsPhone").toString();
	       				contactsInfo.setContactsId(contactsId);
	       				contactsInfo.setContactsName(contactsName);
	       				contactsInfo.setContactsPhone(contactsPhone);
	       				contactsList.add(contactsInfo);
					}
					}
				}
				map.put("code", 0);
				map.put("contactsList", contactsList);
				map.put("num", contactsList.size());
				restP.setSuccess(true);
				restP.setData(map);
			} else {
				map.put("code", 1);
				map.put("msg", "查询失败.");
			}
		} catch (Exception e) {
			logger.error("错误日志", e);
			map.put("code", 2);
			map.put("msg", "系统繁忙，请稍后重试.");
		}	
		return restP;
	
	}
	
	/**
	 * 添加我的联系人页面
	 * @return
	 */
	@RequestMapping(value = "/my/toAddMyContacts.htm", method = { RequestMethod.GET })
	public ModelAndView toAddMyContacts(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception{
		RestResponse restP = new RestResponse();
		
		String travelAddress = appProperties.getTravelAddress();
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				travelAddress += ("?token=" + token);
				model.addAttribute("isLogin", "1");

			}
		}
		model.addAttribute("travelAddress", travelAddress);
		return new ModelAndView(Contants.URL_PREFIX + "/mycontacts/addMyContacts", "response",restP);
	}
	
	/**
	 * 获取token
	 * 
	 * @param hsr
	 * @return
	 */
	private String getToken(HttpServletRequest hsr) {
		Cookie cookie = CasCookie.getCookie(hsr);
		logger.debug("Get cookie:{}", cookie);
		return cookie == null ? null : cookie.getValue();
	}
	
	/**
	 * 添加加联系人
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/addMyContacts.htm", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse addMyContacts(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String contactsName = request.getParameter("contactsName");
		try {
			contactsName= URLDecoder.decode(contactsName,"UTF-8");;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String contactsPhone = request.getParameter("contactsPhone");
		postData.put("contactsName", contactsName);
		postData.put("contactsPhone", contactsPhone);
		
		
		try {
			JSONObject results=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getIsExistMyContacts");
			String codes = results.get("code").toString();
			map.put("code", codes);
			if ("0".equals(codes)) {
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData,appProperties.getRequestRoot(), "insertMyContacts");
				net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(result.get("data"));
				String msg = result.get("msg").toString();
				String code = result.get("code").toString();
				map.put("msg", msg);
				map.put("code", code);
				if ("0".equals(code)) {
					model.addAttribute("contactsName",request.getParameter("contactsName"));
					model.addAttribute("contactsPhone",request.getParameter("contactsPhone"));
					map.put("contactsId",object.getString("contactsId"));
					map.put("code", 0);
					map.put("msg", "添加成功");
					restP.setSuccess(true);
				} else {
					map.put("code", 1);
					map.put("msg", "添加失败");
					restP.setSuccess(false);
				}
			} else {
				map.put("code", 2);
				map.put("msg", "添加失败,手机号和姓名重复!");
			}
			
		} catch (IOException e) {
			logger.error("错误日志", e);
			map.put("code", 2);
			map.put("msg", "系统繁忙，请稍后重试.");
			restP.setSuccess(false);
		}
		restP.setData(map);
		return restP;
	}
	
	/**
	 * 修改联系人页面
	 * @return
	 */
	@RequestMapping(value = "/my/toModifyMyContacts.htm", method = { RequestMethod.GET })
	public ModelAndView toModifyMyContacts(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception{
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		String contactsIds = request.getParameter("contactsId");
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		postData.put("contactsId", contactsIds);       
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getMyContactsById");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       		if ("0".equals(code)) {
       			net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(result.get("data"));
   				ContactsInfo contactsInfo = new ContactsInfo();
   			    String contactsId = object.getString("contactId").toString();
   				String contactsName = object.getString("contactName").toString();
   				String contactsPhone = object.getString("contactPhone").toString();
   				contactsInfo.setContactsId(contactsId);
   				contactsInfo.setContactsName(contactsName);
   				contactsInfo.setContactsPhone(contactsPhone);
   				
   				String travelAddress = appProperties.getTravelAddress();
   				String token = getToken(request);
   				if (StringUtil.isNotEmpty(token)) {
   					VfSsoUser.setCurrentToken(token);
   					User userInfos = VfSsoUser.get();
   					if (userInfos != null) {
   						travelAddress += ("?token=" + token);
   						model.addAttribute("isLogin", "1");

   					}
   				}
   				
   				map.put("contactsInfo", contactsInfo);
   				model.addAttribute("travelAddress", travelAddress);
   		   		restP.setData(map);
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败.");

			}
		} catch (IOException e) {
			logger.error("错误日志", e);
			map.put("code", 2);
			map.put("msg", "系统繁忙，请稍后重试.");
		}
		model.addAttribute("imgurl", imgurl);
		setLoginStatus(request, model);
		//判断是否登陆
   		return new ModelAndView(Contants.URL_PREFIX + "/mycontacts/modifyMyContacts", "response",restP);
	}
	
	private void setLoginStatus(HttpServletRequest request, ModelMap model)
	{
		int isLogin = 0;
		try 
		{
			UserInfo loginUser = getLoginUserInfo(request);
			isLogin = (loginUser != null) ? 1 : 0;
			
		} 
		catch (Exception e)
		{
			logger.warn("failed to get loginStatus", e);
			isLogin = 0;
		}
		
		model.addAttribute("isLogin", isLogin);
	}
	
	/**
	 * 修改联系人
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/modifyMyContacts.htm", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse modifyMyContacts(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		
		String contactsName = request.getParameter("contactsName");
		String contactsPhone = request.getParameter("contactsPhone");
		String contactsId = request.getParameter("contactsId");
		postData.put("contactsName", contactsName);
		postData.put("contactsPhone", contactsPhone);
		postData.put("contactsId", contactsId);
		
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"modifyMyContacts");
			String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       		if ("0".equals(code)) {
       			model.addAttribute("contactsName",request.getParameter("contactsName"));
				model.addAttribute("contactsPhone",request.getParameter("contactsPhone"));
				map.put("code", 0);
				restP.setSuccess(true);
				restP.setData(map);
			}  else {
				map.put("code", 1);
				map.put("msg", "修改失败");
				restP.setSuccess(false);
			}
		} catch (IOException e) {
			logger.error("错误日志", e);
			map.put("code", 2);
			map.put("msg", "系统繁忙，请稍后重试.");
		}
		return restP;
	}
	
	/**
	 * 删除联系人
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/my/removeMyContacts.htm", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse removeMyContacts(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		String contactsId = request.getParameter("contactsId");
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		postData.put("contactsId", contactsId);
		try {
			JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"removeMyContacts");
    		String msg=result.get("msg").toString();
    		String code=result.getString("code");
       		map.put("msg",msg);
       		map.put("code", code);
       		restP.setSuccess(true);
			restP.setData(map);
		} catch (ClientProtocolException e) {
			restP.setSuccess(false);
			map.put("code", 1);
			map.put("msg", "删除失败.");
			logger.error("错误日志", e);
		} catch (IOException e) {
			map.put("code", 2);
			map.put("msg", "系统繁忙，删除失败.");
			restP.setSuccess(false);
			logger.error("错误日志", e);
		}
		return restP;
	}
	
	/** 
	 * 选择联系人页面
	 */
	@RequestMapping(value = "/my/choose_myContacts.htm", method = { RequestMethod.GET })
	public ModelAndView choose_myContacts(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		Map<String, Object> map = new HashMap<String, Object>();
		RestResponse restP = new RestResponse();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		
		//参数
		List<ContactsInfo> contactsList = new ArrayList<ContactsInfo>();
		try {
			
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData,appProperties.getRequestRoot(), "getMyContacts");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					int num = jsonObject.getInt("num");
					if (num > 0) {
						JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("contacts").toString());
						for (int j = 0; j < arrayShowDetail.size(); j++) {
							ContactsInfo contactsInfo = new ContactsInfo();
							net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
		       				String contactsId = jsonObject1.getString("contactsId").toString();
		       				String contactsName = jsonObject1.getString("contactsName").toString();
		       				String contactsPhone = jsonObject1.getString("contactsPhone").toString();
		       				contactsInfo.setContactsId(contactsId);
		       				contactsInfo.setContactsName(contactsName);
		       				contactsInfo.setContactsPhone(contactsPhone);
		       				contactsList.add(contactsInfo);

						}
					}
				}
				map.put("code", 0);
				map.put("contactsList", contactsList);
				map.put("num", contactsList.size());
				restP.setSuccess(true);
				restP.setData(map);
				model.addAttribute("contactsList", contactsList);
				
				model.addAttribute("houseId", request.getParameter("houseId"));
				model.addAttribute("houseName", request.getParameter("houseName"));
				model.addAttribute("cityName", request.getParameter("cityName"));
				model.addAttribute("startdate", request.getParameter("startdate"));
				model.addAttribute("enddate", request.getParameter("enddate"));
				model.addAttribute("footerTotalAmt", request.getParameter("footerTotalAmt"));
				model.addAttribute("total", request.getParameter("total"));
				model.addAttribute("actual", request.getParameter("actual"));
				
			} else {
				map.put("code", 1);
				map.put("msg", "获取参数出错.");
			}
		} catch (IOException e) {
			logger.error("错误日志", e);
			map.put("code", 2);
			map.put("msg", "系统繁忙，请稍后重试.");
		}
		return new ModelAndView(Contants.URL_PREFIX + "/mycontacts/chooseMyContacts", "response",restP);
	}
	
	/** 
	 * 选择联系人页面
	 */
	@RequestMapping(value = "/my/choose_myCoupon.htm", method = { RequestMethod.GET })
	public String choose_myCoupon(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UserInfo userInfo = getLoginUserInfo(request);//得到用户的Session
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", userInfo.getMmWalletId());
		//参数
		List<ContactsInfo> contactsList = new ArrayList<ContactsInfo>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData,appProperties.getRequestRoot(), "getMyContacts");
			//String msg = result.get("msg").toString();
			String code = result.getString("code");
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					int num = jsonObject.getInt("num");
					if (num > 0) {
						JSONArray arrayShowDetail = JSONArray.fromObject(jsonObject.get("contacts").toString());
						for (int j = 0; j < arrayShowDetail.size(); j++) {
							ContactsInfo contactsInfo = new ContactsInfo();
							net.sf.json.JSONObject jsonObject1 = arrayShowDetail.getJSONObject(j);
		       				String contactsId = jsonObject1.getString("contactsId").toString();
		       				String contactsName = jsonObject1.getString("contactsName").toString();
		       				String contactsPhone = jsonObject1.getString("contactsPhone").toString();
		       				contactsInfo.setContactsId(contactsId);
		       				contactsInfo.setContactsName(contactsName);
		       				contactsInfo.setContactsPhone(contactsPhone);
		       				contactsList.add(contactsInfo);
						}
					}
				}
				model.addAttribute("contactsList", contactsList);
			} else {
				
			}
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
		return Contants.URL_PREFIX + "/order/choose_myContacts";
	}

}
 