package com.mmzb.house.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.ActivityMemberRecordInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.model.ActivityConfig;
import com.mmzb.house.web.model.ActivityDate;
import com.mmzb.house.web.model.Luckybean;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.ma.service.base.model.Constant;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.CasCookie;

/**
 * 抽奖活动
 * @author Whl
 *
 */
@Controller
public class ActivityAction extends BaseAction {
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	
	private static Logger logger = LoggerFactory.getLogger(ActivityAction.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
	
	private String appId = "wxf429e1850588ada6";
	private String appSecret = "6adca5426a35d0aebed21549b4f9c630";

	@RequestMapping("/my/toPage.htm")
	 public ModelAndView toActivityPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		    logger.info("start to run toActivityPage().");
		    PrintWriter writer = null;
		    try {
		      writer = response.getWriter();
		      String html = "<html><head>                                          " +
		          "  <script type='text/javascript'>                          " +
		          "    function toActivityPage() {                          " +
		          "      document.getElementById('activityHref').click(); " +
		          "    }                                                    " +
		          "  </script> </head>                                        " +
		          "  <body onload='toActivityPage()'>                         " +
		          "    <a href='/activity/toPage.htm' id='activityHref'></a>" +
		          "  </body>                                                  " +
		          "</html>                                                     ";
		      writer.print(html);
		    } catch (IOException e) {
		      logger.error("生成html异常", e);
		      return new ModelAndView("redirect:/activity/toPage.htm");
		    }
		    finally
		    {
		    	if(writer != null)
		    	{
		    		writer.flush();
		    		writer.close();
		    	}
		    }
		    
		   return null;
	 }
	/*@RequestMapping("/my/toPage.htm")
	public ModelAndView toActivityPage(HttpServletRequest request, ModelMap model) {
		logger.info("start to run toActivityPage().");
		return new ModelAndView("redirect:/activity/toPage.htm");
	}*/

	/**
	 * 加载初始化配置
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/activity/toPage.htm")
	public ModelAndView toPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		logger.info("start to run toPage().");
		String friendcode = ""; //朋友码
		if (null != request.getParameter("friendcode") && !("").equals(request.getParameter("friendcode"))) {
			friendcode = request.getParameter("friendcode");
		}
		
		//站长统计
		CS cs = new CS(1259956374);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
	/*	String nonceStr = UUID.randomUUID().toString();
		RedisRequest reqRe = new RedisRequest();
		reqRe.setKey("mmsfAT001:weixin:weiXinTokenName");
		reqRe.setFiled("signToken");
	
		String jsapi_ticket = (String)redisFacade.getValueByKeyAndField(reqRe);
		logger.info("===开始调用redis数据库服务,返回signToken is {}.",jsapi_ticket);
		reqRe.setKey("mmsfAT001:weixin:weiXinTokenTime"); 	
		reqRe.setFiled("signTime");
		long timestamp = System.currentTimeMillis()/1000;
		String time = String.valueOf(redisFacade.getValueByKeyAndField(reqRe));
	    long signTime = 0;
	    if(StringUtil.isNumeric(time)){
	    	signTime = Long.parseLong(time);
	    }
	    logger.info("===开始调用redis数据库服务,返回signTime is {}.",signTime);
	    long subTime = timestamp - signTime;
		
		if(StringUtil.isBlank(jsapi_ticket)||subTime > 3600){
			try {
				jsapi_ticket = goWeiXinGetTicket(timestamp);
			} catch (Exception e) {
				logger.info("=== 获取jsapi_ticket is error.");
				e.printStackTrace();
			}
		}	
		
		String timestampStr = Long.toString(timestamp);
		logger.info("====request.getRequestURI():{" + request.getQueryString() + "}");
		//System.out.println("request.getRequestURI()="+request.getRequestURI() + "    request.getQueryString()="+ request.getQueryString());
//		String url = "http://m.mmsfang.com" + request.getRequestURI();
		String url = "http://tm.mmsfang.com/activity/toPage.htm";
		int startIndex = request.getRequestURI().indexOf(";jsessionid");
		logger.info("URI  is {}.", request.getRequestURI());
		if(startIndex > 0){
			url += request.getRequestURI().substring(startIndex);
			
		}
		if(!StringUtils.isEmpty(request.getQueryString())) {
			url += "?" + request.getQueryString();
		}
		logger.info("====生成URL:{" + url + "}");
		//用sha1生成分享的签名
		String signature = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestampStr+"&url=" + url;
		
		logger.info("用于生成分享等待签名的signature = "+signature);
		try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signature.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
		logger.info("生成后的签名的signature is {}. ",signature);
	    model.put("noncestr", nonceStr);
	    model.put("signature", signature);
	    model.put("timestamp", timestampStr);
	    model.put("appId", appId);*/
	    model.put("imgurl", imgurl);
	    model.put("friendcode", friendcode);
		
	   /* if (logger.isDebugEnabled()) {
	       logger.debug("end to run toPage(),noncestr={},signature={},timestamp={},appId={}.",nonceStr,signature,timestampStr,appId);
	    }*/
	    
		return new ModelAndView(Contants.URL_PREFIX + "/activity1/recordActitivy");
	}
	
	/**
	 * 微信签名验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/activity/loadWx.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String ,Object> loadWx(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String url = request.getParameter("nowUrl").replace("＆", "&");
		try {
			String nonceStr = UUID.randomUUID().toString();
			RedisRequest reqRe = new RedisRequest();
			reqRe.setKey("mmsfAT001:weixin:weiXinTokenName");
			reqRe.setFiled("signToken");
		
			String jsapi_ticket = (String)redisFacade.getValueByKeyAndField(reqRe);
			logger.info("===开始调用redis数据库服务,返回signToken is {}.",jsapi_ticket);
			reqRe.setKey("mmsfAT001:weixin:weiXinTokenTime"); 	
			reqRe.setFiled("signTime");
			long timestamp = System.currentTimeMillis()/1000;
			String time = String.valueOf(redisFacade.getValueByKeyAndField(reqRe));
		    long signTime = 0;
		    if(StringUtil.isNumeric(time)){
		    	signTime = Long.parseLong(time);
		    }
		    logger.info("===开始调用redis数据库服务,返回signTime is {}.",signTime);
		    long subTime = timestamp - signTime;
			
			if(StringUtil.isBlank(jsapi_ticket)||subTime > 3600){
				try {
					jsapi_ticket = goWeiXinGetTicket(timestamp);
				} catch (Exception e) {
					logger.info("=== 获取jsapi_ticket is error.");
					e.printStackTrace();
				}
			}	
			String timestampStr = Long.toString(timestamp);
			logger.info("URI  is {}.", url);
			//用sha1生成分享的签名
			String signature = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestampStr+"&url=" + url;
			logger.info("用于生成分享等待签名的signature = "+signature);
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signature.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
			logger.info("生成后的签名的signature is {}. ",signature);
			result.put("code", 0);
			result.put("noncestr", nonceStr);
			result.put("signature", signature);
			result.put("timestamp", timestampStr);
			result.put("appId", appId);
		} catch (Exception e) {
			result.put("code", 1);
			logger.error("loadWx() is failed,exception:{}.",e); 
		}
		return result;
	}
	
	public static String gbEncoding(final String gbString) {   
        char[] utfBytes = gbString.toCharArray();   
              String unicodeBytes = "";   
               for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
                    String hexB = Integer.toHexString(utfBytes[byteIndex]);   
                      if (hexB.length() <= 2) {   
                          hexB = "00" + hexB;   
                     }   
                      unicodeBytes = unicodeBytes + "\\u" + hexB;   
                  }   
                  System.out.println("unicodeBytes is: " + unicodeBytes);   
                  return unicodeBytes;   
             }
	
	/**
	 * 初始化页面配置信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/activity/initPage.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String ,Object> initPage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("satrt to run initPage()");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("activityCode", "AT_001");
		try {
			JSONObject getResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "initActivityConf");
			String code = getResult.getString("code");
			if(code.equals("0")){
				logger.info("initPage() interface call successed.");
				ActivityConfig activityConfig = new ActivityConfig();
				JSONObject dataJson = getResult.getJSONObject("data");
				JSONObject activityConfigJson = dataJson.getJSONObject("activityConfig");
				
				//活动日
				com.meidusa.fastjson.JSONArray activityDateArray = activityConfigJson.getJSONArray("activityDates");
				List<ActivityDate> ActivityDateList = new ArrayList<ActivityDate>();
				for (int i = 0; i < activityDateArray.size(); i++) {
					JSONObject indexJson = activityDateArray.getJSONObject(i);
					ActivityDate ad= new ActivityDate();
					ad.setCalendarField(indexJson.getInteger("calendarField"));
					ad.setFieldValue(indexJson.getInteger("fieldValue"));
					ActivityDateList.add(ad);
				}
				activityConfig.setActivityDates(ActivityDateList);
				
				//中奖积分
				com.meidusa.fastjson.JSONArray luckyBeansArray = activityConfigJson.getJSONArray("luckyBeans");
				List<Luckybean> LuckybeanList = new ArrayList<Luckybean>();
				for (int i = 0; i < luckyBeansArray.size(); i++) {
					JSONObject indexJson = luckyBeansArray.getJSONObject(i);
					Luckybean ld= new Luckybean();
					ld.setBeanNums(indexJson.getInteger("beanNums") );
					ld.setNote(indexJson.getString("note"));
					ld.setProbability(indexJson.getInteger("probability"));
					ld.setRandomNumRange(indexJson.getInteger("randomNumRange"));
					LuckybeanList.add(ld);
				}
				activityConfig.setLuckyBeans(LuckybeanList);
				//可分享抽奖次数
				activityConfig.setActivityShareTimes(activityConfigJson.getInteger("activityShareTimes"));
				//加载配置
				result.put("activityConfig", activityConfig);
				
				//result.put("isRcordTimes", dataJson.getInteger("isRcordTimes"));//是否抽奖时间段
				result.put("isActivityDate", dataJson.getInteger("isActivityDate")); //是否抽奖日
	        	
				//会员抽奖记录
				JSONObject resultObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getLotteryRecord");
				JSONObject recordJson = resultObject.getJSONObject("data");
				if(resultObject.getString("code").equals("0")){
					com.meidusa.fastjson.JSONArray amrListJson = recordJson.getJSONArray("amrList");
					List<ActivityMemberRecordInfo> activityMemberRecordList = new ArrayList<ActivityMemberRecordInfo>();
					for(int i=0 ; i<amrListJson.size(); i++){
						JSONObject jsonObject = amrListJson.getJSONObject(i);
						ActivityMemberRecordInfo activityMemberRecord = new ActivityMemberRecordInfo();
						activityMemberRecord.setMemberIdentity(jsonObject.getString("memberIdentity"));
						activityMemberRecord.setTotalPoint(jsonObject.getInteger("totalPoint"));
						activityMemberRecordList.add(activityMemberRecord);
					}
					result.put("amrList", activityMemberRecordList);
				}
			}else{
				logger.error("initPage() interface call failed.");
			}
			result.put("code", code);
		} catch (Exception e) {
			logger.error("initPage() is failed,exception:{}.",e); 
			e.printStackTrace();
		}
		
		if (logger.isDebugEnabled()) {
		    logger.debug("initPage() is finished,params is {}.",result.toString());
		 }
		return result;
	}
	
	
	/**
	 * 加载个人信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/initMemberInfo.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> initMemberInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		logger.info("start to run initMemberInfo()");
		//判断是否登陆
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			String memberId="";
			User userInfo = null;
			String token = getToken(request);
			if (StringUtil.isNotEmpty(token)) {
				VfSsoUser.setCurrentToken(token);
				userInfo = VfSsoUser.get();
				if (userInfo != null) {
					memberId = getMemberInfo(request.getSession()).getMemberId();
				}
			} 
			postData.put("memberId", memberId);
			postData.put("activityCode", "AT_001");
			logger.info("initMemberInfo():memberId={},activityCode={}.",memberId,"AT_001");
			if(!StringUtils.isEmpty(memberId)){
				JSONObject getResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "initMemberInfo");
				String code = getResult.getString("code");
				if(code.equals("0")) {
					JSONObject dataJson = getResult.getJSONObject("data");
					if(!StringUtils.isEmpty(dataJson.getString("nowDate"))) {//系统当前时间
						String[] timeStrs = dataJson.getString("nowDate").split(",");
						result.put("nowDate", timeStrs[0]);
						result.put("status", timeStrs[1]);
						result.put("publishTime", timeStrs[2]);
					} else {
						result.put("nowDate", 0);
					}
					result.put("isShare", dataJson.getInteger("isShare"));
					result.put("recordSize", dataJson.getInteger("recordSize"));
					result.put("totalPoint", dataJson.getInteger("totalPoint"));
				} else {
					logger.info("initMemberInfo():初始化个人信息异常.");
				}
				result.put("code", code);
			} else {
				result.put("recordSize", 3);
				result.put("totalPoint", 0);
		        result.put("code", 0);
			}
		} catch (Exception e) {
			logger.error("initMemberInfo():初始化个人信息异常.", e);
		} 
		
		if (logger.isDebugEnabled()) {
		    logger.debug("initMemberInfo() is finished,params is {}.",result.toString());
		 }
		return result;
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
	 * 抽奖
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/getRandomLuckyBean.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getRandomLuckyBean(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("start run to getRandomLuckyBean()");
		String isLogin = "0"; //判断是否登陆
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			User userInfo = VfSsoUser.get();
			if (userInfo != null) {
				isLogin = "1";
			}
		} 
		Map<String, Object> resultData = new HashMap<String, Object>();
		if(isLogin.equals("1")){
			Map<String, Object> postData = new HashMap<String, Object>();
			try {
				PersonalMemberInfo personalMemberInfo = getMemberInfo(request.getSession());
				postData.put("memberId", personalMemberInfo.getMemberId());
				postData.put("memberIdentity", getMemberPhone(personalMemberInfo));
				postData.put("activityCode", "AT_001");
				
				logger.info("getRandomLuckyBean:调用createActivity,memberId={},memberIdentity={}.",personalMemberInfo.getMemberId(),getMemberPhone(personalMemberInfo));
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "createActivity");
				String code = result.getString("code");
				if(code.equals("0")) {
					JSONObject dataJson = result.getJSONObject("data");
					
					resultData.put("isActivityDate", dataJson.getInteger("isActivityDate"));
					if(dataJson.getInteger("isActivityDate") == 0) { //非抽奖日
						return resultData;
					}
					
					resultData.put("isRcordTimes", dataJson.getInteger("isRcordTimes"));
					if(dataJson.getInteger("isRcordTimes") == 0) { //非抽奖时间段
						return resultData;
					}
					
					resultData.put("isExceedTimes", dataJson.getInteger("isExceedTimes"));
					if(dataJson.getInteger("isExceedTimes") == 0) { //已超出抽奖次数
						return resultData;
					}
					
					resultData.put("recordSize", dataJson.getInteger("recordSize"));
					resultData.put("usedSize", dataJson.getInteger("usedSize"));
					resultData.put("luckyIndex", dataJson.getInteger("luckyIndex"));
		  			JSONObject lbJson = dataJson.getJSONObject("luckybean");
		  			resultData.put("beanNums", lbJson.getInteger("beanNums"));
		  			resultData.put("note", lbJson.getString("note"));
		  			
				}
				resultData.put("code", code);
			} catch (ClientProtocolException e) {
				logger.error("getRandomLuckyBean:调用createActivity失败", e);
				e.printStackTrace();
			} 
		}else{
			resultData.put("isLogin", isLogin);
		}
		if (logger.isDebugEnabled()) {
		    logger.debug("getRandomLuckyBean() is finished,params is {}.",resultData.toString());
		 }
		return resultData;
	}
	
	/**
	 * 更新分享次数
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/doShare.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> doShare(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("start to run doShare(),request:{},response:{}.", request.toString(), response.toString());
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String memberId="";
		User userInfo = null;
		//判断是否登陆
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			userInfo = VfSsoUser.get();
			if (userInfo != null) {
				memberId = getMemberInfo(request.getSession()).getMemberId();
				logger.info("memgerId is {}", memberId);
			}
		} 
		//判断是否登陆
		if(!StringUtils.isEmpty(memberId)) {
			try {
				postData.put("memberId", memberId);
				postData.put("memberIdentity", userInfo.getName());
				postData.put("activityCode", "AT_001");
				JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "doShare");
				String code = result.getString("code");
				JSONObject dataJson = result.getJSONObject("data");
				resultData.put("isShare", dataJson.getInteger("isShare"));
				resultData.put("shareTime", dataJson.getInteger("shareTime"));
				resultData.put("code", code);
			} catch (Exception e) {
				logger.error("doShare():调用doShare异常.", e);
				e.printStackTrace();
			} 
		} else { //非登陆
			resultData.put("code", 2); 
		}
		if (logger.isDebugEnabled()) {
		    logger.debug("doShare() is finished,params is {}.",resultData.toString());
		 }
		return resultData;
	}
	
	
	private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

	

	/**
	 * 从微信获取分享到朋友圈或朋友的ticket值
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	private String goWeiXinGetTicket(long timestamp) throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		//获取微信中返回的access token值
		
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
		HttpGet getToken = new HttpGet(tokenUrl);  
        
		HttpResponse result = httpClient.execute(getToken);  

		String resData = EntityUtils.toString(result.getEntity());
		JSONObject json = new JSONObject();  
		json = json.parseObject(resData);
		String access_token = json.getString("access_token");
		
		//获取微信中返回的jsapi_ticket值
		String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		HttpGet getTicket = new HttpGet(ticketUrl);  
		result = httpClient.execute(getTicket);
		resData = EntityUtils.toString(result.getEntity());
		json = json.parseObject(resData);
		String jsapi_ticket = json.getString("ticket");
		
		// 缓存jsapi_ticket值和当前时间戳
		RedisRequest reqRe = new RedisRequest();
		reqRe.setKey("mmsfAT001:weixin:weiXinTokenName");
		reqRe.setFiled("signToken");
		reqRe.setValue(jsapi_ticket);
		logger.info("=================开始调用redis数据库服务===signToken==="+jsapi_ticket);
	    Long respTicket = (Long) redisFacade.putKeyAndFieldAndValue(reqRe);
	    logger.info("================插入signToken数据结果response====="+respTicket);
	    reqRe.setKey("mmsfAT001:weixin:weiXinTokenTime");
		reqRe.setFiled("signTime");
		reqRe.setValue(String.valueOf(timestamp));
		logger.info("=================开始调用redis数据库服务===signTime==="+timestamp);
	    Long respTime = (Long) redisFacade.putKeyAndFieldAndValue(reqRe);
	    logger.info("================插入timestamp数据结果response====="+respTime);
	    return jsapi_ticket;
	}
	
	/**
	 * 抽奖积分显示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/showMemberRecord.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> showMemberRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("start run to showMemberRecord()");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		List<ActivityMemberRecordInfo> activityMemberRecordList = new ArrayList<ActivityMemberRecordInfo>();
		JSONObject resultObject = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getLotteryRecord");
		JSONObject recordJson = resultObject.getJSONObject("data");
		if(resultObject.getString("code").equals("0")){
			com.meidusa.fastjson.JSONArray amrListJson = recordJson.getJSONArray("amrList");
			for(int i=0 ; i<amrListJson.size(); i++){
				JSONObject jsonObject = amrListJson.getJSONObject(i);
				ActivityMemberRecordInfo activityMemberRecord = new ActivityMemberRecordInfo();
				activityMemberRecord.setMemberIdentity(jsonObject.getString("memberIdentity"));
				activityMemberRecord.setTotalPoint(jsonObject.getInteger("totalPoint"));
				activityMemberRecordList.add(activityMemberRecord);
			}
			result.put("amrList", activityMemberRecordList);
		} 
		if (logger.isDebugEnabled()) {
		    logger.debug("showMemberRecord() is finished,activityMemberRecordList.size()={}.",activityMemberRecordList.size());
		 }
		return result;
	}
	
	/**
	 * 判断是否登陆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/checkLogin.htm", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> checkLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("start run to checkLogin()");
		String memberId="";
		User userInfo = null;
		//判断是否登陆
		String token = getToken(request);
		if (StringUtil.isNotEmpty(token)) {
			VfSsoUser.setCurrentToken(token);
			userInfo = VfSsoUser.get();
			if (userInfo != null) {
				memberId = getMemberInfo(request.getSession()).getMemberId();
			}
		} 
		Map<String, Object> result = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(memberId)){
			result.put("isLogin", 1);
		}else{
			result.put("isLogin", 0);
		}
		if (logger.isDebugEnabled()) {
		    logger.debug("showMemberRecord() is finished,isLogin is {}.",result.toString());
		 }
		return result;
	}
	
	
	/**
	 * 单身趴活动页面
	 * @time
	 * @return 单身趴活动
	 */
	
	@RequestMapping(value = "/activity/singleParty.htm")
	public String toSingleParty(Model model) {
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("ni", "ma");
		
		model.addAttribute("key", "doubi");
		model.addAttribute("map", keyMap);
		return Contants.URL_PREFIX + "/house/room-detail";
	}
	
	@RequestMapping(value = "/activity/homeLoan.htm")
	public String toHomeLoan()
	{
		return Contants.URL_PREFIX + "/activityRecord/home-loan";
	}
	
	/**
	 * 99元特价房活动首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/special_offer_99/index.htm", method = { RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfSpecialOffer99"); 
	}
	
	/**
	 * 99元特价房活动规则页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/special_offer_99/rule.htm", method = { RequestMethod.GET })
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfSpecialOffer99"); 
	}
	
	/**
	 * 团建活动
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activity/team_build/index.htm", method = { RequestMethod.GET })
	public ModelAndView teamBuild(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception {
		return new ModelAndView(Contants.URL_PREFIX + "/mmsf/mmsfSpecialOffer99"); 
	}
	
}
