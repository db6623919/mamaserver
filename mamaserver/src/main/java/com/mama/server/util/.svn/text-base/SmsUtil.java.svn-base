package com.mama.server.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtil {
	//记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsUtil.class);
	
	//一天的毫秒数
	private static final long ONE_DAY_IN_MILLIONS = 24 * 60 * 60 * 1000;
	
	/**
	 * 发送短信
	 * @param phone 目的手机号码
	 * @param msg 短信内容
	 * @return
	 */
	public static boolean sendSms(String phone, String msg) {
		String encodedMsg;
		try  {
			encodedMsg = URLEncoder.encode(msg, "UTF-8");
			String url = PropertiesUtils.getSmsServerUrl();
			String param = "userId=J10080&password=856015&pszMobis=" + phone + "&iMobiCount=1&pszSubPort=*&pszMsg=" + encodedMsg;
			String sendResult = Http.sendGet(url, param);
			if (sendResult == null || sendResult.trim().compareTo("") == 0) {
				LOGGER.error("Failed to send message,message is {}, phone is {}.", msg, phone);
				return false;
			}
			LOGGER.debug("Send message successfully, message is {}, phone is {}.", msg, phone);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Failed to send message.", e);
			return false;
		}
		return true;
	}
	
	/******************************************妈妈小管家短信************************************************/

	/**
	 * 订单提交之后通知妈妈小管家
	 * @param bldName
	 * @param beginTime
	 * @param endTime
	 * @param orderId
	 * @param totalAmt
	 * @param userPhone
	 * @param merchantPhone
	 * @return
	 */
	public static String genCreateOrderNotice2Mama(String houseName, String beginTime, String endTime, String orderId, int totalAmt, String userPhone, String merchantPhone) {
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("客户")
			  .append(userPhone)
			  .append("预订的")
			  .append(msgSegment)
			  .append("，订单总价" + totalAmt)
			  .append("，订单号" + orderId)
			  .append("，审核时间为30分钟，请立即跟踪订单并与商户联系确认信息，商户电话：")
			  .append(merchantPhone);
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genMamaCreateOrderNotice message.", e);
		}
		
		return null;
	}
	
	/**
	 * 订单变为待入后通知妈妈小管家
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @param userPhone
	 * @param orderId
	 * @param totalAmt
	 * @return
	 */
	public static String genLivingOrderNotice2Mama(String beginTime, String endTime, String houseName, String userPhone, String orderId, int totalAmt, int payAmt){
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("客户")
			  .append(userPhone)
			  .append("预订的")
			  .append(msgSegment)
			  .append("已经支付，订单总价" + totalAmt)
			  .append("元，实际支付金额" + payAmt)
			  .append("元，订单号" + orderId);
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genMamaCreateOrderNotice message.", e);
		}
		
		return null;
	}
	
	/**
	 * 会员离店TO妈妈客服
	 * @param phone
	 * @param roomName
	 * @return
	 */
	public static String genLeavedOrderNotice2Mama(String phone, String roomName) {
		return "客户" + phone + "已从" + roomName + "离店，请联系用户回访，谢谢！";
	}
	
	// 用户取消预订 for user 
	public static String genCancelOrderNotice2Mama(String beginTime, String endTime, String houseName, String userPhone, String orderId, int totalAmt) {
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("客户")
			  .append(userPhone)
			  .append("已取消预订的")
			  .append(msgSegment)
			  .append("，订单总价" + totalAmt)
			  .append("，订单号" + orderId)
			  .append("，请与客户联系回访!");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genMamaCreateOrderNotice message.", e);
		}
		
		return null;
	}
	
	/**********************************************用户短信************************************************/
	
	/**
	 * 通知用户入住
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @param merchantPhone
	 * @return
	 */
	public static String genLivingOrderNotice2User(String beginTime, String endTime, String houseName, String merchantPhone) {
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("您已支付")
			  .append(msgSegment)
			  .append("，可以入住美房啦，收拾心情准备出发吧！如有疑问请联系小管家热线：4009966633，商家电话：")
			  .append(merchantPhone);
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genPayingOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 如果预订人不是入住人,则通知预订人支付成功
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @param merchantPhone
	 * @return
	 */
	public static String genPaidOrderNotice2Booker(String beginTime, String endTime, String houseName, String merchantPhone){
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("您已支付")
			  .append(msgSegment)
			  .append("，如有疑问请联系小管家热线：4009966633，商家电话：")
			  .append(merchantPhone);
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genPayingOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 如果预订人是入住人,则通知入住人
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @param merchantPhone
	 * @param userPhone
	 * @return
	 */
	public static String genPaidOrderNotice2User(String beginTime, String endTime, String houseName, String merchantPhone, String userPhone){
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("预订人")
			  .append(userPhone)
			  .append("已为您支付")
			  .append(msgSegment)
			  .append("，可以入住美房啦，收拾心情准备出发吧！如有疑问请联系小管家热线：4009966633，商家电话：")
			  .append(merchantPhone);
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genPayingOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 价格有变动时通知用户
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @return
	 */
	public static String genConfirmingOrderNotice2User(String beginTime, String endTime, String houseName){
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("您预订的 ")
			  .append(msgSegment)
			  .append("，由于价格变动，请您重新浏览订单，并在30分钟内点击确认并支付订单！如有疑问请联系小管家热线：4009966633");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genConfirmingOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 审核通过后，通知用户支付
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @return
	 */
	public static String genPayingOrderNotice2User(String beginTime, String endTime, String houseName) {
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("恭喜！您预订的")
			  .append(msgSegment)
			  .append("已确认，美房在向您招手！请浏览订单，并在30分钟内点击支付，开启美妙旅程！如有疑问请联系小管家热线：4009966633");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genPayingOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 非用户取消时,通知用户
	 * @param beginTime
	 * @param endTime
	 * @param houseName
	 * @return
	 */
	public static String genCancelOrderNotice2User(String beginTime, String endTime, String houseName) {
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("很抱歉，您预订的")
			  .append(msgSegment)
			  .append("由于太过火爆已被订满 。欢迎继续浏览，更多美房在等您！如有疑问请联系小管家热线：4009966633");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genCancelOrderNotice2User message.", e);
		}
		
		return null;
	}
	
	/**
	 * 通知用户订单已生成，正在审核中
	 * @param biginTime 入住时间
	 * @param endTime 离店时间
	 * @param houseName 客栈名
	 * @return
	 * @throws ParseException 
	 */
	public static String genCreateOrderNotice2User(String beginTime, String endTime, String houseName) throws ParseException
	{
		try {
			String msgSegment = parse(beginTime, endTime, houseName);
			StringBuffer sb = new StringBuffer();
			sb.append("亲， 您预订的")
			  .append(msgSegment)
			  .append("，小管家正火速抢房中，订单将在30分钟内确认，如有疑问请联系小管家热线：4009966633");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to genUserCreateOrderNotice message.", e);
		}
		
		return null;
	}
	
	/**
	 * 会员退房
	 * @param phone
	 * @return
	 */
	public static String genLeavedOrderNotice2User(String phone) {
		return "亲爱的" + phone + "，入住的美房您还满意吗：）期待您再次预订，还有更多美房送您住！如有疑问请联系小管家热线：4009966633";
	}
	
	/**********************************************商户短信************************************************/

	/**
	 * 订单提交之后通知商户
	 * @param roomName
	 * @param orderId
	 * @return
	 */
	public static String genMerchantNotify(String roomName, String orderId) {
		return "您管理的" + roomName + "刚才被人预订，订单号为" + orderId + ",请及时审核预订申请，谢谢。";
	}
	
	
	// 注册
	public static String genRegisterSms(String verifyCode) {
		return "您的注册验证码是" + verifyCode + "，即将注册妈妈送房，有效期为30分钟，欢迎您来到妈妈送房。";
	}

	// 重置密码
	public static String genUpdatePwdSms(String verifyCode) {
		return "您的验证码是" + verifyCode + "，即将重置登录密码，有效期为30分钟，请务必保护好您的验证码。";
	}

	// 修改密码
	public static String genChangePwdSms(String verifyCode) {
		return "您的验证码是" + verifyCode + "，即将修改登录密码，有效期为30分钟，请务必保护好您的验证码。";
	}

	// 旅居券领取（好友赠送）
	public static String genChangeFriendSms(String verifyCode) {
		return "您的验证码是" + verifyCode + ",及时领取旅居券，有效期为1分钟，请务必保护好您的验证码。";
	}
	
	// 修改邮箱
	public static String genUpdateEmailSms(String verifyCode) {
		return "您的验证码是" + verifyCode + "，即将修改绑定邮箱，有效期为30分钟，请务必保护好您的验证码。";
	}

	// 充值
	public static String genRechargeSms(String phone, int amt, int reward) {
		return "尊敬的" + phone + "用户，您已成功在妈妈送房网充值" + String.valueOf(amt) + ".00元，平台已赠送您" + String.valueOf(reward) + "积分，可立即开始订房，感谢您对妈妈送房的支持，希望能为您带来愉快的旅游体验。";
	}

	// 到店付审核成功，到店支付 TO用户
	public static String getOrderStoreToPay(String phone, String orderId,String verifyCode) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + "审核成功，请您凭验证码："+verifyCode+"到酒店前台支付";
	}
	
	// 到店付审核成功，到店支付 TO妈妈客服
	public static String getOrderStoreToPayForMama(String phone, String orderId) {
		return "尊敬的妈妈客服，用户" + phone + "，订单号为" + orderId + "审核成功，凭验证码到酒店前台支付";
	}
	
	// 到店付入住成功，TO妈妈客服
	public static String getOrderStoreSuccessForMama(String phone, String orderId) {
		return "尊敬的妈妈客服，用户" + phone + "，订单号为" + orderId + "入住成功！";
	}
	
	// 预订失败
	public static String genMerchantCancelOrderSms(String phone, String orderId, String roomName, String reason) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + "，预订的" + roomName + "房间因" + reason + "原因预订失败，请您谅解，您可以继续寻找其他合适的房源，谢谢。";
	}

	// 预订成功
	public static String genMerchantVerifiedOrderSms(String phone, String orderId, String roomName, String verifyCode, int amt) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",您刚才预订的" + roomName + "房间已审核通过，您的定金" + String.valueOf(amt) + "积分已扣除，入住验证码为" + verifyCode + "，在入住时向楼盘出示即可，谢谢。";
	}

	// 取消预订
	public static String genCancelOrder(String phone, String roomName, String orderId) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + roomName + "房间已超过入住日期12个小时，本次预订取消，您预付的订金无法退还，欢迎您下次再预订，谢谢。";
	}
	
	// 商家取消预订
	public static String genCancelOrderByCom(String phone,String orderId,String cancelOrdeReason) {
		return "尊敬的妈妈客服，用户" + phone + ",订单号为" + orderId + ",因"+cancelOrdeReason+"已取消。";
	}
	
	// 用户取消预订 for user 
	public static String genCancelOrderByUser(String phone, String orderId) {
		return "尊敬的商家，用户" + phone + ",订单号为" + orderId + "，本次预订已取消，谢谢。";
	}
	
	// 入住成功
	public static String genUserLivedOrderSms(String phone, String roomName, String orderId, int amt) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + roomName + "已登记入住，您的房费" + String.valueOf(amt) + "已扣除，祝您游玩愉快，谢谢。";
	}

	// 续房
	public static String genContinueOrderSms(String phone, String roomName, String date, String orderId) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + roomName + "的离店日期已延长至" + date + "，谢谢。";
	}
	
	//入住提醒
	public static String genUserLivingOrderSms(String phone, String roomName, String date, String orderId)
	{
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + roomName + "的入住日期为" + date + "，请按时入，谢谢。";
	}
	
	//未按时入住
	public static String genUserNotLivedOrderSms(String phone, String roomName, String orderId)
	{
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + roomName + "未按时入住，已自动取消,感谢您使用妈妈送房的服务，期待为您下一次旅途服务，谢谢。";
	}
	
	// 价格修改后，待确认
	public static String genBeDeterminedOrderSms(String phone,String orderId, String roomName, String date, String presentPrice,String originalPrice) {
		return "尊敬的" + phone + "用户，您订单号为" + orderId + ",预订的" + date+roomName + "的房源，原价"+originalPrice+"现价为" + presentPrice + "，请登录网站确认！";
	}
	
	// 价格修改后，待妈妈客服确认
	public static String genBeMamaDeterminedOrderSms(String phone,String orderId, String roomName, String date, String presentPrice,String originalPrice) {
		return "尊敬的妈妈客服用户"+phone+"，订单号为" + orderId + ",预订的" + date+roomName + "的房源，原价"+originalPrice+"现价为" + presentPrice + "！";
	}
	

	
	
	//微信订单待确认点击确定后   【妈妈送房】恭喜！您预订的     月     日                      （客栈名）       间     晚已确认，美房在向您招手！请浏览订单，点击支付，开启美妙旅程！如有疑问请联系小管家热线：4009966633
	public static String genOrderWaittingToUser(String data,String roomName, String daySize) {
		return "【妈妈送房】恭喜！您预订的  "+data+roomName+"1间"+daySize+"晚已确认，美房在向您招手！请浏览订单，点击支付，开启美妙旅程！如有疑问请联系小管家热线：4009966633";
	}
	
	public static String genCanceledOrderSms(String phone, String roomName, String orderId)
	{
		return phone + "用户，订单号为" + orderId + ",预订的" + roomName + "房间已超过入住日期12个小时，本次预订取消。"; 
	}
	
	/**
	 * 确认参加单身party回执短信
	 * @param phone
	 * @return
	 */
	public static String genActivityEnroll(String phone) {
		return "亲爱的" + phone + "，您已确认参加！";
	}
	
	//      月     日到      月     日                     （客栈名）      晚
	private static String parse(String beginTime, String endTime, String houseName){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long begin = sdf.parse(beginTime).getTime();
			long end = sdf.parse(endTime).getTime();		
			
			//天数
			long days = (end - begin) / ONE_DAY_IN_MILLIONS;
			
			//beginTimes 下标 [0]代表年、[1]代表月、[2]代表日
			//endTimes   下标 [0]代表年、[1]代表月、[2]代表日
			String[] beginTimes = beginTime.split("-");
			String[] endTimes = endTime.split("-");
			
			StringBuffer sb = new StringBuffer();
			sb.append(beginTimes[1] + "月")
			.append(beginTimes[2] + "日到")
			.append(endTimes[1] + "月")
			.append(endTimes[2] + "日")
			.append(houseName)
			.append(days + "晚");
			
			return sb.toString();
			
		} catch (Exception e) {
			LOGGER.error("Failed to parse date.",e);
		}
		
		return null;
	}
}
