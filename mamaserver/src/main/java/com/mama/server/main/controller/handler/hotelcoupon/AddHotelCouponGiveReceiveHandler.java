package com.mama.server.main.controller.handler.hotelcoupon;

import com.mama.server.main.controller.ConstValue;
import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.HotelCouponGivePo;
import com.mama.server.main.dao.model.SmsPo;
import com.mama.server.main.service.MainService;
import com.meidusa.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AddHotelCouponGiveReceiveHandler extends BaseHandler
{
  public HashMap<String, Object> handle(HashMap<String, Object> param)
  {
    try
    {
      String phoneNumber = (String)param.get("phoneNumber");
      String verifyCode = (String)param.get("verifyCode");
      String giveCode = (String)param.get("giveCode");
      this.log.info("AddHotelCouponGiveReceiveHandler request. phoneNumber:{}, verifyCode:{}, giveCode:{}", new Object[] { phoneNumber, verifyCode, giveCode });
      if (StringUtils.isBlank(phoneNumber)) {
        genErrOutputMapWithCode("param error, phoneNumber required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (StringUtils.isBlank(verifyCode)) {
        genErrOutputMapWithCode("param error, verifyCode required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      if (StringUtils.isBlank(giveCode)) {
        genErrOutputMapWithCode("param error, giveCode required", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      SmsPo sms = new SmsPo();
      sms.setPhone(phoneNumber);
      sms.setContent(verifyCode);
      sms.setType(ConstValue.SMS_HOTEL_COUPON_GIVE);

      List smsList = this.mainService.getSmsByAllParam(sms);
      if ((smsList == null) || (smsList.size() == 0)) {
        genErrOutputMapWithCode("verify code incorrect", ReturnCode.PHONE_VERIFY_FAILED);
        return this.outputMap;
      }
      sms = (SmsPo)smsList.get(0);
      if (sms.getUsed() == 1) {
        genErrOutputMapWithCode("verify code incorrect", ReturnCode.PHONE_VERIFY_FAILED);
        return this.outputMap;
      }

      Map params = new HashMap();
      params.put("giveCode", giveCode);
      List gives = this.mainService.getHotelCouponGiveList(params);
      if ((gives == null) || (gives.isEmpty())) {
        genErrOutputMapWithCode("param error, invalid giveCode", ReturnCode.PARAM_ERROR);
        return this.outputMap;
      }
      HotelCouponGivePo givePo = (HotelCouponGivePo)gives.get(0);
      if (givePo.getStatus() != 0) {
        genErrOutputMapWithCode("param error, hotel coupon give status fail.", ReturnCode.HOTEL_COUPON_GIVE_DONE_ERROR);
        return this.outputMap;
      }
      JSONObject showDetailJson = JSONObject.parseObject(givePo.getShowDetail());
      int copiesCount = showDetailJson.getIntValue("copiesCount");
      if (givePo.getReceivedCount() >= copiesCount) {
        this.mainService.updateHotelCouponGiveStatus(givePo.getId(), 1);
        genErrOutputMapWithCode("param error, hotel coupon give done.", ReturnCode.HOTEL_COUPON_GIVE_DONE_ERROR);
        return this.outputMap;
      }

      sms.setUsed(1);
      if (this.mainService.updateSmsById(sms) != 0) {
        genErrOutputMapWithCode("verify failed", ReturnCode.UPDATE_VERIFY_CODE_ERROR);
        return this.outputMap;
      }

      this.mainService.giveHotelCoupon(givePo, phoneNumber);

      genSuccOutputMap();
    } catch (Exception e) {
      e.printStackTrace();
      genErrOutputMap("interface error");
    }
    return this.outputMap;
  }
}