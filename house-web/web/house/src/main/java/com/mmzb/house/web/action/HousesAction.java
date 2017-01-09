package com.mmzb.house.web.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.common.entity.KWSearchResultEntity;
import com.mama.server.common.entity.KWSearchResultEntityH5;
import com.mama.server.common.util.PartnershipEnum;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.City;
import com.mmzb.house.web.action.dto.HouseInfo;
import com.mmzb.house.web.action.dto.HouseTag;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.Response;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.mmzb.house.web.model.OrderCommentVo;
import com.mmzb.house.web.model.TradeArea;
import com.mmzb.house.web.model.houses.Facilities;
import com.mmzb.house.web.model.houses.HouseCommentVo;
import com.mmzb.house.web.model.houses.HouseDetailVo;
import com.mmzb.house.web.model.houses.HouseEntity;
import com.mmzb.house.web.model.houses.HouseImage;
import com.mmzb.house.web.model.houses.HousePagerEntity;
import com.mmzb.house.web.model.houses.HouseSearchConditionVo;
import com.mmzb.house.web.model.houses.HouseShopVo;
import com.mmzb.house.web.model.houses.Other;
import com.mmzb.house.web.model.houses.RoomFacilityVo;
import com.mmzb.house.web.model.houses.Supporting;
import com.mmzb.house.web.util.CS;
import com.netfinworks.common.lang.StringUtil;
import com.mmzb.house.web.core.common.util.Contants;

@Controller
public class HousesAction extends BaseAction {
	
    @Resource(name="appProperties")
	private AppProperties appProperties;
    
    private static Logger logger = LoggerFactory.getLogger(HousesAction.class);
    
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
	
	private String appId = "wxf429e1850588ada6";
	private String appSecret = "6adca5426a35d0aebed21549b4f9c630";
    
    //获取房源详细信息
    @RequestMapping(value = "/house/toDetail.htm", method = { RequestMethod.GET })
    public String toDetailRoom(HttpServletRequest request,HttpServletResponse response, Model model){
    	if (log.isInfoEnabled())
    	{
			log.info("start to run toDetailRoom()");
		}
		
    	//站长统计
		CS cs = new CS(1260334725);
		cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		
    	try
    	{
        	//获取房源详情
			int houseId = Integer.parseInt(request.getParameter("houseId"));
			Map<String, Object> houseParam = new HashMap<String, Object>();
			houseParam.put("houseId", houseId);
			JSONObject houseDetailJson = HttpClientPostMethod.httpReqUrl(houseParam, appProperties.getRequestRoot(), "getHouseDetail");
			JSONObject dataObj = houseDetailJson.getJSONObject("data");
			
			//1.获取房源信息
			JSONObject houseObj = dataObj.getJSONObject("houseInfo");
			HouseDetailVo houseDetailVo = JSON.toJavaObject(houseObj, HouseDetailVo.class);
			if (houseDetailVo.getMarket_price()>0) {
				model.addAttribute("notNullMarketPrice", "1");
				float  discount = (float)houseDetailVo.getMemTotalAmt()*10/(float)houseDetailVo.getMarket_price();
				BigDecimal   b   =   new   BigDecimal(discount);  
				float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue(); 
				model.addAttribute("discount",f1);				
			}
			
			//合作关系
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("shopId", houseDetailVo.getShopId());
			JSONObject detailJson = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot()+"/customer", "getHouseShopByPar");
			String code = detailJson.getString("code");
			if (Contants.CODE_SUCCESSED.equals(code)) {
				JSONObject obj = houseDetailJson.getJSONObject("data");
				JSONObject data = obj.getJSONObject("shop");
				HouseShopVo houseShopVo = JSON.toJavaObject(data, HouseShopVo.class);
				if (PartnershipEnum.MemberInn.getCode().equals(houseShopVo.getPartnership())) {
					model.addAttribute("partnership", PartnershipEnum.MemberInn.getMessage());//会员
				}else if (PartnershipEnum.Holding.getCode().equals(houseShopVo.getPartnership())) {
					model.addAttribute("partnership", PartnershipEnum.Holding.getMessage());//控股
				}else if (PartnershipEnum.GuesthouseInn.getCode().equals(houseShopVo.getPartnership())) {
					model.addAttribute("partnership", PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
				}else if (PartnershipEnum.DepthCooperation.getCode().equals(houseShopVo.getPartnership())) {
					model.addAttribute("partnership", PartnershipEnum.DepthCooperation.getMessage());//深度合作
				}else if (PartnershipEnum.JointBusinessInn.getCode().equals(houseShopVo.getPartnership())) {
					model.addAttribute("partnership", PartnershipEnum.JointBusinessInn.getMessage());//联合运营
				}else {
					model.addAttribute("partnership", PartnershipEnum.MemberInn.getMessage());//会员
				}
			}else {
				model.addAttribute("partnership", PartnershipEnum.MemberInn.getMessage());//会员
			}
			
			//微信验证签名
			model = getVx(model, request, houseDetailVo.getHouseId());
			
			model.addAttribute("houseDetailVo", houseDetailVo);
			model.addAttribute("roomFacilities", getRoomFacilities(houseDetailVo));
			
			//2.获取房源标签名称
			model.addAttribute("tagNames", getTagList(houseDetailVo));
			
			//3.获取评论信息
			JSONObject commentObj = dataObj.getJSONObject("comment");
			HouseCommentVo commentVo = JSON.toJavaObject(commentObj, HouseCommentVo.class);
			model.addAttribute("commentVo", commentVo);
			
			//4.获取店铺信息
			JSONObject shopObj = dataObj.getJSONObject("shop");
			HouseShopVo shopVo = JSON.toJavaObject(shopObj, HouseShopVo.class);
			shopVo = console(shopVo);
			model.addAttribute("shopVo", shopVo);
			
			//合作关系标识
			String partnership = getPartnership(shopVo.getPartnership());
			model.addAttribute("partnership", partnership);
			model.addAttribute("other", getOther(houseDetailVo));
			
			//对图片按照类型进行排序
			Collections.sort(houseDetailVo.getImgList(), new Comparator<HouseImage>()
			{
				@Override
				public int compare(HouseImage o1, HouseImage o2)
				{
					return Integer.valueOf(o1.getImageType()).compareTo(Integer.valueOf(o2.getImageType()));
				}
				
			});
			
			
    		//5.若用户已登陆，则查询收藏状态
    		UserInfo loginUser = getLoginUserInfo(request);
        	if(loginUser != null)
        	{
        		String uuid = loginUser.getUid();
        		
        		RedisRequest redisRequest = new RedisRequest();
        		redisRequest.setKey("mmsf:collect:"+houseId + uuid);
        		String collectFlag = redisFacade.getValueByKey(redisRequest);
        		
//        		String collectFlag = RedisHelper.get(RedisConstants.COLLECT_KEY + houseId + uuid);
        		if (StringUtils.isNotEmpty(collectFlag))
        		{
					model.addAttribute("collectFlag", collectFlag);
				}
        		
        		model.addAttribute("isLogin", "1");
        	}
			
    	}
    	catch(Exception e)
    	{
    		logger.error("跳转房源详细信息异常", e);
    	}
    	model.addAttribute("imgurl", imgurl);
    	
    	return  Contants.URL_PREFIX + "/house/room-detail";
    	
    }
    
    private HouseShopVo console(HouseShopVo shopVo) {
		if (PartnershipEnum.MemberInn.getCode().equals(shopVo.getPartnership())) {
			shopVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
		}else if (PartnershipEnum.Holding.getCode().equals(shopVo.getPartnership())) {
			shopVo.setPartnership(PartnershipEnum.Holding.getMessage());//控股
		}else if (PartnershipEnum.GuesthouseInn.getCode().equals(shopVo.getPartnership())) {
			shopVo.setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
		}else if (PartnershipEnum.DepthCooperation.getCode().equals(shopVo.getPartnership())) {
			shopVo.setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
		}else {
			shopVo.setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
		}
		return shopVo;
	}

	private String getPartnership(String partnership) {
    	String ship = "";
    	if (PartnershipEnum.MemberInn.getCode().equals(partnership)) {
    		ship = PartnershipEnum.MemberInn.getMessage();//会员
		}else if (PartnershipEnum.Holding.getCode().equals(partnership)) {
			ship = PartnershipEnum.Holding.getMessage();//控股
		}else if (PartnershipEnum.GuesthouseInn.getCode().equals(partnership)) {
			ship = PartnershipEnum.GuesthouseInn.getMessage();//民宿贷客栈
		}else if (PartnershipEnum.DepthCooperation.getCode().equals(partnership)) {
			ship = PartnershipEnum.DepthCooperation.getMessage();//深度合作
		}else {
			ship = PartnershipEnum.MemberInn.getMessage();//会员
		}
		return ship;
	}

	public Model getVx(Model model,HttpServletRequest request,long houseId) {
    	
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
		String url = "http://m.mmsfang.com/house/toDetail.htm?" + request.getQueryString();
		logger.info("====生成URL:{" + url + "} queryString:{" + request.getQueryString() + "}");
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
	    model.addAttribute("noncestr", nonceStr);
	    model.addAttribute("signature", signature);
	    model.addAttribute("timestamp", timestampStr);
	    model.addAttribute("appId", appId);
	    model.addAttribute("url", url);
	    
	    return model;
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
	
    
    private String getOther(HouseDetailVo vo)
	{
    	if ((vo == null) || (vo.getOtherList() == null))
    	{
			return null;
		}
    	
    	Other other = vo.getOtherList();
    	StringBuffer sb = new StringBuffer();
    	if(other.getCook() == 1)
    	{
    		sb.append("可做饭  ");
    	}
    	if (other.getParty() == 1)
    	{
			sb.append("适合聚会  ");
		}
    	if (other.getSmoking() == 1)
    	{
    		sb.append("可吸烟  ");
		}
    	if (other.getPet() == 1)
    	{
    		sb.append("可带宠物  ");
		}
    	if (other.getExtrabed() == 1)
    	{
    		sb.append("可加床  ");
		}
    	if (other.getGuests() == 1)
    	{
    		sb.append("接待外宾  ");
		}
    	if (other.getBreakfast() == 1)
    	{
    		sb.append("提供早餐  ");
		}
    	if (other.getChildrenstay() == 1)
    	{
    		sb.append("欢迎孩子入住  ");
		}
    	
    	return sb.toString();
	}

	private List<String> getTagList(HouseDetailVo houseDetailVo)
	{
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey("mmsf:allHouseTag");
		String houseTag = redisFacade.getValueByKey(redisRequest);
		
//		String houseTag = RedisHelper.get(RedisConstants.HOUSE_TAG_KEY);
		List<Integer> houseTagId = houseDetailVo.getTagList();
		if ((houseTag != null) && CollectionUtils.isNotEmpty(houseTagId))
		{
			//只取前三个标签
			houseTag = "[" + houseTag + "]";
			JSONArray houseTags = JSONArray.fromObject(houseTag);
			List<String> tagNames = new ArrayList<String>();
			int tagSize = (houseTagId.size() > 3) ? 3 : houseTagId.size();
			for(int i = 0; i < tagSize; i++)
			{
				for(int y = 0 ; y < houseTags.size() ; y ++)
				{
					net.sf.json.JSONObject tagObject = (net.sf.json.JSONObject) houseTags
							.get(y);
					if (tagObject.getInt("id") == houseTagId.get(i)) 
					{
						tagNames.add(tagObject.getString("name"));
						break;
					}
		        }
			}
			
			return tagNames;
		}
		
		return null;
	}
    
    private List<RoomFacilityVo> getRoomFacilities(HouseDetailVo houseDetailVo){
    	if (houseDetailVo == null)
    	{
			return null;
		}
    	
    	List<RoomFacilityVo> vos = new ArrayList<RoomFacilityVo>();
    	Facilities facilities = houseDetailVo.getFacilitiesList();
    	if (facilities != null)
    	{
			if (facilities.getConditioner() == 1) {
				vos.add(new RoomFacilityVo("空调", "http://file.88mmmoney.com/bfff3ff6-d1c8-4ad1-9cb1-24a88a049c84"));
			}
			if (facilities.getWashing() == 1) {
				vos.add(new RoomFacilityVo("洗衣机", "http://file.88mmmoney.com/e76ced03-fc50-4356-80fb-dfbc8b05edd2"));
			}
			if (facilities.getFridge() == 1) {
				vos.add(new RoomFacilityVo("冰箱", "http://file.88mmmoney.com/ce2cb645-0d8f-46e8-b99a-7b24863f336a"));
			}
			if (facilities.getDrinking() == 1) {
				vos.add(new RoomFacilityVo("饮水机", "http://file.88mmmoney.com/ba56223e-3e8a-48f4-aa9b-34159438cea3"));
			}
			if (facilities.getTv() == 1) {
				vos.add(new RoomFacilityVo("电视机", "http://file.88mmmoney.com/0d9009f3-1a13-4125-b255-794c3c500b7f"));
			}
			if (facilities.getTowels() == 1) {
				vos.add(new RoomFacilityVo("毛巾", "http://file.88mmmoney.com/2c90c0fb-a781-4dc5-8992-25baeb162bbe"));
			}
			if (facilities.getTooth() == 1) {
				vos.add(new RoomFacilityVo("牙具", "http://file.88mmmoney.com/775edd8a-5e1a-4376-8406-8af9367625c3"));
			}
			if (facilities.getSlipper() == 1) {
				vos.add(new RoomFacilityVo("拖鞋", "http://file.88mmmoney.com/47854342-b508-42da-8299-9c200a65bfb2"));
			}
			if (facilities.getShampoo() == 1) {
				vos.add(new RoomFacilityVo("洗发/沐浴露", "http://file.88mmmoney.com/a8b35256-33a8-4f65-8e43-28157cefffe1"));
			}
			if (facilities.getHairdrier() == 1) {
				vos.add(new RoomFacilityVo("吹风机", "http://file.88mmmoney.com/9b5e5e11-eba5-4d61-bf09-ab6d603d43aa"));
			}
			if (facilities.getShower() == 1) {
				vos.add(new RoomFacilityVo("浴缸", "http://file.88mmmoney.com/7a8787b6-dac6-4c34-bfc2-34785d429d75"));
			}
			if (facilities.getHeater() == 1) {
				vos.add(new RoomFacilityVo("热水器", "http://file.88mmmoney.com/ce3acf2e-8917-4b73-8920-4b7f2bd4f5cb"));
			}
			if (facilities.getDryer() == 1) {
				vos.add(new RoomFacilityVo("烘干机", "http://file.88mmmoney.com/db689372-18b7-41e5-967a-5166c7e31ad6"));
			}
			if (facilities.getSmokedetector() == 1) {
				vos.add(new RoomFacilityVo("烟雾探测器", "http://file.88mmmoney.com/840e88a5-6eda-43cd-a0d6-651c716171f0"));
			}
			if (facilities.getHeating() == 1) {
				vos.add(new RoomFacilityVo("暖气", "http://file.88mmmoney.com/2cb2bcf4-b821-49fa-95f5-7affd97d4967"));
			}
			if (facilities.getExtinguisher() == 1) {
				vos.add(new RoomFacilityVo("灭火器", "http://file.88mmmoney.com/ff5cf078-915f-45c7-8a68-b1cd69b96b60"));
			}
			if (facilities.getAidkit() == 1) {
				vos.add(new RoomFacilityVo("急救包", "http://file.88mmmoney.com/719a7fd6-657a-4745-af1f-da4e9684d468"));
			}
			if (facilities.getHotpot() == 1) {
				vos.add(new RoomFacilityVo("热水壶", "http://file.88mmmoney.com/88cd0f14-cc1b-48a8-95b5-8a9a5222c5d2"));
			}
		}
    	
    	Supporting supporting = houseDetailVo.getSupportingList();
    	if (supporting != null)
    	{
			if (supporting.getWifi() == 1) {
				vos.add(new RoomFacilityVo("wifi", "http://file.88mmmoney.com/55a8d84c-9110-4faa-b11e-257d0e48e15f"));
			}
			if (supporting.getBroadband() == 1) {
				vos.add(new RoomFacilityVo("宽带", "http://file.88mmmoney.com/47b43e0d-dfb9-4a95-ba61-afa274cc45ea"));
			}
			if (supporting.getElevator() == 1) {
				vos.add(new RoomFacilityVo("电梯", "http://file.88mmmoney.com/b549dde2-287d-4d3e-a229-2b3476c503b2"));
			}
			if (supporting.getSwimming() == 1) {
				vos.add(new RoomFacilityVo("游泳池", "http://file.88mmmoney.com/1d3ebf50-e208-4845-86ea-e15e6e43d10e"));
			}
			if (supporting.getAccesscard() == 1) {
				vos.add(new RoomFacilityVo("门禁卡", "http://file.88mmmoney.com/ebea6620-8a2d-4428-95dc-45ac832578c9"));
			}
			if (supporting.getSecuritystaff() == 1) {
				vos.add(new RoomFacilityVo("保安", "http://file.88mmmoney.com/86971bf6-7aeb-45cc-9224-575f57366901"));
			}
			if (supporting.getStore() == 1) {
				vos.add(new RoomFacilityVo("便利店", "http://file.88mmmoney.com/538cce2f-e945-42d6-98d2-89f0570ebbf5"));
			}
			if (supporting.getParking() == 1) {
				vos.add(new RoomFacilityVo("停车位", "http://file.88mmmoney.com/b202a115-f396-4470-bfce-7c51e1c33ae8"));
			}
			if (supporting.getGym() == 1) {
				vos.add(new RoomFacilityVo("健身房", "http://file.88mmmoney.com/caef8550-116f-49fa-b57b-98e579910d00"));
			}
			if (supporting.getPlayground() == 1) {
				vos.add(new RoomFacilityVo("儿童乐园", "http://file.88mmmoney.com/81179342-41ff-4430-b264-cdb6b7474185"));
			}
			if (supporting.getWheelchair() == 1) {
				vos.add(new RoomFacilityVo("无障碍设施", "http://file.88mmmoney.com/def3f9e0-1aa8-49ef-be32-720f7450dd99"));
			}
			if (supporting.getBuzzer() == 1) {
				vos.add(new RoomFacilityVo("无线对讲机", "http://file.88mmmoney.com/1bdb461d-0a07-4f98-af75-9b060c2f782e"));
			}
		}
    	
		return vos;
	}

	/**
     * 房源详情--》全部评价入口
     * @param houseId
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/house/getAllComments")
    public String getAllComments(int houseId, int page, Model model){
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to getAllComments, houseId=", houseId);
		}
    	
    	try 
    	{
    		//查询房源评论概况
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("houseId", houseId);
    		JSONObject commentsResult = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getHouseComments");
    		String commentsCode = commentsResult.getString("code");
    		if("0".equals(commentsCode))
    		{
    			String data = commentsResult.getString("data"); 
    			HouseCommentVo vo = JSONObject.parseObject(data).getObject("result", HouseCommentVo.class);
    			model.addAttribute("summary", vo);
    		}
    		else
    		{
    			logger.error("failed to getAllComments,error msg is {}.", commentsResult.getString("msg"));
			}
    		
    		//分页查询评论列表
    		getCommentsByPage(houseId, page, model);
			
		}
    	catch (Exception e) 
		{
			logger.error("failed to getAllComments", e);
		}
    	
    	return Contants.URL_PREFIX + "/house/comment-list";
    }

    @RequestMapping(value = "/house/getCommentsByPage")
    @ResponseBody
	public HashMap<String, Object> getCommentsByPage(int houseId, int page, Model model){
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to getCommentsByPage, houseId={},page={}.", houseId, page);
		}
    	
		List<OrderCommentVo> orderCommentList = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try
    	{
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("houseId", houseId);
    		
    		param.put("sort", 1);//按推荐时间、评论时间排序
    		param.put("status", "-1");//查询所有状态的评论
    		param.put("currentPage", page);
    		JSONObject result = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getOrderComment");
    		String code = result.getString("code");
    		if("0".equals(code))
    		{
    			net.sf.json.JSONObject jsonData= net.sf.json.JSONObject.fromObject(result.get("data"));
    			net.sf.json.JSONObject pageJson = jsonData.getJSONObject("page");
    			orderCommentList = new ArrayList<OrderCommentVo>();
    			JSONArray resultJson = pageJson.getJSONArray("sourceList");
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			for(int i=0 ; i<resultJson.size(); i++){
    				net.sf.json.JSONObject jsonObject = resultJson.getJSONObject(i);
    				if (jsonObject.getInt("status") == 2) {
    					logger.warn("ignored invalid comments, status=2,id={}.", jsonObject.getString("id"));
						continue;
					}
    				
    				OrderCommentVo orderComment = new OrderCommentVo();
    				
    				//隐藏号码中间四位
    				orderComment.setId(jsonObject.getString("id"));
    				String userPhone = jsonObject.getString("userPhone");
    				orderComment.setUserPhone(userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
    				orderComment.setScore(jsonObject.getInt("score"));
    				orderComment.setComments(jsonObject.getString("comments"));
    				Date date = new Date(jsonObject.getLong("createTime"));
    				orderComment.setCreateTime(sdf.format(date));
    				orderComment.setRecommendTime(jsonObject.getLong("recommendTime"));
    				JSONArray imageaArray = jsonObject.getJSONArray("images");
    				int imageSize = imageaArray.size();
    				String[] images = new String[imageSize];
    				for(int j = 0; j < imageSize; j++)
    				{
    					images[j] = imageaArray.getString(j);
    				}
    				
    				orderComment.setImages(images);
    				
    				orderCommentList.add(orderComment);
    				
    				model.addAttribute("results", orderCommentList);
    			}
    		}
    		else
    		{
    			logger.error("failed to getCommentsByPage,error msg is {}.", result.getString("msg"));
			}
    		
    		resultMap.put("code", code);
    		resultMap.put("msg", result.getString("msg"));
    		resultMap.put("results", orderCommentList);
			
		}
    	catch (Exception e) 
		{
    		resultMap.put("code", 2);
    		resultMap.put("msg", "查询失败");
    		logger.error("failed to getCommentsByPage", e);
		}
		
		return resultMap;
	}
    
    @RequestMapping(value = "/house/toComment")
    public String toComment(Integer houseId, Integer orderId, Model model)
    {
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to run toComment(), houseId={},orderId={}.", houseId, orderId);
		}
    	
    	try
    	{
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("houseId", houseId);
    		JSONObject commentsResult =HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getHouseComments");
    		String code = commentsResult.getString("code");
    		if("0".equals(code))
    		{
    			String data = commentsResult.getString("data"); 
    			HouseCommentVo vo = JSONObject.parseObject(data).getObject("result", HouseCommentVo.class);
    			if (vo == null) {
					vo = new HouseCommentVo();
					vo.setHouseId(houseId);
				}
    			param.put("houseIds", houseId.toString());
    			JSONObject houseResult = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(),"getHousesSearch");
    			if ((houseResult != null) && ("0".equals(houseResult.getString("code")))) 
    			{
    				String houses = houseResult.getString("data");
    				List pos = JSONObject.parseObject(houses).getObject("list", List.class);
    				String house = JSONObject.toJSONString(pos.get(0));
    				String imageUrl = JSONObject.parseObject(house).getString("imageUrl");
    				String houseName = JSONObject.parseObject(house).getString("name");
    				vo.setImgUrl(imageUrl.split(",")[0]);
    				vo.setHouseName(houseName);
    			}
    			else
    			{
    				logger.error("failed to getCommentsByPage,error msg is {}.", houseResult.getString("msg"));
				}
    			
    			model.addAttribute("result", vo);
    			model.addAttribute("orderId", orderId);
    		}
    		else
    		{
    			logger.error("failed to getCommentsByPage,error msg is {}.", commentsResult.getString("msg"));
			}
    		
		}
    	catch (Exception e) 
		{
    		logger.error("failed to run toComment()", e);
		}
    	
    	return Contants.URL_PREFIX + "/house/toComment";
    }
    
    @RequestMapping(value = "/house/addComment", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addComment(HttpServletRequest request, OrderCommentVo vo)
    {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try 
    	{
    		if (logger.isInfoEnabled())
    		{
				logger.info("start to add comment, orderId={}.", vo.getOrderId());
			}
    		
    		//校验评分
    		if (vo.getScore() <= 0) 
    		{
    			resultMap.put("code", 2);
        		resultMap.put("msg", "请评分");
        		return resultMap;
			}
    		
    		//校验评论内容
    		if (StringUtils.isEmpty(vo.getComments()))
    		{
    			resultMap.put("code", 2);
        		resultMap.put("msg", "请填写评论内容");
        		return resultMap;
			}
    		
    		//1.获取登陆用户信息0
    		UserInfo loginUser = getLoginUserInfo(request);
    		vo.setUserPhone(loginUser.getName());
    		vo.setUid(loginUser.getUid());
    		vo.setCreateTime(String.valueOf(new Date().getTime()));
    		
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("orderComment", vo);
    		JSONObject result = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "addOrderComment");
    		if ("0".equals(result.getString("code"))) {
    			param.put("status", 17);
    			param.put("orderId", vo.getOrderId());
    			JSONObject updateResult = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot() + "/merchant", "updateOrderStatus");
    			resultMap.put("code", updateResult.getShort("code"));
    			resultMap.put("msg", updateResult.get("msg"));
			}
    		else
    		{
    			resultMap.put("code", result.getShort("code"));
    			resultMap.put("msg", result.get("msg"));
			}
		} 
    	catch (Exception e) 
    	{
    		resultMap.put("code", 2);
    		resultMap.put("msg", "添加失败");
    		
    		logger.error("failed to add comment", e);
		}
    	
    	return resultMap;
    }
    
    @RequestMapping(value = "/house/importCommentImg")
    @ResponseBody
    public Map<String, Object> importCommentImg(HttpServletRequest request)
	{
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to run importCommentImg()");
		}
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) 
		{
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("imgfile");
			int size = iter.size();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("recieved request to upload images, count is {}.", size);
			}
			
			for (int i = 0; i < size; i++)
			{
				String url = appProperties.getUpload_house_url();
				HessianProxyFactory factory = new HessianProxyFactory();
				try
				{
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());

					FileUploadResponse fileResponse = facade.uploadFile(req);
					
					String imgUrl = fileResponse.getFileUrl();
					if (logger.isDebugEnabled())
					{
						logger.debug("upload image successfully, url is {}.", imgUrl);
					}
					
					if (StringUtils.isNotEmpty(imgUrl))
					{
						resultMap.put("url", imgUrl);
					}
					
				}
				catch (Exception e) 
				{
					logger.error("failed to upload the imags.{}", e);
				}

			}
		}
		
		return resultMap;
	}
    
    @RequestMapping(value = "/house/comment-success")
    public String commentSuccess(int houseId, Model model)
    {
    	model.addAttribute("houseId", houseId);
    	return Contants.URL_PREFIX + "/house/comment-success";
    }
    
    @RequestMapping(value = "/house/room-map")
    public String toMap(HttpServletRequest request, String name, double longitude, double latitude,String address, Model model)
    {
    	try 
    	{
			model.addAttribute("name", URLDecoder.decode(request.getParameter("name"), "UTF-8"));
			model.addAttribute("address", URLDecoder.decode(request.getParameter("address"), "UTF-8"));
		} 
    	catch (UnsupportedEncodingException e)
    	{
			e.printStackTrace();
		}
    	model.addAttribute("longitude", longitude);
    	model.addAttribute("latitude", latitude);
    	
    	return Contants.URL_PREFIX + "/house/room-map";
    }
    
	@RequestMapping(value = "/house/searchResult", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<KWSearchResultEntityH5> searchByKeyword(HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		
		logger.info("start to run searchByKeyword(),keyword:{}."+keyword); 
		if(StringUtils.isEmpty(keyword))
		{
			return null;
		}
		
		try 
		{
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			postDataMap.put("keyword", keyword);
			postDataMap.put("type", "H5");
			Response<KWSearchResultEntityH5> resultResponse = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "kwSearch", KWSearchResultEntityH5.class);
			if(resultResponse.getCode().equals("0"))
			{
				KWSearchResultEntityH5 entity = resultResponse.getData();
				return new ResponseOut<KWSearchResultEntityH5>(Contants.SUCCESSED, "", entity);
			}
			else
			{
				return new ResponseOut<KWSearchResultEntityH5>(Contants.FAILED, resultResponse.getMsg());
			}
			
		} 
		catch (Exception e)
		{
			logger.error("Failed to search by keyword", e);
		}
		
		return new ResponseOut<KWSearchResultEntityH5>(Contants.FAILED, "未知异常");
	}
	
	@RequestMapping(value = "/house/searchHouseList", method = { RequestMethod.POST,RequestMethod.GET})
	public String searchHouseList(HttpServletRequest request, HttpServletResponse response, Model model){
		logger.info("start to run searchHouseList(),cityId:{}.", request.getParameter("cityId"));
		//站长统计
		CS cs = new CS(1260334725);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		try 
		{
			HouseSearchConditionVo conditionVo = new HouseSearchConditionVo();
			String cityId = request.getParameter("cityId");
			String checkinDate = request.getParameter("checkinDate"); //时间戳
			String checkoutDate = request.getParameter("checkoutDate");//时间戳
			String cityName = request.getParameter("name");//城市名
			if(null==cityName||"".equals(cityName)){
				Map<String, Object> postData = new HashMap<String, Object>();
				postData.put("cityId", cityId);
				JSONObject result=	HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot()+"/customer","getCity");
				String msg=result.get("msg").toString();
				String code=result.getString("code");
	    		Object object = result.get("data");
	    		if ("0".equals(code)) {
	    			net.sf.json.JSONObject Object = net.sf.json.JSONObject.fromObject(object);
	    			List<City> cityList = new ArrayList<City>();
	    			cityList = (List)JSONArray.toList(Object.getJSONArray("cityList"), City.class);
	    			cityName = cityList.get(0).getName().toString();
	    		}else {
					logger.error("按cityid查询城市名称异常!");
				}
			}
			
		    //按城市ID查询redis中商圈
		    RedisRequest redisReq = new RedisRequest();
		    redisReq.setKey("mmsf:tradeArea:"+cityId);
			String tradeArea = "["+redisFacade.getValueByKey(redisReq)+"]";
			if (null!= redisFacade.getValueByKey(redisReq) && !"".equals(redisFacade.getValueByKey(redisReq)) 
					&& !"null".equals(redisFacade.getValueByKey(redisReq)) && !"{}".equals(redisFacade.getValueByKey(redisReq))) {
				JSONArray tradeArray = JSONArray.fromObject(tradeArea);
				List<TradeArea> tradeList = getTradeList(tradeArray);
				model.addAttribute("tradeList", tradeList);
			}
			
			RedisRequest reqRe = new RedisRequest();
			reqRe.setKey("mmsf:houseTag");
			String tagStr = "["+redisFacade.getValueByKey(reqRe)+"]";
			if (null!= redisFacade.getValueByKey(reqRe) && !"".equals(redisFacade.getValueByKey(reqRe)) 
					&& !"null".equals(redisFacade.getValueByKey(reqRe)) && !"{}".equals(redisFacade.getValueByKey(reqRe))) {
				JSONArray tagArray = JSONArray.fromObject(tagStr);
				List<HouseTag> tagList = getTagList(tagArray);
				setTagList(tagList,model);

			}else {
				Map<String, Object> postData = new HashMap<String, Object>();
				postData.put("name", cityName);
				JSONObject resObject = HttpClientPostMethod.httpDataReqUrl(postData, appProperties.getRequestRoot(), "putTagsToRedis");
				String codes = resObject.getString("code");
				if ("0".equals(codes)) {
					if (null!= redisFacade.getValueByKey(reqRe) && !"".equals(redisFacade.getValueByKey(reqRe)) 
							&& !"null".equals(redisFacade.getValueByKey(reqRe)) && !"{}".equals(redisFacade.getValueByKey(reqRe))) {
						JSONArray tagArray = JSONArray.fromObject(tagStr);
						List<HouseTag> tagList = getTagList(tagArray);
						setTagList(tagList,model);

					}
					
				}
			}
			
			conditionVo.setCityId(Integer.parseInt(cityId));
			conditionVo.setCheckinDate(checkinDate);
			conditionVo.setCheckoutDate(checkoutDate);
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			postDataMap.put("condition", conditionVo);
			Response<HousePagerEntity> resultResponse = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "appHouseSearch", HousePagerEntity.class);
			List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
			if(resultResponse.getCode().equals("0")){
				HousePagerEntity entity = resultResponse.getData();
				List<HouseEntity> list = entity.getHouses();
				for (HouseEntity houseEntity:list) {
					HouseInfo houseInfo = new HouseInfo();
					houseInfo.setHouseId(houseEntity.getHouseId());					
					String[] introductionImg = houseEntity.getImgUrl().split(",");
					houseInfo.setIntroductionImg(introductionImg);
					houseInfo.setHouseName(houseEntity.getHouseName());
					houseInfo.setMemTotalAmt(houseEntity.getPrice());  //会员价
					List<Integer> tagList = houseEntity.getTagList();
				    List<String> nameList = new ArrayList<String>();
	    			RedisRequest redisRequest = new RedisRequest();
	    			redisRequest.setKey("mmsf:allHouseTag");
	    			String houseTag = "["+redisFacade.getValueByKey(redisRequest)+"]";
	    			JSONArray jArray = JSONArray.fromObject(houseTag);
					for (int i = 0; i < jArray.size(); i++) {
						net.sf.json.JSONObject jObject = jArray.getJSONObject(i);
						for (int j = 0; j < tagList.size(); j++) {
							int tagId = tagList.get(j);
							if (jObject.getString("id").equals(String.valueOf(tagId))) {
								nameList.add(jObject.getString("name"));
							}
						}
					}
					if (nameList.size()>3) {
						nameList = nameList.subList(0, 3);
					}
					houseInfo.setTagNameList(nameList);
					
					if(getMemberInfo(request.getSession()) != null) { //判断是否登陆
						String memberId = getMemberInfo(request.getSession()).getMemberId();
						
		        		RedisRequest req = new RedisRequest();
	            		req.setKey("mmsf:collect:"+houseInfo.getHouseId()+memberId);		            		
	            		
						if(redisFacade.getValueByKey(req) != null) {
							houseInfo.setCollectFlag(1);
						}
					}	
					
					houseInfoList.add(houseInfo);
				}
				model.addAttribute("houseInfoList", houseInfoList);
				model.addAttribute("cityId", cityId);
				model.addAttribute("pageNum",1);
				model.addAttribute("cityName", cityName);
				//判断是否登陆
				setLoginStatus(request, model);
				
				return Contants.URL_PREFIX + "/house/HouseList";
			}else{
				return Contants.URL_PREFIX + "/house/HouseList";
			}
			
		} 
		catch (Exception e)
		{
			logger.error("Failed to ssearchHouseList", e);
		}
		logger.info("finish to run searchHouseList();");
		return null;
	}
	
	private void setLoginStatus(HttpServletRequest request, Model model)
	{
		int isLogin = 0;
		try 
		{
			UserInfo loginUser = getLoginUserInfo(request);
			isLogin = (loginUser.getUid() != null) ? 1 : 0;
			
		} 
		catch (Exception e)
		{
			logger.warn("failed to get loginStatus", e);
			isLogin = 0;
		}
		
		model.addAttribute("isLogin", isLogin);
	}
	
    private List<TradeArea> getTradeList(JSONArray tradeArray) {
    	List<TradeArea> tradeList = new ArrayList<TradeArea>();
    	for (int i = 0; i < tradeArray.size(); i++) {
    		TradeArea tradeArea = new TradeArea();
    		net.sf.json.JSONObject jObject = tradeArray.getJSONObject(i);
    		tradeArea.setId(Integer.parseInt(jObject.getString("id")));
    		tradeArea.setName(jObject.getString("name"));
    		tradeList.add(tradeArea);
    	}
	    return tradeList;
    }
	
    private List<HouseTag> getTagList(JSONArray tagArray) {
    	List<HouseTag> tagList = new ArrayList<HouseTag>();
    	for (int i = 0; i < tagArray.size(); i++) {
    		HouseTag houseTag = new HouseTag();
    		net.sf.json.JSONObject jObject = tagArray.getJSONObject(i);
    		houseTag.setId(Integer.parseInt(jObject.getString("id")));
    		houseTag.setName(jObject.getString("name"));
    		tagList.add(houseTag);
    	}
	    return tagList;
    }
    
	   private void setTagList(List<HouseTag> tagList, Model model) {
		   if (tagList.size()>6) {
				if (tagList.size()>=9) {
					model.addAttribute("tagList3", tagList.subList(6, 9));
				}else {
					model.addAttribute("tagList3", tagList.subList(6, tagList.size()));
				}
			} 
			if (tagList.size()>3) {
				if (tagList.size()>=6) {
					model.addAttribute("tagList2", tagList.subList(3, 6));
				}else {
					model.addAttribute("tagList2", tagList.subList(3, tagList.size()));
				}
			}
			if (tagList.size()>0){
				if (tagList.size()>=3) {
					model.addAttribute("tagList1", tagList.subList(0, 3));
				}else {
					model.addAttribute("tagList1", tagList.subList(0, tagList.size()));
				}
			}
		
	}
	   
}