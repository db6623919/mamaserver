package com.mmzb.house.app.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.CouponVo;
import com.mmzb.house.app.vo.CouponsVo;
import com.mmzb.house.app.vo.HotelCouponGroupVo;
import com.mmzb.house.app.vo.HotelCouponGroupsVo;
import com.mmzb.house.app.vo.UserInfoVo;
import com.netfinworks.authorize.ws.dataobject.MemberToken;
import com.netfinworks.common.util.DateUtil;

/**
 * 旅居券
 * @author whl
 *
 */
@Controller
public class CouponAction {
	
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
	@Autowired
	private MemberTokenService memberTokenService;
	
	private Logger logger = LoggerFactory.getLogger(CouponAction.class);
	
	/**
	 * 查询旅居券
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/my/myCoupons", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<CouponsVo> myCoupons(HttpServletRequest request,HttpServletResponse response) {
		
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/myCoupons");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
		try {
			
			Map<String,Object> postData = new HashMap<String,Object>();
			postData.put("mmWalletId", userInfo.getMemberId());
			if(null != request.getParameter("houseId")) {
				postData.put("houseId", Integer.parseInt(request.getParameter("houseId")));
				postData.put("statusIds", Arrays.asList(0));
			}
			
			if(null != request.getParameter("checkinDate")) {
				Long checkinDate = Long.parseLong(request.getParameter("checkinDate"));
				Date date = new Date(checkinDate);
				postData.put("expireTimeFrom", sdf.format(date));
				//postData.put("expireTimeFrom", DateUtil.format(DateUtil.addHours(new Date(), 24), "yyyy-MM-dd"));
			}
			
			Response<HotelCouponGroupsVo> httpResult = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHotelCouponGroups",HotelCouponGroupsVo.class);
			if(httpResult.getCode().equals("0")) {
				HotelCouponGroupsVo hotelCouponGroups = httpResult.getData();
				//Vo转换
				List<CouponVo> coupons = couponVoToCouponsVo(hotelCouponGroups.getHotelCouponGroups());
				CouponsVo couponsVo = new CouponsVo();
				couponsVo.setCoupons(coupons);
				
				if (logger.isInfoEnabled()) {
					logger.info("end to run /app/myCoupons");
				}
				
				return new ResponseOut<CouponsVo>(Constants.APP_SUCCESSED,"",couponsVo);
			} else {
				logger.error("/app/myCoupons:旅居券查询失败.");
				return new ResponseOut<CouponsVo>(Constants.APP_FAILED,"旅居券查询失败.",null);
			}
			
		} catch ( Exception e) {
			logger.error("/app/myCoupons:旅居券查询失败.",e);
			return new ResponseOut<CouponsVo>(Constants.APP_FAILED,"旅居券查询失败.",null);
		}
		
	}
	
	public List<CouponVo> couponVoToCouponsVo(List<HotelCouponGroupVo> hotelCouponGroups) {
		List<CouponVo> coupons = new ArrayList<CouponVo>();
		for(int i = 0 ; i < hotelCouponGroups.size() ; i ++ ) {
			HotelCouponGroupVo hotelCouponGroup = hotelCouponGroups.get(i);
			CouponVo coupon = new CouponVo();
			coupon.setHotelCouponIds(hotelCouponGroup.getHotelCouponIds());
			coupon.setCouponState(hotelCouponGroup.getStatus());
			coupon.setCouponName(hotelCouponGroup.getName());
			coupon.setHouseId(hotelCouponGroup.getHouseId());
			coupon.setCouponDeadline(hotelCouponGroup.getExpireTime().getTime());
			coupons.add(coupon);
		}
		return coupons;
	}

}
