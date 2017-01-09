package com.console.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.console.framework.dao.Pagination;
import com.console.util.Constant;
import com.console.util.HttpClientPostMethod;
import com.console.util.RestResponse;
import com.console.util.StringUtil;
import com.console.vo.CardInfoVO;
import com.console.vo.CollectInfoVO;
import com.console.vo.FlowInfoVO;
import com.console.vo.MemberVO;
import com.console.vo.OrderInfoVO;



@Service
public class MemberService {
	
	public static Logger logger = Logger.getLogger(MemberService.class);
	
	/**
	 * 查询会员列表
	 * @param postData
	 * @return
	 */
	public Pagination getMemberList(Map<String, Object> postData){
		List<MemberVO> memberList = new ArrayList<MemberVO>();
		com.meidusa.fastjson.JSONObject result = null;
		 Pagination pagination=new Pagination();
		try {
			logger.info("查询会员列表请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpReqUrl(postData,  "getAllUserInfo");
			logger.info("查询会员列表返回结果,result{}"+result);
			if(result != null){
				JSONObject memberObject =JSONObject.fromObject(result.getString("data"));
				String totalNum = memberObject.getString("totalNum");
				JSONArray memberArray = JSONArray.fromObject(memberObject.getString("userInfos").toString());
				for (int i = 0; i < memberArray.size(); i++) {
					JSONObject member = memberArray.getJSONObject(i);
					MemberVO memberVO = new MemberVO();
					memberVO.setChannel(member.getString("channel"));
					memberVO.setEmail(member.getString("email"));
					memberVO.setIdCard(member.getString("idCard"));
					memberVO.setName(member.getString("name"));
					memberVO.setNickName(member.getString("nickName"));
					memberVO.setPhone(member.getString("phone"));
					memberVO.setUid(member.getString("uid"));
				
					Map<String,Object> postData1 = new HashMap<String, Object>();
					postData1.put("uid", member.getString("uid"));
					CardInfoVO cardInfo = getCardInfo(postData1);
					
					memberVO.setAvailAmt(cardInfo.getAvailAmt());
					memberVO.setFreezeAmt(cardInfo.getFreezeAmt());
					
					memberVO.setTotalRechargeAmt(cardInfo.getTotalRechargeAmt());
					memberVO.setTotalRewardAmt(cardInfo.getTotalRewardAmt());
					
					memberList.add(memberVO);
				}
				Integer pageNum=Integer.valueOf(postData.get("pageNum").toString());
				 pagination.paging(pageNum, StringUtil.pageSize, Integer.valueOf(totalNum));
				 pagination.setList(memberList);
			}
			logger.info("查询会员列表返回到页面的结果,memberList{}"+memberList);
		}catch (Exception e) {
			logger.info("查询会员列表出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return pagination;
	}
	
	/**
	 * 获取会员收藏列表
	 * @param postData
	 * @param url
	 * @return
	 */
	public Map<String, List<CollectInfoVO>> getCollectList(Map<String, Object> postData){
		Map<String, List<CollectInfoVO>> resultMap = new HashMap<String, List<CollectInfoVO>>();
		List<CollectInfoVO> collectList = new ArrayList<CollectInfoVO>();
		com.meidusa.fastjson.JSONObject result = null;
		try {
			logger.info("查询会员收藏记录请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpReqUrl(postData,  "getCollects");
			logger.info("查询会员收藏记录返回结果,result{}"+result);
			if(result != null){
				JSONObject collectObject =JSONObject.fromObject(result.getString("data"));
				JSONArray collectArray = JSONArray.fromObject(collectObject.getString("collects"));
				for (int i = 0; i < collectArray.size()&&collectArray!=null; i++) {
				
					JSONObject collect = collectArray.getJSONObject(i);
					CollectInfoVO collectVO = new CollectInfoVO();
					collectVO.setHouseId(collect.getString("houseId"));
					String operTime = collect.getString("operTime");
					Date operDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
					operTime = new SimpleDateFormat("yyyy-MM-dd").format(operDate);
					collectVO.setOperTime(operTime);
					collectList.add(collectVO);
				}
				resultMap.put("collectList",collectList);
			}
			logger.info("查询会员收藏记录返回到页面的结果,collectList{}"+resultMap);
		}catch (Exception e) {
			logger.info("查询会员收藏记录出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 查询卡信息
	 * @param postData
	 * @param url
	 * @return
	 */
	public CardInfoVO getCardInfo(Map<String, Object> postData){
		CardInfoVO cardInfoVO = new CardInfoVO();
		com.meidusa.fastjson.JSONObject result = null;
		try {
			logger.info("查询会员卡信息请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpCustReqUrl(postData,  "getCardInfo");
			logger.info("查询会员卡信息返回结果,result{}"+result);
			if(result != null){
				JSONObject cardInfo =JSONObject.fromObject(result.getString("data"));
				if(cardInfo!=null&&!cardInfo.isEmpty()){
					cardInfoVO.setAvailAmt(cardInfo.getString("availAmt"));
					//cardInfoVO.setCardId(cardInfo.getString("cardId"));
					cardInfoVO.setFreezeAmt(cardInfo.getString("freezeAmt"));
					cardInfoVO.setLevel(cardInfo.getString("level"));
					cardInfoVO.setTotalRechargeAmt(cardInfo.getString("totalRechargeAmt"));
					cardInfoVO.setTotalRewardAmt(cardInfo.getString("totalRewardAmt"));
				}
			}
			logger.info("查询会员卡信息返回到页面的结果,cardInfoVO{}"+cardInfoVO);
		}catch (Exception e) {
			logger.info("查询会员卡信息出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return cardInfoVO;
	}
	
	/**
	 * 进行充值
	 * @param postData
	 * @return
	 */
	public RestResponse toRecharge(Map<String, Object> postData){
		RestResponse  response = new RestResponse();
		com.meidusa.fastjson.JSONObject result = null;
		try {
			logger.info("会员充值请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpReqUrl(postData,  "recharge");
			logger.info("会员充值返回结果,result{}"+result);
			if(result != null){
				String code = result.getString("code");
				if(Constant.RETURN_CODE.contains(code)){
					response.setSuccess(true);
				}else {
					response.setSuccess(false);
					response.setMessage(result.getString("msg"));
				}
			}
			logger.info("会员充值返回到页面的结果,response{}"+response);
		}catch (Exception e) {
			logger.info("会员充值出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 充值，奖励，消费记录
	 * @param postData
	 * @param url
	 * @return
	 */
	public Map<String, Object> getFlowList(Map<String, Object> postData,String url){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<FlowInfoVO> flowList = new ArrayList<FlowInfoVO>();
		com.meidusa.fastjson.JSONObject result = null;
		try {
			logger.info("查询会员充值，奖励，消费记录请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpCustReqUrl(postData,  "getFlows");
			logger.info("查询会员充值，奖励，消费记录返回结果,result{}"+result);
			if(result != null){
				JSONObject flowObject =JSONObject.fromObject(result.getString("data"));
				String count = flowObject.getString("num");
				JSONArray flowArray = JSONArray.fromObject(flowObject.getString("flows"));
				resultMap.put("total_count",count);
				for (int i = 0; i < flowArray.size()&&flowArray!=null; i++) {
					JSONObject flow = flowArray.getJSONObject(i);
					FlowInfoVO flowInfoVO = new FlowInfoVO();
					flowInfoVO.setAmt(flow.getString("amt"));
					flowInfoVO.setFlowId(flow.getString("flowId"));
					flowInfoVO.setShowDetail(flow.getString("showDetail"));
					flowInfoVO.setType(flow.getString("type"));
					flowInfoVO.setUid(flow.getString("uid"));
					String operTime = flow.getString("operTime");
					Date operDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
					operTime = new SimpleDateFormat("yyyy-MM-dd").format(operDate);
					flowInfoVO.setOperTime(operTime);
					flowList.add(flowInfoVO);
				}
				resultMap.put("flowList",flowList);
			}
			logger.info("查询会员充值，奖励，消费记录返回到页面的结果,flowList{}"+resultMap);
		}catch (Exception e) {
			logger.info("查询会员充值，奖励，消费记录出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 订单列表
	 * @param postData
	 * @param url
	 * @return
	 */
	public Pagination getOrderList(Map<String, Object> postData,String url){
		List<OrderInfoVO> orderList = new ArrayList<OrderInfoVO>();
		com.meidusa.fastjson.JSONObject result = null;
		Pagination pagination=new Pagination();
		try {
			logger.info("查询会员订单列表记录请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpCustReqUrl(postData,  "getOrders");
			logger.info("查询会员订单列表记录返回结果,result{}"+result);
			if(result != null){
				JSONObject orderObjectObject =JSONObject.fromObject(result.getString("data"));
				String totalNum = orderObjectObject.getString("totalNum");
				JSONArray orderArray = JSONArray.fromObject(orderObjectObject.getString("orders"));
				//resultMap.put("total_count",count);
				for (int i = 0; i < orderArray.size()&&orderArray!=null; i++) {
					JSONObject order = orderArray.getJSONObject(i);
					OrderInfoVO orderInfoVO = new OrderInfoVO();
					orderInfoVO.setOrderId(order.getString("orderId"));
					orderInfoVO.setFreezeAmt(order.getString("freezeAmt"));
					orderInfoVO.setTotalAmt(order.getString("totalAmt"));
					orderInfoVO.setHouseId(order.getString("houseId"));
					orderInfoVO.setStatus(order.getString("status"));
					JSONObject houseObject =JSONObject.fromObject(order.getString("liveDetail"));
					String houseName=houseObject.getString("houseName");
  
					String endTime=houseObject.getString("endTime");
					String beginTime=houseObject.getString("beginTime");
					String linkmanName=houseObject.getString("linkmanName");
					String linkmanPhone=houseObject.getString("linkmanPhone");
					String uid=order.getString("uid");
					orderInfoVO.setUserName(linkmanName);
					orderInfoVO.setPhone(linkmanPhone);
					orderInfoVO.setBeginTime(beginTime);
					orderInfoVO.setEndTime(endTime);
					orderInfoVO.setHouseName(houseName);
					orderInfoVO.setUid(uid);
					String operTime = order.getString("operTime");
					Date operDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
					operTime = new SimpleDateFormat("yyyy-MM-dd").format(operDate);
					orderInfoVO.setOperTime(operTime);
					
					Map<String, Object> houseData=new HashMap<String, Object>();
					houseData.put("houseId", Integer.valueOf(order.getString("houseId")));
			    	com.meidusa.fastjson.JSONObject houseresult=HttpClientPostMethod.httpCustReqUrl(houseData,"getHouseInfo");
	
                    JSONArray array = JSONArray.fromObject(houseresult.get("data")); 
			       		for(int j = 0; j < array.size(); j++){
			       			net.sf.json.JSONObject jsonObject =array.getJSONObject(j);
			       			String devId=jsonObject.get("devId").toString();
			       			String bldId=jsonObject.get("bldId").toString();
			       			String cityId=jsonObject.get("cityId").toString();
			       			String room=jsonObject.get("room").toString();
			       			orderInfoVO.setDevId(devId);
			       			orderInfoVO.setBldId(bldId);
			       			orderInfoVO.setCityId(cityId);
			       			orderInfoVO.setRoom(room);
			       		}
					
					orderList.add(orderInfoVO);
				}
				pagination.paging(Integer.valueOf(postData.get("pageNum").toString()), StringUtil.pageSize, Integer.valueOf(totalNum));
				pagination.setList(orderList);
			}
		}catch (Exception e) {
			logger.info("查询会员订单列表记录出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return pagination;
	}
	
	/**
	 * 订单详情
	 * @param postData
	 * @param url
	 * @return
	 */
	public OrderInfoVO getOrderInfo(Map<String, Object> postData,String url){
		OrderInfoVO orderInfoVO = new OrderInfoVO();
		com.meidusa.fastjson.JSONObject result = null;
		try {
			logger.info("查询会员卡信息请求参数,postData{}"+postData);
			result = HttpClientPostMethod.httpCustReqUrl(postData,  "getOrderInfo");
			logger.info("查询会员卡信息返回结果,result{}"+result);
			if(result != null){
				JSONObject orderObject =JSONObject.fromObject(result.getString("data"));
				orderInfoVO.setFreezeAmt(orderObject.getString("freezeAmt"));
				String operTime = orderObject.getString("operTime");
				Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(operTime);
				operTime = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
				orderInfoVO.setOperTime(operTime);
				orderInfoVO.setLiveDetail(orderObject.getString("liveDetail"));
				JSONObject houseObject =JSONObject.fromObject(orderObject.getString("liveDetail"));
				String houseName=houseObject.getString("houseName");
				orderInfoVO.setHouseName(houseName);
				orderInfoVO.setStatus(orderObject.getString("status"));
				orderInfoVO.setTotalAmt(orderObject.getString("totalAmt"));
				orderInfoVO.setHouseId(orderObject.getString("houseId"));
				orderInfoVO.setOrderId(orderObject.getString("orderId"));
			}
			logger.info("查询会员卡信息返回到页面的结果,orderInfoVO{}"+orderInfoVO);
		}catch (Exception e) {
			logger.info("查询会员卡信息出现异常，异常为："+e.getMessage());
			e.printStackTrace();
		}
		return orderInfoVO;
	}
}
