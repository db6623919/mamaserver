package com.mmzb.house.web.action.n99;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.server.common.vo.n99.HouseCardPageVo;
import com.mama.server.common.vo.n99.HouseCardShareVo;
import com.mama.server.common.vo.n99.HouseCardVo;
import com.mmzb.house.web.action.base.BaseAction;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.Contants;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;
import com.mmzb.house.web.core.common.vo.Response;
import com.mmzb.house.web.core.common.vo.ResponseOut;
import com.netfinworks.ma.service.response.PersonalMemberInfo;

/**
 * 房券需求相关
 * */
@Controller
public class HouseCardAction extends BaseAction {
	
	private Logger logger = LoggerFactory.getLogger(HouseCardAction.class);
	

	@Resource(name="appProperties")
	private AppProperties appProperties;
	
	/** 分页查询当前用户所属房券列表 */
	@RequestMapping(value = "/my/houseCardOrderList.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> houseCardOrderList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/houseCardOrderList.htm");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    String phoneNo = loginUser.getPhone();
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("phoneNo", phoneNo);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardOrderList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Contants.SUCCESSED,"获取房券订单列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/my/houseCardOrderLis.htmt:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Contants.FAILED, "获取房券订单列表失败.", null);
	}
	
	/** 领取的房券列表 */
	@RequestMapping(value = "/my/earnedHouseCardList.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> earnedHouseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/earnedHouseCardList.htm");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
		    PersonalMemberInfo personalMemberInfo = getMemberInfo(request.getSession());
		    String phoneNo = getMemberPhone(personalMemberInfo);//电话号码
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("phoneNo", phoneNo);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"earnedHouseCardList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Contants.SUCCESSED,"获取领取的房券列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/my/earnedHouseCardList.htm:获取领取的房券列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Contants.FAILED, "获取领取的房券列表失败.", null);
	}
	
	/** 交换的房券列表 */
	@RequestMapping(value = "/my/exchangedHouseCardList.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> exchangedHouseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/exchangedHouseCardList.htm");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("pageSize", pageSize);
			postData.put("pageIndex", pageIndex);
			
			Response<HouseCardPageVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"exchangedHouseCardList", HouseCardPageVo.class);
			if (result.getCode().equals("0")) {
				HouseCardPageVo houseCardPageVo = result.getData();
				return new ResponseOut<HouseCardPageVo>(Contants.SUCCESSED,"获取交换的房券列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/my/exchangedHouseCardList.htm:获取交换的房券列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Contants.FAILED, "获取交换的房券列表失败.", null);
	}
	
	/** 根据订单号分页查询当前订单下所属房券列表 */
	@RequestMapping(value = "/my/houseCardList.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardPageVo> houseCardList(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/my/houseCardList");
		}
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));//不传后台默认设置一个值，比如：10
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前页数
			String orderId = request.getParameter("orderId");
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    String phone = loginUser.getPhone();
		    
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
				return new ResponseOut<HouseCardPageVo>(Contants.SUCCESSED,"获取房券订单列表成功.", houseCardPageVo);
			}
		} catch (Exception e) {
			logger.error("/my/queryHouseCardListByOrderId.htm:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardPageVo>(Contants.FAILED, "获取房券订单列表失败.", null);
	}
	
	/** 单张房券详情页面 */
	@RequestMapping(value = "/my/houseCardDetail.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardVo> houseCardDetail(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/houseCardDetail");
		}
		try {
			String cardId = request.getParameter("cardId");
			String cardNo = request.getParameter("cardNo");
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("cardId", cardId);
			postData.put("cardNo", cardNo);
			postData.put("phone", loginUser.getPhone());
			
			//h5房券详情页面url
			//分享需要的：主标题、副标题、url、图片url
			
			Response<HouseCardVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardDetail", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				HouseCardVo houseCardDetail = result.getData();
				return new ResponseOut<HouseCardVo>(Contants.SUCCESSED,"获取房券详情成功.", houseCardDetail);
			}
		} catch (Exception e) {
			logger.error("/my/queryHouseCardListByOrderId.htm:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardVo>(Contants.FAILED, "获取房券详情失败.", null);
	}
	
	/** 单张房券详情页面 */
	@RequestMapping(value = "/open/viewHouseCardDetail.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardVo> viewHouseCardDetail(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /open/viewHouseCardDetail");
		}
		try {
			String cardId = request.getParameter("cardId");
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("uid", uid);
			postData.put("cardId", cardId);
			postData.put("viewType", "open");
			
			//h5房券详情页面url
			//分享需要的：主标题、副标题、url、图片url
			
			Response<HouseCardVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"houseCardDetail", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				HouseCardVo houseCardDetail = result.getData();
				houseCardDetail.setCardNo("");
				return new ResponseOut<HouseCardVo>(Contants.SUCCESSED,"获取房券详情成功.", houseCardDetail);
			}
		} catch (Exception e) {
			logger.error("/open/queryHouseCardListByOrderId.htm:获取房券订单列表失败.",e);
		}
		return new ResponseOut<HouseCardVo>(Contants.FAILED, "获取房券详情失败.", null);
	}
	
	/** 根据订单号分享房券 */
	@RequestMapping(value = "/my/shareHouseCardOfOrder.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardShareVo> shareHouseCardOfOrder(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/shareHouseCardOfOrder.htm");
		}
		String msg = "分享、赠送房券失败.";
		try {
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    String phoneNo = loginUser.getPhone();
		    
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
				return new ResponseOut(Contants.SUCCESSED, result.getMsg(), houseCardShareVo);
			}
			msg = result.getMsg();
		} catch (Exception e) {
			logger.error("/my/shareHouseCardOfOrder.htm:分享、赠送房券失败.", e);
		}
		return new ResponseOut(Contants.FAILED, msg, null);
	}
	
	/** 根据订单号分享房券 */
	@RequestMapping(value = "/my/shareHouseCard.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardShareVo> shareHouseCard(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/shareHouseCard.htm");
		}
		try {
			String cardNo = request.getParameter("cardNo");
		    PersonalMemberInfo personalMemberInfo = getMemberInfo(request.getSession());
		    String uid = personalMemberInfo.getMemberId();//会员编号
		    String receivePhone = getMemberPhone(personalMemberInfo);//电话号码
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("cardNo", cardNo);
			postData.put("uid", uid);
			postData.put("receivePhone", receivePhone);
			postData.put("giveType", "2");//分享方式：1、订单；2、单张房券；
			
			Response<HouseCardShareVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"giveHouseCard", HouseCardShareVo.class);
			if (result.getCode().equals("0")) {
				HouseCardShareVo houseCardShareVo = result.getData();
				return new ResponseOut(Contants.SUCCESSED, result.getMsg(), houseCardShareVo);
			}
		} catch (Exception e) {
			logger.error("/my/shareHouseCard.htm:分享、赠送房券失败.", e);
		}
		return new ResponseOut(Contants.FAILED, "分享、赠送房券失败.", null);
	}
	
	/** 分享成功更新分享状态 */
	@RequestMapping(value = "/my/updateShareStatus.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut updateShareStatus(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /my/updateShareStatus.htm");
		}
		try {
			UserInfo loginUser = getLoginUserInfo(request);
		    String uid = loginUser.getUid();//会员编号
		    
		    String sharePatchNo = request.getParameter("sharePatchNo");//房券分享批次号
		    String opType = request.getParameter("opType");//操作方式：0、取消；1、确定
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("sharePatchNo", sharePatchNo);
			postData.put("opType", opType);
			
			Response result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"updateShareStatus", HouseCardVo.class);
			if (result.getCode().equals("0")) {
				return new ResponseOut(Contants.SUCCESSED, result.getMsg());
			}
		} catch (Exception e) {
			logger.error("/my/updateShareStatus.htm:更新分享批次状态失败.", e);
		}
		return new ResponseOut(Contants.FAILED, "更新分享批次状态失败.", null);
	} 
	
	/** 根据手机号码领取房券 */
	@RequestMapping(value = "/open/receiveHouseCard.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseCardVo> receiveHouseCard(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /open/receiveHouseCard.htm");
		}
		int code = Contants.FAILED;
		String msg = "领取房券失败.";
		try {
		    String sharePatchNo = request.getParameter("sharePatchNo");//房券分享批次号
		    String phoneNo = request.getParameter("phoneNo");
		    
			Map<String, Object> postData = new HashMap<String, Object>();
			postData.put("sharePatchNo", sharePatchNo);
			postData.put("phoneNo", phoneNo);
			
			Response<HouseCardVo> result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), 
					"receiveHouseCard", HouseCardVo.class);
			code = Integer.valueOf(result.getCode());
			msg = result.getMsg();
			if (code == 0) {
				HouseCardVo houseCardVo = result.getData();
				return new ResponseOut(Contants.SUCCESSED, msg, houseCardVo);
			}
		} catch (Exception e) {
			logger.error("/open/receiveHouseCard.htm:领取房券失败.", e);
			msg = "领取过程中出现异常，领取失败";
		}
		return new ResponseOut(code, msg, null);
	} 
}
