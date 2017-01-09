package com.mmzb.house.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.RewardFacade;
import com.mmzb.charge.facade.entity.ChargeRequest;
import com.mmzb.charge.facade.entity.ChargeResponse;
import com.mmzb.charge.facade.entity.RewardCalRequest;
import com.mmzb.charge.facade.entity.RewardCalResponse;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.house.web.action.base.ApplicationConstValue;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.RechargeBean;
import com.mmzb.house.web.action.dto.UserCardInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.SmsUtil;
import com.mmzb.house.web.common.resolver.GerneralMethod;
import com.mmzb.house.web.constant.DataResponse;
import com.mmzb.house.web.core.common.RestResponse;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientGetMethod;

@Controller
public class CardAction extends BaseAction {

	
	@Resource(name="appProperties")
	private AppProperties appProperties;
	private static Logger logger = LoggerFactory.getLogger(CardAction.class);
	
	 @Autowired
	 public GerneralMethod gerneralMethod;
	 
	/**
		 * chenmeiyang
		 * 线上充值成功
	*/
	@RequestMapping(value = "/my/recharge_success.htm", method = { RequestMethod.GET })
	public ModelAndView success(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		RechargeBean rechargeBean= super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, RechargeBean.class);
		UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		//支付系统发送短信
		/*try{
		   //发送充值成功短信给用户
           SmsUtil.sendSms(loginUser.getPhone(), SmsUtil.genRechargeSms(loginUser.getPhone(), rechargeBean.getMoney(), rechargeBean.getRewardMoney()));
		}catch(Exception e){
			logger.error("客户发送短信异常",e);
		}*/
        model.addAttribute("rechargeBean",rechargeBean);
		return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge_success");
	}
	 
	 
	/**
		 * chenmeiyang
		 * 用户点击  ”微信支付“ 按钮 ，执行充值动作
     */
	@RequestMapping(value = "/my/recharge_process.htm", method = { RequestMethod.POST})
	public @ResponseBody DataResponse  charge(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		         
		try{
			    UserInfo loginUser = super.getJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
				//1、基本请求参数校验
				String  money          =  request.getParameter("money"); //金额
				String  productCode    =  request.getParameter("productCode");//支付方式编码
				String  openid         =  request.getParameter("jsopenid");//微信ID
				String  uid            =  loginUser.getUid();//会员编号
				
				logger.info("============money=="+money+"====openid===="+openid+"===uid=="+uid);
				
				
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
		 * 线上充值
	*/
	@RequestMapping(value = "/my/recharge_online.htm", method = { RequestMethod.GET })
	public ModelAndView online(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
    	//获取微信code
    	String weixinCode = request.getParameter("code");
    	
    	if(null == weixinCode){//获取用户的微信code失败，重新获取
    		return new ModelAndView("redirect:/my/recharge_online_wx.htm");
    	}
    	
    	RechargeBean rechargeBean= super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, RechargeBean.class);
    	
    	try {
    		JSONObject result = HttpClientGetMethod.httpReqUrl(null, weixinCode);
    		logger.info("=========获取微信code的相应内容为======微信code为====="+weixinCode +"=====结果集为=="+result);
    		
    		String  openid = result.getString("openid");
    		model.addAttribute("openid", openid);
    		model.addAttribute("rechargeBean", rechargeBean);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("===========获取微信Code异常=============",e);
		}
		return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge_online");
	}
	 
	/**
		 * chenmeiyang
		 * 线上充值
	*/
	@RequestMapping(value = "/my/recharge_online_wx.htm", method = { RequestMethod.GET })
	public ModelAndView onlineWx(HttpServletRequest request,HttpServletResponse response, ModelMap model) {

			return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge_online_wx");
	}
	 

	/**
	 * chenmeiyang
	 * 线下充值
	 */
	@RequestMapping(value = "/recharge_offline.htm", method = { RequestMethod.GET })
	public ModelAndView offline(HttpServletRequest request,HttpServletResponse response, ModelMap model) {

		return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge_offline");
	}

	@RequestMapping(value = "/my/myvip.htm", method = { RequestMethod.GET })
	public ModelAndView changepwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
		UserCardInfo uci = new UserCardInfo();
		try {
			uci = gerneralMethod.getUserCardInfo(loginUser.getUid());
		} catch (Exception e) {
			logger.error("个人资料异常",e);
		}
		
		map.put("userInfo", loginUser);
		map.put("userCardInfo", uci);
		RestResponse restP = new RestResponse();
		restP.setData(map);
		return new ModelAndView(Contants.URL_PREFIX + "/card/myvip", "response",restP);
	}

	@RequestMapping(value = "/my/uplevel.htm", method = { RequestMethod.GET })
	public ModelAndView cardC(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		String uid = loginUser.getUid();
		Map<String,Object> res = gerneralMethod.getLevel(uid);
		int amt = (Integer)res.get("amt");
		int level = (Integer)res.get("level");
		//yunyou
		if(level< 5){
			int yunyouReward = Integer.parseInt(gerneralMethod.calcurateReword(uid, ApplicationConstValue.YUNYOUKA_BASE_AMT-amt).getRewardMoney());
			res.put("yunyouReward", yunyouReward);
			res.put("yunyouAmt", ApplicationConstValue.YUNYOUKA_BASE_AMT-amt);
			res.put("yunyouPrice", ApplicationConstValue.YUNYOUKA_BASE_PRICE);
			res.put("yunyouCount", yunyouReward/ApplicationConstValue.YUNYOUKA_BASE_PRICE);
		}
		//huanju
		if(level< 4){
			int huanjuReward = Integer.parseInt(gerneralMethod.calcurateReword(uid, ApplicationConstValue.HUANJUKA_BASE_AMT-amt).getRewardMoney());
			res.put("huanjuReward", huanjuReward);
			res.put("huanjuAmt", ApplicationConstValue.HUANJUKA_BASE_AMT-amt);
			res.put("huanjuPrice", ApplicationConstValue.HUANJUKA_BASE_PRICE);
			res.put("huanjuCount", huanjuReward/ApplicationConstValue.HUANJUKA_BASE_PRICE);
		}
		//tianmi
		if(level< 3){
			int tianmiReward = Integer.parseInt(gerneralMethod.calcurateReword(uid, ApplicationConstValue.TIANMIKA_BASE_AMT-amt).getRewardMoney());
			res.put("tianmiReward", tianmiReward);
			res.put("tianmiAmt", ApplicationConstValue.TIANMIKA_BASE_AMT-amt);
			res.put("tianmiPrice", ApplicationConstValue.TIANMIKA_BASE_PRICE);
			res.put("tianmiCount", tianmiReward/ApplicationConstValue.TIANMIKA_BASE_PRICE);
		}
		//xiaoyao
		if(level< 2){
			int xiaoyaoReward = Integer.parseInt(gerneralMethod.calcurateReword(uid, ApplicationConstValue.XIAOYAOKA_BASE_AMT-amt).getRewardMoney());
			res.put("xiaoyaoReward", xiaoyaoReward);
			res.put("xiaoyaoAmt", ApplicationConstValue.XIAOYAOKA_BASE_AMT-amt);
			res.put("xiaoyaoPrice", ApplicationConstValue.XIAOYAOKA_BASE_PRICE);
			res.put("xiaoyaoCount", xiaoyaoReward/ApplicationConstValue.XIAOYAOKA_BASE_PRICE);
		}
		model.addAttribute("map", res);
		return new ModelAndView(Contants.URL_PREFIX + "/card/uplevel");
	}
	
	@RequestMapping(value = "/renwoxing.htm", method = { RequestMethod.GET })
	public ModelAndView renwoxing(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		 if(null != loginUser) model.put("isLogin", 1);
		return new ModelAndView(Contants.URL_PREFIX + "/card/renwoxing");
	}
	
	

	@RequestMapping(value = "/my/recharge_index.htm", method = { RequestMethod.GET })
	public ModelAndView hotline(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		model.addAttribute("amt", null == request.getParameter("amt") ? "0": request.getParameter("amt"));
		return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge_index");
	}
	
	
	
	@RequestMapping(value = "/vip-buy.htm", method = { RequestMethod.GET })
	public ModelAndView vipBuy(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/card/vip-buy");
	}

	@RequestMapping(value = "/vip-consume.htm", method = { RequestMethod.GET })
	public ModelAndView vipConsumer(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/card/vip-consume");
	}

	@RequestMapping(value = "/vip-share.htm", method = { RequestMethod.GET })
	public ModelAndView vipShare(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/card/vip-share");
	}

	@RequestMapping(value = "/vip-trans.htm", method = { RequestMethod.GET })
	public ModelAndView vipTrans(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return new ModelAndView(Contants.URL_PREFIX + "/card/vip-trans");
	}
	
	@RequestMapping(value = "/recharge-detail.htm", method = { RequestMethod.GET })
    public ModelAndView rechargeDetail(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
   		return new ModelAndView(Contants.URL_PREFIX + "/recharge/recharge-detail");
    }
	
	@RequestMapping(value = "/my/calcurateReword.htm", method = { RequestMethod.GET })
    public void calcurateReword(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		UserInfo loginUser = super.getJsonAttribute(request.getSession(),
                Contants.SESSION_ATTR_NAME_CURRENT_USER, UserInfo.class);
		//参数money
		String money = request.getParameter("money");
		net.sf.json.JSONObject resJson = new net.sf.json.JSONObject(); 
		try{
			money = "".equals(money) || null == money ? "0":money;
			RewardCalResponse rewardCalResponse = gerneralMethod.calcurateReword(loginUser.getUid(), Integer.parseInt(money));
		 	logger.info("支付系统返回结果:"+rewardCalResponse);
		 	if(rewardCalResponse.isSuccess()){
		 		String totalVirtual = rewardCalResponse.getTotalVirtual();
		 		String rewardMoney = rewardCalResponse.getRewardMoney();
		 		String totalRewardMoney = rewardCalResponse.getTotalRewardMoney();
		 		String totalAccount = rewardCalResponse.getTotalAccount();
		 		RechargeBean rechargeBean = new RechargeBean();
		 		rechargeBean.setMoney(money);
		 		rechargeBean.setRewardMoney(rewardMoney);
		 		rechargeBean.setTotalAccount(totalAccount);
		 		rechargeBean.setTotalVirtual(totalVirtual);
		 		rechargeBean.setTotalRewardMoney(totalRewardMoney);
		 		resJson.put("totalVirtual", totalVirtual);
		 		resJson.put("rewardMoney", rewardMoney);
		 		resJson.put("totalRewardMoney", totalRewardMoney);
		 		resJson.put("totalAccount", totalAccount);
		 		setJsonAttribute(request.getSession(), Contants.SESSION_ATTR_NAME_CURRENT_RECHARGE, rechargeBean);
		 	}
			//返回前端
		 	response.setContentType("json,charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(resJson.toString());
			response.getWriter().flush();
		}catch(Exception e){
			logger.error("计算奖励异常",e);
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
	
}
