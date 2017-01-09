package com.mmzb.house.app.web.action.n99;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.vo.n99.HouseCardPageVo;
import com.mama.server.common.vo.n99.HouseCardShareVo;
import com.mama.server.common.vo.n99.HouseCardVo;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.UserInfoVo;

/**
 * 房券需求相关
 * */
@Controller
public class HouseCardAction {
	
	private Logger logger = LoggerFactory.getLogger(HouseCardAction.class);
	
	@Autowired
	private MemberTokenService memberTokenService;
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	/** 分页查询当前用户所属房券列表 */
	@RequestMapping(value = "/app/my/houseCardOrderList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> houseCardOrderList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/houseCardOrderList");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    String phoneNo = userInfo.getMobile();
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			postData.put("phoneNo", phoneNo);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardOrderList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Constants.APP_SUCCESSED,"获取房券订单列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/houseCardOrderList:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Constants.APP_FAILED, "获取房券订单列表失败.", null);
	}
	
	/** 领取的房券列表 */
	@RequestMapping(value = "/app/my/earnedHouseCardList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> earnedHouseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/earnedHouseCardList");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String phoneNo = userInfo.getMobile();//电话号码
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("phoneNo", phoneNo);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"earnedHouseCardList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Constants.APP_SUCCESSED,"获取领取的房券列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/earnedHouseCardList:获取领取的房券列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Constants.APP_FAILED, "获取领取的房券列表失败.", null);
	}
	
	/** 交换的房券列表 */
	@RequestMapping(value = "/app/my/exchangedHouseCardList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> exchangedHouseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/exchangedHouseCardList");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
			String uid = userInfo.getMemberId();//会员编号
		    String phoneNo = userInfo.getMobile();
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("phoneNo", phoneNo);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"exchangedHouseCardList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Constants.APP_SUCCESSED,"获取交换的房券列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/exchangedHouseCardList:获取交换的房券列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Constants.APP_FAILED, "获取交换的房券列表失败.", null);
	}
	
	/** 根据订单号分页查询当前订单下所属房券列表 */
	@RequestMapping(value = "/app/my/houseCardList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> houseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/houseCardList");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			String orderId = request.getParameter("orderId");
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    String phone = userInfo.getMobile();
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("phone", phone);
			postData.put("orderId", orderId);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Constants.APP_SUCCESSED,"获取房券订单列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/queryHouseCardListByOrderId:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Constants.APP_FAILED, "获取房券订单列表失败.", null);
	}
	
	/** 单张房券详情页面 */
	@RequestMapping(value = "/app/my/houseCardDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardVo> houseCardDetail(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/houseCardDetail");
		}
		try {
			String cardId = request.getParameter("cardId");
			String cardNo = request.getParameter("cardNo");
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("cardId", cardId);
			postData.put("cardNo", cardNo);
			postData.put("phone", userInfo.getMobile());
			
			Response<HouseCardVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardDetail", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				/** 详情页面提示 */
				StringBuffer msg = new StringBuffer("【使用规则】\n");
				msg.append("1. 您可凭本券入住N99系列的任意房型壹间夜\n");
				msg.append("2. 限非周末、节假日使用\n");
				msg.append("3. 凭券码兑换入住，提前24小时致电客服400-9966-633\n");
				msg.append("4. 有效期至2017年3月31日\n");
				msg.append("【温馨提示】\n");
				msg.append("您可在妈妈送房APP/H5“我的—电子房券”中查看房券\n");
				HouseCardVo houseCardDetail = result.getData();
				houseCardDetail.setTipsMsg(msg.toString());
				return new ResponseOut<HouseCardVo>(Constants.APP_SUCCESSED,"获取房券详情成功.", houseCardDetail);
			}
		} catch (Exception e) {
			logger.error("/app/my/queryHouseCardListByOrderId:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardVo>(Constants.APP_FAILED, "获取房券详情失败.", null);
	}
	
	/** 根据订单号分享房券 */
	@RequestMapping(value = "/app/my/shareHouseCardOfOrder", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardShareVo> shareHouseCardOfOrder(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/shareHouseCardOfOrder");
		}
		String msg = "分享、赠送房券失败.";
		try {
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    String phoneNo = userInfo.getMobile();
		    
		    String orderId = request.getParameter("orderId");
		    String cardNum = request.getParameter("cardNum");
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("orderId", orderId);
			postData.put("uid", uid);
			postData.put("phoneNo", phoneNo);
			postData.put("giveType", "1");//分享方式：1、订单；2、单张房券；
			postData.put("cardNum", cardNum);
			
			Response<HouseCardShareVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"giveHouseCard", HouseCardShareVo.class);
			if (result.getCode().equals("0")) {
				HouseCardShareVo houseCardShareVo = result.getData();
				return new ResponseOut(Constants.APP_SUCCESSED, result.getMsg(), houseCardShareVo);
			}
			msg = result.getMsg();
		} catch (Exception e) {
			logger.error("/app/my/shareHouseCardOfOrder:分享、赠送房券失败.", e);
		}
		return new ResponseOut(Constants.APP_FAILED, msg, null);
	}
	
	/** 根据订单号分享房券 */
	@RequestMapping(value = "/app/my/shareHouseCard", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardShareVo> shareHouseCard(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/shareHouseCard");
		}
		try {
			String cardNo = request.getParameter("cardNo");
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    String receivePhone = userInfo.getMobile();
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("cardNo", cardNo);
			postData.put("uid", uid);
			postData.put("receivePhone", receivePhone);
			postData.put("giveType", "2");//分享方式：1、订单；2、单张房券；
			
			Response<HouseCardShareVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"giveHouseCard", HouseCardShareVo.class);
			if (result.getCode().equals("0")) {
				HouseCardShareVo houseCardShareVo = result.getData();
				return new ResponseOut(Constants.APP_SUCCESSED, result.getMsg(), houseCardShareVo);
			}
		} catch (Exception e) {
			logger.error("/app/my/shareHouseCard:分享、赠送房券失败.", e);
		}
		return new ResponseOut(Constants.APP_FAILED, "分享、赠送房券失败.", null);
	}
	
	/** 分享成功更新分享状态 */
	@RequestMapping(value = "/app/my/updateShareStatus", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut updateShareStatus(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/updateShareStatus");
		}
		try {
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String sharePatchNo = request.getParameter("sharePatchNo");//房券分享批次号
		    String opType = request.getParameter("opType");//操作方式：0、取消；1、确定
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("sharePatchNo", sharePatchNo);
			postData.put("opType", opType);
			
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"updateShareStatus", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				return new ResponseOut(Constants.APP_SUCCESSED, result.getMsg());
			}
		} catch (Exception e) {
			logger.error("/app/my/updateShareStatus:更新分享批次状态失败.", e);
		}
		return new ResponseOut(Constants.APP_FAILED, "更新分享批次状态失败.", null);
	} 
	
	/** 根据手机号码领取房券 */
	@RequestMapping(value = "/app/my/receiveHouseCard", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardVo> receiveHouseCard(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/receiveHouseCard");
		}
		try {
			String accessToken = request.getParameter("accessToken");
			if(accessToken == null || accessToken.trim().length() < 1) {
				accessToken = request.getHeader("x-client-accessToken");
			}
			UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(accessToken);
		    String uid = userInfo.getMemberId();//会员编号
		    
		    String sharePatchNo = request.getParameter("sharePatchNo");//房券分享批次号
		    String phoneNo = request.getParameter("phoneNo");
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("sharePatchNo", sharePatchNo);
			postData.put("phoneNo", phoneNo);
			
			Response<HouseCardVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"receiveHouseCard", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				return new ResponseOut(Constants.APP_SUCCESSED, result.getMsg());
			}
		} catch (Exception e) {
			logger.error("/app/my/receiveHouseCard:领取房券失败.", e);
		}
		return new ResponseOut(Constants.APP_FAILED, "分享、赠送房券失败.", null);
	} 
}
