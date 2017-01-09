package com.mama.server.main.controller.handler.main;

import java.util.HashMap;
import java.util.List;





import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.OrderPo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.util.Log;
import com.mama.server.util.PropertiesUtils;
import com.mama.server.util.SmsUtil;
import com.meidusa.fastjson.JSONObject;

@Component
public class SendOrderPayCodeHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
    	try {
            String uid = "";
            if (param.get("uid") != null) {
               uid = (String)param.get("uid");
            }
            
            String orderId="";
            if (param.get("orderId") != null) {
            	orderId = (String)param.get("orderId");
             }
            OrderPo order=new OrderPo();
            String LiveDetail="",linkmanPhone="";
            order.setOrderId(orderId);
            
            //根据订单号获取订单信息
            List<OrderPo> orderList=mainService.getOrderByAllParam(order);
            
            String userPhone = null;
            String beginTime = null;
            String endTime = null;
            String houseName = null;
            String merchantPhone = null;
            int totalAmt = 0;
            int payAmt = 0;
            
            //获取预定手机号
            if(orderList.size()>0){
            	LiveDetail=orderList.get(0).getLiveDetail();
            	JSONObject showDetailJsonObject = JSONObject.parseObject(LiveDetail);
            	linkmanPhone=showDetailJsonObject.getString("linkmanPhone");
            	userPhone = showDetailJsonObject.getString("user_phone");
            	beginTime = showDetailJsonObject.getString("beginTime");
            	endTime = showDetailJsonObject.getString("endTime");
            	houseName = showDetailJsonObject.getString("houseName");
            	merchantPhone = showDetailJsonObject.getString("phone");
            	totalAmt = orderList.get(0).getTotalAmt();
            	payAmt = orderList.get(0).getPayAmt();
            }
            
            //获取验证码
            String verifyCode = mainService.genVerifyCode(3);
            Log.SERVICE.debug("verifyCode:" + verifyCode);
            SmsPo sms = new SmsPo();
            sms.setType(3);
            sms.setContent(verifyCode);
            sms.setPhone(linkmanPhone);
            sms.setStatus(0);
            sms.setUsed(0);
            sms.setUid(uid);
            if (mainService.insSms(sms) != 0) {
                genErrOutputMap("fail to send sms");
                return outputMap;
            }
            order.setVerifyCode(verifyCode);
            order.setStatus(ConstValue.ORDER_MERCHANT_VERIFIED);
            if(mainService.updateOrder(order)!=0){
            	genErrOutputMap("fail to verifyCode update");
                return outputMap;
            }
            
            //通知用户
            //如果预订人就是入住人,则只发送一条短信
            if (userPhone.equals(linkmanPhone)) {
            	String msg = SmsUtil.genLivingOrderNotice2User(beginTime, endTime, houseName, merchantPhone);
            	SmsUtil.sendSms(linkmanPhone, msg); // 发送短信
			}
            else {
            	//通知预订人
				String userMsg = SmsUtil.genPaidOrderNotice2Booker(beginTime, endTime, houseName, merchantPhone);
				SmsUtil.sendSms(userPhone, userMsg);
				
				//通知入住人
				String linkManMsg = SmsUtil.genPaidOrderNotice2User(beginTime, endTime, houseName, merchantPhone, userPhone);
				SmsUtil.sendSms(linkmanPhone, linkManMsg);
			}
            
            //通知妈妈小管家和财务
            String mamaMsg = SmsUtil.genLivingOrderNotice2Mama(beginTime, endTime, houseName, linkmanPhone, orderId, totalAmt, payAmt);
            SmsUtil.sendSms(PropertiesUtils.getMmManagerPhone(), mamaMsg);
            SmsUtil.sendSms(PropertiesUtils.getMmFinancePhone(), mamaMsg);
            
            dataMap.put("verifyCode", verifyCode);
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
