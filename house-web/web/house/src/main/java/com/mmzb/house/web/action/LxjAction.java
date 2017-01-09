package com.mmzb.house.web.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.core.common.util.Contants;
import com.netfinworks.common.lang.StringUtil;

/**
 * 旅行家
 * @author Administrator
 *
 */
@Controller
public class LxjAction extends BaseAction{
	
private static Logger logger = LoggerFactory.getLogger(ActivityAction.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
	
	private String appId = "wxf429e1850588ada6";
	private String appSecret = "6adca5426a35d0aebed21549b4f9c630";


	/**
	 * 进入页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/lxj/toPage.htm")
	public ModelAndView toPage(HttpServletRequest request, ModelMap model) {
		String nonceStr = UUID.randomUUID().toString();
		RedisRequest reqRe = new RedisRequest();
		reqRe.setKey("weixin:weiXinTokenName");
		reqRe.setFiled("signToken");
	
		String jsapi_ticket = (String)redisFacade.getValueByKeyAndField(reqRe);
		log.info("=================开始调用redis数据库服务===返回signToken==="+jsapi_ticket);
		reqRe.setKey("weixin:weiXinTokenTime"); 	
		reqRe.setFiled("signTime");
		long timestamp = System.currentTimeMillis()/1000;
		String time = String.valueOf(redisFacade.getValueByKeyAndField(reqRe));
	    long signTime = 0;
	    if(StringUtil.isNumeric(time)){
	    	signTime = Long.parseLong(time);
	    }
	    logger.info("=================开始调用redis数据库服务===返回signTime==="+signTime);
	    long subTime = timestamp - signTime;
		
		if(StringUtil.isBlank(jsapi_ticket)||subTime > 3600){
			try {
				jsapi_ticket = goWeiXinGetTicket(timestamp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		String timestampStr = Long.toString(timestamp);
		logger.info("====request.getRequestURI():{" + request.getQueryString() + "}");
		String url = "http://m.mmsfang.com" + request.getRequestURI();
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
		logger.info("生成后的签名的signature = "+signature);
	    model.put("noncestr", nonceStr);
	    model.put("signature", signature);
	    model.put("timestamp", timestampStr);
	    model.put("appId", appId);
		return new ModelAndView(Contants.URL_PREFIX + "/lxj/main_lxj");
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
		reqRe.setKey("weixin:weiXinTokenName");
		reqRe.setFiled("signToken");
		reqRe.setValue(jsapi_ticket);
		log.info("=================开始调用redis数据库服务===signToken==="+jsapi_ticket);
	    Long respTicket = (Long) redisFacade.putKeyAndFieldAndValue(reqRe);
	    log.info("================插入signToken数据结果response====="+respTicket);
	    reqRe.setKey("weixin:weiXinTokenTime");
		reqRe.setFiled("signTime");
		reqRe.setValue(String.valueOf(timestamp));
		log.info("=================开始调用redis数据库服务===signTime==="+timestamp);
	    Long respTime = (Long) redisFacade.putKeyAndFieldAndValue(reqRe);
	    log.info("================插入timestamp数据结果response====="+respTime);
	    return jsapi_ticket;
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
}
