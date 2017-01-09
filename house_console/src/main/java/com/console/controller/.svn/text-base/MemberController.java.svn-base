package com.console.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.console.dto.BuildingsDto;
import com.console.dto.CityInfo;
import com.console.dto.RechargeBean;
import com.console.entity.TSysUser;
import com.console.framework.dao.Pagination;
import com.console.service.MemberService;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.JsonGeneratorUtils;
import com.console.util.MsgPropertiesUtils;
import com.console.util.RestResponse;
import com.console.util.StringUtil;
import com.console.vo.CardInfoVO;
import com.console.vo.CollectInfoVO;
import com.console.vo.FlowInfoVO;
import com.console.vo.MemberVO;
import com.console.vo.OrderInfoVO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.RewardFacade;
import com.mmzb.charge.facade.VirtualFacade;
import com.mmzb.charge.facade.entity.OfflineChargeRequest;
import com.mmzb.charge.facade.entity.OfflineChargeResponse;
import com.mmzb.charge.facade.entity.RewardCalRequest;
import com.mmzb.charge.facade.entity.RewardCalResponse;
import com.mmzb.charge.facade.entity.VirtualFlowEntity;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;



@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	public static Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;


	/**
	 * 查询所有会员信息
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/toMemberList")
	public ModelAndView toMemberList(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> phoneData = new HashMap<String, Object>();
		List<MemberVO> memberList = new ArrayList<MemberVO>();
		Map<String, Object> postData = new HashMap<String, Object>();
		String currentPageStr=request.getParameter("current_page")==null?"1":request.getParameter("current_page");
		Integer currentPage=Integer.valueOf(currentPageStr);
		String phone = request.getParameter("phone");
		 Pagination pagination=new Pagination();
		
		if(StringUtils.isNotBlank(phone)){
			String uid="";
			phoneData.put("phone", phone);
			com.meidusa.fastjson.JSONObject result = HttpClientPostMethod.httpCustReqUrl(phoneData, "getUserInfo");
			String code=result.getString("code");

	        if("0".equals(code)){
				JSONArray array = JSONArray.fromObject(result.get("data"));
				 for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					MemberVO memberVO = new MemberVO();
					//memberVO.setChannel(jsonObject.getString("channel"));
					memberVO.setEmail(jsonObject.getString("email"));
					memberVO.setIdCard(jsonObject.getString("idCard"));
					memberVO.setName(jsonObject.getString("name"));
					memberVO.setNickName(jsonObject.getString("nickName"));
					memberVO.setPhone(jsonObject.getString("phone"));
					memberVO.setUid(jsonObject.getString("uid"));
					Map<String,Object> postData1 = new HashMap<String, Object>();
					postData1.put("uid", jsonObject.getString("uid"));
					CardInfoVO cardInfo = memberService.getCardInfo(postData1);
					memberVO.setAvailAmt(cardInfo.getAvailAmt());
					memberVO.setFreezeAmt(cardInfo.getFreezeAmt());
					
					memberVO.setTotalRechargeAmt(cardInfo.getTotalRechargeAmt());
					memberVO.setTotalRewardAmt(cardInfo.getTotalRewardAmt());
					
					memberList.add(memberVO);
					postData.put("uid", uid);
				 }
				 pagination.setList(memberList);
				 mv.addObject("phone", phone);
			}
		}else{
			postData.put("pageNum", currentPage);
			postData.put("pageCount", StringUtil.pageSize);
			
			pagination = memberService.getMemberList(postData);
		}
		mv.setViewName("/member/memberList_new");
		mv.addObject("pager", pagination);
		return mv;
	}
	
	/**
	 * 获取会员卡信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCardInfo")
	public ModelAndView toCardInfo(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		postData.put("uid", uid);
		String phone = request.getParameter("phone");
		CardInfoVO cardInfoVO = memberService.getCardInfo(postData);
		mv.setViewName("/member/cardInfo_new");
		mv.addObject("card", cardInfoVO);
		mv.addObject("phone", phone);
		return mv;
	}
	
	
	
	/**
	 * 收藏列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCollectList")
	public ModelAndView toCollectList(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		postData.put("uid", uid);
		String phone = request.getParameter("phone");
		Map<String, List<CollectInfoVO>> collectList = memberService.getCollectList(postData);
		mv.setViewName("/member/collectList_new");
		mv.addObject("pager", collectList);
		mv.addObject("phone", phone);
		return mv;
	}
	
	/**
	 * 充值页面 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	@RequestMapping("/toRechargePage")
	public ModelAndView toRechargePage(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		String uid = request.getParameter("uid");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String chargeUrl = MsgPropertiesUtils.getChargeUrl();
		/*List<ChargeEntity> data = null;
		*//**
		 * 线下充值查询
		 *//*
		ChargeQueryRequest cqr = new ChargeQueryRequest();
		cqr.setMemberID(uid);
		HessianProxyFactory factory = new HessianProxyFactory();
		ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, chargeUrl+"chargeFacade");
		OfflineChargeQueryResponse ocs =facade.offlineChargeQuery(cqr);
		data.addAll(ocs.getData());
		*//**
		 * 线上充值查询
		 *//*
		ChargeQueryResponse cqs = facade.chargeQuery(cqr);
		data.addAll(cqs.getData());
		if(data.size() == 0){
			 mv.setViewName("/member/fristRecharge");
		}else{
			 mv.setViewName("/member/fristRecharge");
		   	for(int i = 0; i < data.size(); i++){
	   				//查询第一次充值几最后一次充值
	   				postData=new HashMap<String, Object>();
	   				Integer types1[]={100};
	   				postData.put("uid", uid);
	   				postData.put("types", types1);
	   				postData.put("sortByTime", 0);
	   				
	   				String url = RB.getString("mamaserver_url");
	   				Map<String, Object> flowList = memberService.getFlowList(postData,url);
	   				List<FlowInfoVO>  flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
	   				FlowInfoVO firstFlow=flowVoList.get(0);//第一次充值
	   				float firstRechargeFlow=Float.valueOf(firstFlow.amt);
	   				FlowInfoVO lastFlow=flowVoList.get(flowVoList.size()-1);
	   				Calendar cal1 = Calendar.getInstance();
	   				cal1.setTime(sdf.parse(lastFlow.getOperTime()));
	   				cal1.add(Calendar.DAY_OF_MONTH, -30);
	   				String startDate=sdf.format(cal1.getTime());//最后一次充值时间往后推30天的时间
	   				
	   				String endDate=lastFlow.getOperTime();//最后一次充值时间
	   				
	   				//累计30内充值奖励
	   				postData=new HashMap<String, Object>();
	   				Integer types3[]={100};
	   				postData.put("uid", uid);
	   				postData.put("types", types3);
	   				postData.put("sortByTime", 0);
	   				postData.put("startDate", startDate+" 00:00:00");
	   				postData.put("endDate", endDate+" 23:59:59");
	   				
	   			    url = RB.getString("mamaserver_url");
	   				flowList = memberService.getFlowList(postData,url);
	   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
	   				float sumRechargeFlow=0;
	   				for(int j=0;j<flowVoList.size();j++){
	   					FlowInfoVO vo=flowVoList.get(j);
	   					sumRechargeFlow=sumRechargeFlow+Float.valueOf(vo.amt);
	   				}
	   				
	   				if(sdf.parse(startDate).getTime()>sdf.parse(firstFlow.getOperTime()).getTime()){//不包含了第一次充值
	   					mv.addObject("rewardFlowFlag", "1");
	   					mv.addObject("sumRechargeFlow", sumRechargeFlow);
	   				}else{
	   					mv.addObject("rewardFlowFlag", "2");//包含第一次充值
	   					mv.addObject("sumRechargeFlow", sumRechargeFlow-firstRechargeFlow);
	   				}
	   				
	   				//查询第一次奖励和最后一次奖励
	   				postData=new HashMap<String, Object>();
	   				Integer types2[]={200};
	   				postData.put("uid", uid);
	   				postData.put("types", types2);
	   				postData.put("sortByTime", 0);
	   			    url = RB.getString("mamaserver_url");
	   				flowList = memberService.getFlowList(postData,url);
	   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
	   				firstFlow=flowVoList.get(0);
	   				float firstRewardFlow=Float.valueOf(firstFlow.amt);
	   				lastFlow=flowVoList.get(flowVoList.size()-1);//第一次奖励
	   			    cal1 = Calendar.getInstance();
	   				cal1.setTime(sdf.parse(lastFlow.getOperTime()));
	   				cal1.add(Calendar.DAY_OF_MONTH, -30);
	   				startDate=sdf.format(cal1.getTime());//最后一次奖励时间往后推20天时间
	   				endDate=lastFlow.getOperTime();//最后一次奖励时间
	   				
	   			    //累计30内奖励
	   				postData=new HashMap<String, Object>();
	   				Integer types4[]={200};
	   				postData.put("uid", uid);
	   				postData.put("types", types4);
	   				postData.put("sortByTime", 0);
	   				postData.put("startDate", startDate+" 00:00:00");
	   				postData.put("endDate", endDate+" 23:59:59");
	   				
	   			    url = RB.getString("mamaserver_url");
	   				flowList = memberService.getFlowList(postData,url);
	   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
	   				float sumRewardFlow=0;
	   				for(int j=0;j<flowVoList.size();j++){
	   					FlowInfoVO vo=flowVoList.get(j);
	   					sumRewardFlow=sumRewardFlow+Float.valueOf(vo.amt);
	   				}
	   				
	   				
	   				if(sdf.parse(startDate).getTime()>sdf.parse(firstFlow.getOperTime()).getTime()){//不包含了第一次充值
	   					mv.addObject("sumRewardFlow", sumRewardFlow);
	   				}else{
	   					mv.addObject("sumRewardFlow", sumRewardFlow-firstRewardFlow);
	   				}
	   			    mv.setViewName("/member/recharge");
   			
		   		}
		}*/
		mv.setViewName("/member/fristRecharge_new");
		request.setCharacterEncoding("UTF-8");
		name=new String(name.getBytes("ISO-8859-1"),"UTF-8");
		mv.addObject("name", name);
		mv.addObject("uid", uid);
		mv.addObject("phone", phone);
		return mv;
	}
	
	@RequestMapping("/calculation")
	public ModelAndView calculation(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String rechargeamtStr = request.getParameter("rechargeamt");
		float sumRechargeFlow=Float.valueOf(request.getParameter("sumRechargeFlow"));//累计充值30天
		float sumRewardFlow=Float.valueOf(request.getParameter("sumRewardFlow"));//累计奖励30天

		float rechargeamt=Float.valueOf(rechargeamtStr);
		float rechargeamtReward=0f;
		if((rechargeamt+sumRechargeFlow)<10000){
			rechargeamtReward=(float) ((rechargeamt+sumRechargeFlow)*0.01-sumRewardFlow);
		}else if((rechargeamt+sumRechargeFlow)<30000&&(rechargeamt+sumRechargeFlow)>=10000){
			rechargeamtReward=(float) ((rechargeamt+sumRechargeFlow)*0.05-sumRewardFlow);
		}else if((rechargeamt+sumRechargeFlow)<100000&&(rechargeamt+sumRechargeFlow)>=30000){
			rechargeamtReward=(float) ((rechargeamt+sumRechargeFlow)* 0.07-sumRewardFlow);
		}else if((rechargeamt+sumRechargeFlow)<300000&&(rechargeamt+sumRechargeFlow)>=100000){
			rechargeamtReward=(float) ((rechargeamt+sumRechargeFlow)* 0.1-sumRewardFlow);
		}else if((rechargeamt+sumRechargeFlow)>=300000){
			rechargeamtReward=(float) ((rechargeamt+sumRechargeFlow)* 0.15-sumRewardFlow);
		}
	
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		ObjectMapper objectMapper=new ObjectMapper();
		JsonGenerator jsonGenerator=objectMapper.getFactory().createGenerator(response.getWriter());	 
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("rechargeamtReward", ""+rechargeamtReward); 
		jsonGenerator.writeEndObject();
		jsonGenerator.flush();
		jsonGenerator.close();
		return null;
	}
	
	@RequestMapping("/firstCalculation")
	public ModelAndView firstCalculation(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String uid = request.getParameter("uid");
		String money = request.getParameter("rechargeamt");
		RechargeBean recharge = this.getRechargeBean(uid, money);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		ObjectMapper objectMapper=new ObjectMapper();
		JsonGenerator jsonGenerator=objectMapper.getFactory().createGenerator(response.getWriter());	 
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("rechargeamtReward", ""+recharge.getRewardMoney()); 
		jsonGenerator.writeEndObject();
		jsonGenerator.flush();
		jsonGenerator.close();
		return null;
	}
	
	/**
	 * 进行充值
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/toRecharge")
	public void toRecharge(HttpSession session, 
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		TSysUser user = (TSysUser) session.getAttribute(Constant.SESSION_USERINFO);
		String uid = request.getParameter("uid");
		String rewardamt = "".equals(request.getParameter("rewardamt")) || null == request.getParameter("rewardamt")?"0":request.getParameter("rewardamt");
		String rechargeamt = "".equals(request.getParameter("rechargeamt")) || null == request.getParameter("rechargeamt")?"0":request.getParameter("rechargeamt");
		String showdetail = request.getParameter("showdetail");
		String phone = request.getParameter("phone");
		String chargeUrl = MsgPropertiesUtils.getChargeUrl();
		OfflineChargeRequest vcr = new OfflineChargeRequest();
        vcr.setMemberID(uid);
        vcr.setMoney(rechargeamt);
        vcr.setRemark(showdetail);
        vcr.setOperator(user.getName());
        HessianProxyFactory factory = new HessianProxyFactory();
	 	ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, chargeUrl+"chargeFacade");
	 	OfflineChargeResponse ocs= facade.chargeOffline(vcr);
	 	if(!ocs.isSuccess()){
	 		logger.info("线下充值不成功");
	 	}else{
	 			//支付系统发送短信给用户
	 		 /* //发送充值成功短信给用户
	           SmsUtil.sendSms(phone, SmsUtil.genRechargeSms(phone, rechargeamt, rewardamt));*/
	 	}
		JsonGeneratorUtils.createRetMaptJSONObject(response, ocs.isSuccess(),ocs.getMsg());
	}
	
	
	

	@RequestMapping("/onlinRecharge")
	public Object onlinRecharge(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String uid = request.getParameter("uid");
		Float rechargeamt = Float.valueOf(request.getParameter("rechargeamt"));
		float rewardamt=0;
		String showdetail ="线上充值";
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		Integer types[]={100};
		postData.put("uid", uid);
		postData.put("types", types);
		
		JSONObject result=	HttpClientPostMethod.httpCustReqUrl(postData, "getFlows");
	   	JSONArray array = JSONArray.fromObject(result.get("data")); 
	   	for(int i = 0; i < array.size(); i++){
   			net.sf.json.JSONObject houseObject = array.getJSONObject(i);
   			String num = houseObject.get("num").toString();
   			if("0".equals(num)){
   				float rechargeamtReward=0;
   				if(rechargeamt<10000){
   					rechargeamtReward=(float) (rechargeamt*0.02);
   				}else if(rechargeamt<30000&&rechargeamt>=10000){
   					rechargeamtReward=(float) (rechargeamt*0.1);
   				}else if(rechargeamt<100000&&rechargeamt>=30000){
   					rechargeamtReward=(float) (rechargeamt* 0.14);
   				}else if(rechargeamt<300000&&rechargeamt>=100000){
   					rechargeamtReward=(float) (rechargeamt* 0.2);
   				}else if(rechargeamt>=300000){
   					rechargeamtReward=(float) (rechargeamt* 0.3);
   				}
   				rewardamt=rechargeamtReward;
   			}else{
   				//查询第一次充值几最后一次充值
   				postData=new HashMap<String, Object>();
   				Integer types1[]={100};
   				postData.put("uid", uid);
   				postData.put("types", types1);
   				postData.put("sortByTime", 0);
   				
   				String url = MsgPropertiesUtils.getMaMaServerUrl();
   				Map<String, Object> flowList = memberService.getFlowList(postData,url);
   				List<FlowInfoVO>  flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
   				FlowInfoVO firstFlow=flowVoList.get(0);//第一次充值
   				float firstRechargeFlow=Float.valueOf(firstFlow.amt);
   				FlowInfoVO lastFlow=flowVoList.get(flowVoList.size()-1);
   				Calendar cal1 = Calendar.getInstance();
   				cal1.setTime(sdf.parse(lastFlow.getOperTime()));
   				cal1.add(Calendar.DAY_OF_MONTH, -30);
   				String startDate=sdf.format(cal1.getTime());//最后一次充值时间往后推30天的时间
   				
   				String endDate=lastFlow.getOperTime();//最后一次充值时间
   				
   				//累计30内充值奖励
   				postData=new HashMap<String, Object>();
   				Integer types3[]={100};
   				postData.put("uid", uid);
   				postData.put("types", types3);
   				postData.put("sortByTime", 0);
   				postData.put("startDate", startDate+" 00:00:00");
   				postData.put("endDate", endDate+" 23:59:59");
   				
   			    url = MsgPropertiesUtils.getMaMaServerUrl();
   				flowList = memberService.getFlowList(postData,url);
   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
   				float sumRechargeFlow=0;
   				for(int j=0;j<flowVoList.size();j++){
   					FlowInfoVO vo=flowVoList.get(j);
   					sumRechargeFlow=sumRechargeFlow+Float.valueOf(vo.amt);
   				}
   				float sumRechargeFlowAmt=0;
   				if(sdf.parse(startDate).getTime()>sdf.parse(firstFlow.getOperTime()).getTime()){//不包含了第一次充值
   					sumRechargeFlowAmt=sumRechargeFlow;
   				}else{//包含第一次充值
   					sumRechargeFlowAmt=sumRechargeFlow-firstRechargeFlow;
   				}
   				
   				//查询第一次奖励和最后一次奖励
   				postData=new HashMap<String, Object>();
   				Integer types2[]={200};
   				postData.put("uid", uid);
   				postData.put("types", types2);
   				postData.put("sortByTime", 0);
   			    url = MsgPropertiesUtils.getMaMaServerUrl();
   				flowList = memberService.getFlowList(postData,url);
   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
   				firstFlow=flowVoList.get(0);
   				float firstRewardFlow=Float.valueOf(firstFlow.amt);
   				lastFlow=flowVoList.get(flowVoList.size()-1);//第一次奖励
   			    cal1 = Calendar.getInstance();
   				cal1.setTime(sdf.parse(lastFlow.getOperTime()));
   				cal1.add(Calendar.DAY_OF_MONTH, -30);
   				startDate=sdf.format(cal1.getTime());//最后一次奖励时间往后推20天时间
   				endDate=lastFlow.getOperTime();//最后一次奖励时间
   				
   			    //累计30内奖励
   				postData=new HashMap<String, Object>();
   				Integer types4[]={200};
   				postData.put("uid", uid);
   				postData.put("types", types4);
   				postData.put("sortByTime", 0);
   				postData.put("startDate", startDate+" 00:00:00");
   				postData.put("endDate", endDate+" 23:59:59");
   				
   			    url = MsgPropertiesUtils.getMaMaServerUrl();
   				flowList = memberService.getFlowList(postData,url);
   				flowVoList=(List<FlowInfoVO>)flowList.get("flowList");
   				float sumRewardFlow=0;
   				for(int j=0;j<flowVoList.size();j++){
   					FlowInfoVO vo=flowVoList.get(j);
   					sumRewardFlow=sumRewardFlow+Float.valueOf(vo.amt);
   				}
   				float sumRewardFlowAmt=0;
   				
   				if(sdf.parse(startDate).getTime()>sdf.parse(firstFlow.getOperTime()).getTime()){//不包含了第一次充值
   					sumRewardFlowAmt= sumRewardFlow;
   				}else{
   					sumRewardFlowAmt=sumRewardFlow-firstRewardFlow;
   				}
   				float rechargeamtReward=0f;
   				if((rechargeamt+sumRechargeFlowAmt)<10000){
   					rechargeamtReward=(float) ((rechargeamt+sumRechargeFlowAmt)*0.01-sumRewardFlowAmt);
   				}else if((rechargeamt+sumRechargeFlowAmt)<30000&&(rechargeamt+sumRechargeFlowAmt)>=10000){
   					rechargeamtReward=(float) ((rechargeamt+sumRechargeFlowAmt)*0.05-sumRewardFlowAmt);
   				}else if((rechargeamt+sumRechargeFlowAmt)<100000&&(rechargeamt+sumRechargeFlowAmt)>=30000){
   					rechargeamtReward=(float) ((rechargeamt+sumRechargeFlowAmt)* 0.07-sumRewardFlowAmt);
   				}else if((rechargeamt+sumRechargeFlowAmt)<300000&&(rechargeamt+sumRechargeFlowAmt)>=100000){
   					rechargeamtReward=(float) ((rechargeamt+sumRechargeFlowAmt)* 0.1-sumRewardFlowAmt);
   				}else if((rechargeamt+sumRechargeFlowAmt)>=300000){
   					rechargeamtReward=(float) ((rechargeamt+sumRechargeFlowAmt)* 0.15-sumRewardFlowAmt);
   				}
   				rewardamt=rechargeamtReward;
   			}
	   	}
		
		
	   	postData=new HashMap<String, Object>();
	   	postData.put("uid", uid);
		postData.put("rewardamt", rewardamt);
		postData.put("rechargeamt",rechargeamt);
		postData.put("showdetail", showdetail);
		RestResponse result1=  memberService.toRecharge(postData);
		JsonGeneratorUtils.createRetMaptJSONObject(response, result1.isSuccess(),result1.getMessage());
		return null;
	}
	
	
	
	
	/**
	 * 充值，奖励，消费记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toFlowList")
	public ModelAndView toFlowList(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> flowList = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		String type = request.getParameter("type");
		String phone = request.getParameter("phone");
		String url = MsgPropertiesUtils.getChargeUrl();
    	List<VirtualFlowEntity> data = null;
    	List<FlowInfoVO> flowInfoList = new ArrayList<FlowInfoVO>();
    	List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
    	try{
    		VirtualFlowQueryRequest vfqr = new VirtualFlowQueryRequest();
    		vfqr.setMemberID(uid);
    		if(Constant.FLOW_CODE_1.equals(type)){
    			mv.addObject("flowType", "充值");
    			types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
    			types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
    			vfqr.setType(types);
	    	}else if(Constant.FLOW_CODE_2.equals(type)){
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_REWARD);
	    		vfqr.setType(types);
	    	}else if(Constant.FLOW_CODE_3.equals(type)){
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_FREEZED);
	    		types.add(VirtualFlowTypeEnum.VIRTUAL_PAY);
	    		vfqr.setType(types);
	    	}
    		HessianProxyFactory factory = new HessianProxyFactory();
			VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, url+"virtualFacade");
			VirtualFlowQueryResponse o= facade.virtualFlowQuery(vfqr);
		 	logger.info("支付系统返回"+o);
    		data = o.getData();
    		for(VirtualFlowEntity v : data){
    			FlowInfoVO flowInfoVO = new FlowInfoVO();
				flowInfoVO.setAmt(v.getMoney());
				flowInfoVO.setFlowId("1");
				flowInfoVO.setShowDetail(v.getRemark());
				flowInfoVO.setType(String.valueOf(v.getType()));
				flowInfoVO.setUid(uid);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				flowInfoVO.setOperTime(simpleDateFormat.format(v.getCreateTime()));
    			flowInfoList.add(flowInfoVO);
    		}
    		flowList.put("flowList", flowInfoList);
    		mv.setViewName("/member/flowList");
    		mv.addObject("pager", flowList);
    		mv.addObject("phone", phone);
    	}catch(Exception e){
    		logger.error("查询流水异常", e);
    	}
    		return mv;
	}
	
	/**
	 * 订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toOrderList")
	public ModelAndView toOrderList(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		postData.put("uid", uid);
		String phone = request.getParameter("phone");
		String url = MsgPropertiesUtils.getMaMaServerUrl();
		String currentPageStr=request.getParameter("current_page")==null?"1":request.getParameter("current_page");
		Integer currentPage=Integer.valueOf(currentPageStr);
		postData.put("pageNum", currentPage);
		postData.put("pageCount", StringUtil.pageSize);
		
		Pagination orderList = memberService.getOrderList(postData,url);
		mv.setViewName("/member/orderList_new");
		mv.addObject("uid", uid);
		mv.addObject("pager", orderList);
		mv.addObject("phone", phone);
		return mv;
	}
	
	@RequestMapping("/toMyOrderList")
	public ModelAndView toMyOrderList(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		int citiesInt[]=null;
		String cities[]=request.getParameterValues("cities");
		if(cities!=null){
			 citiesInt=new int[cities.length];
			for (int i=0;i<cities.length;i++) {
				citiesInt[i]=Integer.valueOf(cities[i]);
				
			}
		}
		String status=request.getParameter("status");
		if(status!=null&&!"".equals(status)){
			Integer statusInt[]=new Integer[1];
			statusInt[0]=Integer.valueOf(status);
			postData.put("statuses",statusInt);
		}
	    mv.addObject("status", status);
		
		postData.put("cityIds", citiesInt);
		
		String currentPageStr=request.getParameter("current_page")==null?"1":request.getParameter("current_page");
		Integer currentPage=Integer.valueOf(currentPageStr);
		postData.put("pageNum", currentPage);
		postData.put("pageCount", StringUtil.pageSize);
		
		String url = MsgPropertiesUtils.getMaMaServerUrl();
		Pagination orderList = memberService.getOrderList(postData,url);
		List<CityInfo> cityList=getCities();
		List<BuildingsDto> buildList=getBuildings(Constant.BUILDING_TYPE_00);
		mv.addObject("cityList", cityList);
		mv.addObject("buildList", buildList);
		mv.setViewName("/member/myOrderList_new");
		mv.addObject("uid", uid);
		mv.addObject("pager", orderList);
		List<CityInfo> cityInfoList=getCities();
        for (CityInfo cityInfo : cityInfoList) {
        	if(citiesInt!=null){
	   			for (int i=0;i<citiesInt.length;i++) {
	   				int cityFlag=citiesInt[i];
	   				if(cityFlag==cityInfo.getCityId()){
	   					cityInfo.setChecked(1);
	   				}
	   				
	   			}
   		    }
		}
        mv.addObject("cityInfoList", cityInfoList);
		return mv;
	}
	
	/**
	 * 订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toOrderInfo")
	public ModelAndView toOrderInfo(
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String, Object> postData = new HashMap<String, Object>();
		String uid = request.getParameter("id");
		String orderId = request.getParameter("orderId");
		String phone = request.getParameter("phone");
		postData.put("uid", uid);
		postData.put("orderId", orderId);
		String url = MsgPropertiesUtils.getMaMaServerUrl();
		OrderInfoVO orderInfoVO = memberService.getOrderInfo(postData,url);
		mv.setViewName("/member/orderInfo_new");
		mv.addObject("order", orderInfoVO);
		mv.addObject("phone", phone);
		return mv;
	}
	
	
	public RechargeBean getRechargeBean(String uid,String money){
		RechargeBean rechargeBean = new RechargeBean();
		try{
			money = "".equals(money) || null == money ? "0":money;
			//请求支付系统计算奖励
		 	HessianProxyFactory factory = new HessianProxyFactory();  
		 	RewardFacade facade = (RewardFacade) factory.create(RewardFacade.class,MsgPropertiesUtils.getChargeUrl()+"rewardFacade");
		 	RewardCalRequest rewardRequest = new RewardCalRequest();
		 	rewardRequest.setMemberID(uid);
		 	rewardRequest.setMoney(money);
		 	RewardCalResponse rewardCalResponse =facade.calReward(rewardRequest);
		 	logger.info("支付系统返回结果:"+rewardCalResponse);
		 	if(rewardCalResponse.isSuccess()){
		 		String totalVirtual = rewardCalResponse.getTotalVirtual();
		 		String rewardMoney = rewardCalResponse.getRewardMoney();
		 		String totalRewardMoney = rewardCalResponse.getTotalRewardMoney();
		 		String totalAccount = rewardCalResponse.getTotalAccount();
		 		rechargeBean.setMoney(money);
		 		rechargeBean.setRewardMoney(rewardMoney);
		 		rechargeBean.setTotalAccount(totalAccount);
		 		rechargeBean.setTotalVirtual(totalVirtual);
		 		rechargeBean.setTotalRewardMoney(totalRewardMoney);
		 	}
		}catch(Exception e){
			logger.error("计算奖励异常",e);
		}	
		return rechargeBean;
	}
}