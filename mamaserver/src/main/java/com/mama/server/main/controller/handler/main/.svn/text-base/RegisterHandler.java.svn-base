package com.mama.server.main.controller.handler.main;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.UserCardPo;
import com.mama.server.main.dao.model.UserInfoPo;

@Component
public class RegisterHandler extends BaseHandler {

    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
            if (param.get("phone") == null) {
                genErrOutputMapWithCode("param error, phone required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            if (param.get("password") == null) {
                genErrOutputMapWithCode("param error, password required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
//            if (param.get("verifyCode") == null) {
//                genErrOutputMapWithCode("param error, verifyCode required", ReturnCode.PARAM_ERROR);
//                return outputMap;
//            }
            String nickName = "";
            if(null != param.get("nickName")){
            	nickName = (String)param.get("nickName");
            }
            String name = "";
            if(null != param.get("name")){
            	name = (String)param.get("name");
            }
            String idCard = "";
            if(null != param.get("idCard")){
            	idCard = (String)param.get("idCard");
            }
            
            
            String phone = (String)param.get("phone");
            String password = (String)param.get("password");
//            String verifyCode = (String)param.get("verifyCode");
            int channel = ConstValue.USER_WEB;
            
            if (param.get("channel") != null) {
                channel = (Integer)param.get("channel");
            }
            
            if (channel != ConstValue.USER_WEB && channel != ConstValue.USER_ANDROID && 
                    channel != ConstValue.USER_IOS) {
                genErrOutputMapWithCode("param error, channel invalid", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
//            SmsPo sms = new SmsPo();
//            sms.setPhone(phone);
//            sms.setContent(verifyCode);
//            sms.setType(ConstValue.SMS_REGISTER);
//            
//            List<SmsPo> smsList = mainService.getSmsByAllParam(sms);
//            if (smsList == null || smsList.size() == 0) {
//                genErrOutputMapWithCode("verify code incorrect", ReturnCode.PHONE_VERIFY_FAILED);
//                return outputMap;
//            }
//            sms = smsList.get(0);
//            if (sms.getUsed() == 1) {
//                genErrOutputMapWithCode("verify code incorrect", ReturnCode.PHONE_VERIFY_FAILED);
//                return outputMap;
//            }
//            sms.setUsed(1);
//            if (mainService.updateSmsById(sms) != 0) {
//                genErrOutputMapWithCode("verify failed", ReturnCode.UPDATE_VERIFY_CODE_ERROR);
//                return outputMap;
//            }
            
            
            String friendCode = (String) param.get("friendCode");	// 邀请人手机号
            boolean isInvited = false;
            String inviterUid = null;	// 邀请人uid
            if(StringUtils.isNotBlank(friendCode)) {
            	// 判断朋友码是否存在
            	inviterUid = mainService.getUidByPhone(friendCode);
            	if(inviterUid == null) {
            		genErrOutputMapWithCode("param error, invalid friend code", ReturnCode.PARAM_ERROR);
                    return outputMap;
            	}
            	isInvited = true;
            }
            
            
            UserInfoPo userInfo = new UserInfoPo();
            userInfo.setUid(mainService.getTimeToMd5());
            userInfo.setName(name);
            userInfo.setIdCard(idCard);
            userInfo.setType(1);
            userInfo.setIcon("");
            userInfo.setEmail("");
            userInfo.setNickName(nickName);
            userInfo.setRemoved(0);
            userInfo.setPhone(phone);
            userInfo.setPasswordStamp(String.valueOf(new Date().getTime()));
            userInfo.setPassword(mainService.encodePassword(password, userInfo.getPasswordStamp()));
            if(isInvited) {
            	userInfo.setInviterPhone(friendCode);
            }
            String openId = (String)param.get("openId");//注册人微信号
            if(StringUtils.isNotBlank(openId)) {
            	userInfo.setOpenId(openId);
            }
            
            if (mainService.insertUserInfo(userInfo) == -1) {
                genErrOutputMapWithCode("register failed", ReturnCode.REGISTER_FAILED);
                return outputMap;
            }
            
            UserCardPo userCardPo = new UserCardPo();
            List<String> cardStr = mainService.genUserCard();
            userCardPo.setCardId(cardStr.get(0));
            userCardPo.setCardPassword(cardStr.get(1));
            userCardPo.setUid(userInfo.getUid());
            userCardPo.setAvailAmt(0);
            userCardPo.setFreezeAmt(0);
            userCardPo.setChannel(0);
            userCardPo.setStatus(0);
            userCardPo.setTotalRechargeAmt(0);
            userCardPo.setTotalRewardAmt(0);
            userCardPo.setOperTime(mainService.getCurrentDatetime());
            userCardPo.setLevel(1);
            userCardPo.setType(1);
            userCardPo.setChannel(channel);
            
            if (mainService.insertUserCard(userCardPo) != 0) {
                genErrOutputMapWithCode("register failed", ReturnCode.GENERATE_USER_CARD_FAILED);
                return outputMap;
            }
            
            dataMap.put("uid", userInfo.getUid());
            if(isInvited) {
            	dataMap.put("inviterUid", inviterUid);
            	// 返回邀请人的邀请人数
            	dataMap.put("invitedNumber", mainService.getInvitedNumberByPhone(friendCode));
            }
            genSuccOutputMap();
        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
