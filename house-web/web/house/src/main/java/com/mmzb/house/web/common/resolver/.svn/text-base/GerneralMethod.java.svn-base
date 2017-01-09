package com.mmzb.house.web.common.resolver;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.meidusa.fastjson.JSONObject;
import com.mmzb.charge.domain.common.RewardTypeEnum;
import com.mmzb.charge.domain.common.VirtualFlowTypeEnum;
import com.mmzb.charge.facade.ChargeFacade;
import com.mmzb.charge.facade.RewardFacade;
import com.mmzb.charge.facade.VirtualFacade;
import com.mmzb.charge.facade.entity.AccountBuildRequest;
import com.mmzb.charge.facade.entity.AccountBuildResponse;
import com.mmzb.charge.facade.entity.ChargeQueryRequest;
import com.mmzb.charge.facade.entity.ChargeQueryResponse;
import com.mmzb.charge.facade.entity.OfflineChargeQueryResponse;
import com.mmzb.charge.facade.entity.RewardCalRequest;
import com.mmzb.charge.facade.entity.RewardCalResponse;
import com.mmzb.charge.facade.entity.RewardInsertRequest;
import com.mmzb.charge.facade.entity.RewardInsertResponse;
import com.mmzb.charge.facade.entity.VirtualFlowEntity;
import com.mmzb.charge.facade.entity.VirtualFlowQueryRequest;
import com.mmzb.charge.facade.entity.VirtualFlowQueryResponse;
import com.mmzb.house.web.action.base.ApplicationConfig;
import com.mmzb.house.web.action.dto.BuildingInfo;
import com.mmzb.house.web.action.dto.CityInfo;
import com.mmzb.house.web.action.dto.CollectsInfo;
import com.mmzb.house.web.action.dto.DeveloperInfo;
import com.mmzb.house.web.action.dto.FlowInfo;
import com.mmzb.house.web.action.dto.HouseInfo;
import com.mmzb.house.web.action.dto.HousePrice;
import com.mmzb.house.web.action.dto.OpenIdInfo;
import com.mmzb.house.web.action.dto.OrderInfo;
import com.mmzb.house.web.action.dto.UserCardInfo;
import com.mmzb.house.web.action.dto.UserInfo;
import com.mmzb.house.web.action.util.StringUtils;
import com.mmzb.house.web.core.common.util.AppProperties;
import com.mmzb.house.web.core.common.util.HttpClientPostMethod;

/**
 * 
 * @ClassName: GerneralMethod
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Lijiaqi
 * @date 2015年9月19日 上午10:27:56
 */
@Service
public class GerneralMethod {

	@Resource(name = "appProperties")
	private AppProperties appProperties;

	private static Logger logger = LoggerFactory.getLogger(GerneralMethod.class);

	/**
	 * 
	 * @Title: getDeveloperInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         devId @return @return DeveloperInfo 返回类型 @throws
	 */
	public DeveloperInfo getDeveloperInfo(int devId) {
		DeveloperInfo dev = new DeveloperInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("devId", devId);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getDeveloperInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					dev.setDevId(jsonObject.getInt("devId"));
					dev.setName(jsonObject.getString("name"));
					dev.setType(jsonObject.getInt("type"));
					dev.setMark(jsonObject.getString("mark"));
					net.sf.json.JSONObject detail = net.sf.json.JSONObject.fromObject(jsonObject.get("showDetail"));
					dev.setDescription(detail.containsKey("description") ? detail.getString("description") : "");
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return dev;
	}

	/**
	 * 
	 * @Title: getBuildingInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         bldId @return @return BuildingInfo 返回类型 @throws
	 */
	public BuildingInfo getBuildingInfo(int bldId) {
		BuildingInfo bldInfo = new BuildingInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("bldId", bldId);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getBuildingInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					bldInfo.setDevId(jsonObject.getInt("devId"));
					bldInfo.setCityId(jsonObject.getInt("cityId"));
					bldInfo.setProvId(jsonObject.getInt("provId"));
					bldInfo.setName(jsonObject.getString("name"));
					bldInfo.setType(jsonObject.getInt("type"));
					bldInfo.setMark(jsonObject.getString("mark"));
					net.sf.json.JSONObject detail = net.sf.json.JSONObject.fromObject(jsonObject.get("showDetail"));
					bldInfo.setDescription(detail.containsKey("description") ? detail.getString("description") : "");
					bldInfo.setHouseExplain(detail.containsKey("houseExplain") ? detail.getString("houseExplain") : "");
					bldInfo.setTelphone(detail.containsKey("telphone") ? detail.getString("telphone") : "");
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return bldInfo;
	}

	/**
	 * 
	 * @Title: getHouseInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         houseId @return @return HouseInfo 返回类型 @throws
	 */
	public HouseInfo getHouseInfo(int houseId) {

		HouseInfo house = new HouseInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		List<String> ss = new ArrayList<String>();
		List<String> pt = new ArrayList<String>();
		List<String> qt = new ArrayList<String>();
		try {
			postData.put("houseId", houseId);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHouseInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					house.setDevId(jsonObject.getInt("devId"));
					house.setBldId(jsonObject.getInt("bldId"));
					house.setHouseId(jsonObject.getInt("houseId"));
					//@SuppressWarnings("unchecked")
					//List<Integer> list = (List<Integer>) jsonObject.get("flag");
					house.setFlag(jsonObject.getInt("flag"));

					house.setMemFreezeAmt(jsonObject.getInt("memFreezeAmt"));
					house.setCityId(jsonObject.getInt("cityId"));
					house.setType(jsonObject.getInt("type"));
					house.setRoom(String.valueOf(jsonObject.getInt("room")));
					house.setFreezeAmt(jsonObject.getInt("freezeAmt"));
					house.setSummaryInfo(jsonObject.getString("summaryInfo"));
					net.sf.json.JSONObject summary = net.sf.json.JSONObject.fromObject(house.getSummaryInfo());
					house.setHouseName(summary.getString("houseName"));
					BigDecimal totalAmt = new BigDecimal(jsonObject.getInt("totalAmt"));
					BigDecimal memTotalAmt = new BigDecimal(jsonObject.getInt("memTotalAmt"));
					house.setDiscount(memTotalAmt.divide(totalAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(10)).setScale(1).toPlainString());
					house.setTotalAmt(totalAmt.intValue());
					house.setMemTotalAmt(memTotalAmt.intValue());
					house.setLuxury(jsonObject.getInt("luxury"));
					house.setTheme(jsonObject.getInt("theme"));
					@SuppressWarnings("unchecked")
					List<Integer> mark = (List<Integer>) jsonObject.get("mark");
					house.setMark(mark);
					@SuppressWarnings("unchecked")
					List<String> tagNameList = (List<String>) jsonObject.get("tagNameList");
					house.setTagNameList(tagNameList);
					
					house.setOperTime(jsonObject.getString("operTime"));
					house.setTotalRoomNum(jsonObject.getInt("totalRoomNum"));
					// detail
					net.sf.json.JSONObject detail = net.sf.json.JSONObject.fromObject(jsonObject.get("showDetail"));
					house.setAddress(detail.getString("address"));
					house.setBedType(detail.getString("bedType"));
					house.setCheckInTime(detail.getString("checkInTime"));
					house.setCheckOutTime(detail.getString("checkOutTime"));
					house.setCheckdesc(detail.getString("checkdesc"));
					if (detail.containsKey("inSeasonDesc")) {
						house.setInSeasonDesc(detail.getString("inSeasonDesc"));
					}
					house.setHouseArea(detail.getString("houseArea"));
					house.setHouseType(detail.getString("houseType"));
					house.setIsInvoice(detail.getString("isInvoice"));
					house.setLatitude(detail.getString("latitude"));
					house.setLongitude(detail.getString("longitude"));
					house.setTelephone(detail.getString("telephone"));
					house.setVideoUrl(detail.getString("videoUrl"));
					house.setIntroductionImg(detail.getString("introductionImg").split(","));
					house.setIntroductionName(detail.getString("introductionName").split(","));
					// facilities
					net.sf.json.JSONObject facilities = net.sf.json.JSONObject.fromObject(detail.get("facilities"));
					Set<String> set = facilities.keySet();
					for (String str : set) {
						String temp = facilities.getString(str);
						if ("1".equals(temp))
							ss.add(ApplicationConfig.houseconfigMap.get(str));
					}
					house.setSsList(ss);
					house.setAidkit(facilities.getString("aidkit"));
					house.setConditione(facilities.getString("conditione"));
					house.setDrinking(facilities.getString("drinking"));
					house.setDryer(facilities.getString("dryer"));
					house.setExtinguisher(facilities.getString("extinguisher"));
					house.setFridge(facilities.getString("extinguisher"));
					house.setHairdrier(facilities.getString("hairdrier"));
					house.setHeater(facilities.getString("heater"));
					house.setHeating(facilities.getString("heating"));
					house.setHotpot(facilities.getString("hotpot"));
					house.setShampoo(facilities.getString("shampoo"));
					house.setShower(facilities.getString("shower"));
					house.setSlipper(facilities.getString("slipper"));
					house.setSmokedetector(facilities.getString("smokedetector"));
					house.setTooth(facilities.getString("tooth"));
					house.setTowels(facilities.getString("towels"));
					house.setTv(facilities.getString("tv"));
					house.setWashing(facilities.getString("washing"));
					// supporting
					net.sf.json.JSONObject supporting = net.sf.json.JSONObject.fromObject(detail.get("supporting"));
					Set<String> set1 = supporting.keySet();
					for (String str : set1) {
						String temp = supporting.getString(str);
						if ("1".equals(temp))
							pt.add(ApplicationConfig.houseconfigMap.get(str));
					}
					house.setPtList(pt);
					house.setAccesscard(supporting.getString("accesscard"));
					house.setBroadband(supporting.getString("broadband"));
					house.setBuzzer(supporting.getString("buzzer"));
					house.setElevator(supporting.getString("elevator"));
					house.setGym(supporting.getString("gym"));
					house.setParking(supporting.getString("parking"));
					house.setPlayground(supporting.getString("playground"));
					house.setSecuritystaff(supporting.getString("securitystaff"));
					house.setStore(supporting.getString("store"));
					house.setSwimming(supporting.getString("swimming"));
					house.setWheelchair(supporting.getString("wheelchair"));
					// other
					net.sf.json.JSONObject other = net.sf.json.JSONObject.fromObject(detail.get("other"));
					Set<String> set2 = other.keySet();
					for (String str : set2) {
						String temp = other.getString(str);
						if ("1".equals(temp))
							qt.add(ApplicationConfig.houseconfigMap.get(str));
					}
					house.setQtList(qt);
					house.setBreakfast(other.getString("breakfast"));
					house.setChildrenstay(other.getString("childrenstay"));
					house.setCook(other.getString("cook"));
					house.setExtrabed(other.getString("extrabed"));
					house.setGuests(other.getString("guests"));
					house.setParty(other.getString("party"));
					house.setPet(other.getString("pet"));
					house.setSmoking(other.getString("smoking"));
					
					setHouseComments(house, jsonObject);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}
		catch(Exception e)
		{
			logger.error("error:", e);
		}

		return house;

	}

	private void setHouseComments(HouseInfo house,
			net.sf.json.JSONObject jsonObject)
	{
		house.setAverageScore(jsonObject.getDouble("averageScore"));
		house.setTotalCommentsNum(jsonObject.getInt("totalCommentsNum"));
		
		String comment = jsonObject.getString("comment");
		if (StringUtils.isNotEmpty(comment))
		{
			//如果评论字数大于50字，则50字以外显示...
			if (comment.length() > 50)
			{
				comment = comment.substring(0, 50) + "...";
			}
			house.setComment(comment);
		}
	}

	/**
	 * 
	 * @Title: getUserCardInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         uid @return @return UserCardInfo 返回类型 @throws
	 */
	public UserCardInfo getUserCardInfo(String uid) {

		UserCardInfo uci = new UserCardInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCardInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					uci.setAvailAmt(jsonObject.getInt("availAmt"));
					uci.setTotalRechargeAmt(jsonObject.getInt("totalRechargeAmt"));
					// uci.setTotalRewardAmt(jsonObject.getInt("totalRewardAmt"));
					uci.setLevel(jsonObject.getInt("level"));
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return uci;
	}

	/**
	 * 
	 * @Title: getUserInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         uid @return @return UserInfo 返回类型 @throws
	 */
	public UserInfo getUserInfo(String uid, String phone, String openId) {
		UserInfo userInfo = new UserInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(uid))
			postData.put("uid", uid);
		if (StringUtils.isNotEmpty(phone))
			postData.put("phone", phone);
		if (StringUtils.isNotEmpty(openId))
			postData.put("openId", openId);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getUserInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					userInfo.setUid(jsonObject.getString("uid"));
					userInfo.setEmail(jsonObject.containsKey("email") ? jsonObject.getString("email") : "");
					userInfo.setIcon(jsonObject.containsKey("icon") ? jsonObject.getString("icon") : "");
					userInfo.setIdCard(jsonObject.containsKey("idCard") ? jsonObject.getString("idCard") : "");
					userInfo.setName(jsonObject.containsKey("name") ? jsonObject.getString("name") : "");
					userInfo.setNickName(jsonObject.containsKey("nickName") ? jsonObject.getString("nickName") : "");
					userInfo.setPhone(jsonObject.containsKey("phone") ? jsonObject.getString("phone") : "");
					if (jsonObject.containsKey("openId"))
						userInfo.setOpenId(jsonObject.getString("openId"));
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return userInfo;
	}

	/**
	 * 
	 * @Title: getCityInfos @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         list @param isHot @return @return List<CityInfo> 返回类型 @throws
	 */
	public List<CityInfo> getCityInfos(List<Integer> list, int isHot) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		if (list != null)
			postData.put("provIds", list);
		if (isHot != 0)
			postData.put("isHot", isHot);
		List<CityInfo> cityList = new ArrayList<CityInfo>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCities");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					JSONArray arrayCitys = JSONArray.fromObject(result.get("cities"));
					for (int j = 0; j < arrayCitys.size(); j++) {
						CityInfo city = new CityInfo();
						net.sf.json.JSONObject cityObject = arrayCitys.getJSONObject(j);
						city.setCityId(cityObject.getInt("cityId"));
						city.setProvId(cityObject.getInt("provId"));
						city.setRoomNum(String.valueOf(cityObject.getInt("roomNum")));
						city.setHouseNum(String.valueOf(cityObject.getInt("houseNum")));
						city.setCityName(cityObject.getString("cityName"));
						city.setProvName(ApplicationConfig.provinceshuMap.get(city.getProvId()));
						cityList.add(city);
					}

				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return cityList;
	}

	/**
	 * 
	 * @Title: getOrderInfo @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         uid @param orderId @return @return OrderInfo 返回类型 @throws
	 */
	public OrderInfo getOrderInfo(String uid, String orderId) {
		OrderInfo orderInfo = new OrderInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		postData.put("orderId", orderId);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrderInfo");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			map.put("msg", msg);
			map.put("code", code);
			if ("0".equals(code)) {
				JSONObject object = result.getJSONObject("data");
				orderInfo.setOrderId(object.getString("orderId"));
				orderInfo.setHouseId(object.getString("houseId"));
				orderInfo.setFreezeAmt(object.getIntValue("freezeAmt"));
				orderInfo.setTotalAmt(String.valueOf(object.get("totalAmt")));
				orderInfo.setPayAmt(String.valueOf(object.get("payAmt")));
				orderInfo.setStatus(String.valueOf(object.get("status")));
				orderInfo.setVerifyCode(object.getString("verifyCode"));
				JSONObject detail = JSONObject.parseObject(object.getString("liveDetail"));
				orderInfo.setLinkmanName(detail.getString("linkmanName"));
				orderInfo.setLinkmanPhone(detail.getString("linkmanPhone"));
				orderInfo.setPrepayment(detail.getIntValue("prepayment"));
				orderInfo.setHouseName(detail.getString("houseName"));
				orderInfo.setMemTotalAmt(detail.getIntValue("memTotalAmt"));
				orderInfo.setBeginTime(detail.getString("beginTime"));
				orderInfo.setEndTime(detail.getString("endTime"));
				orderInfo.setAmmount(detail.getIntValue("ammount"));
				orderInfo.setPay_type(object.getString("pay_type"));
				if (detail.containsKey("hotelCouponIds")) {
					com.meidusa.fastjson.JSONArray hotelCouponIdsJsonArray = detail.getJSONArray("hotelCouponIds");
					if (hotelCouponIdsJsonArray != null && hotelCouponIdsJsonArray.size() > 0) {
						List<Long> hotelCouponIds = new ArrayList<Long>();
						for (int i = 0; i < hotelCouponIdsJsonArray.size(); i++) {
							hotelCouponIds.add(hotelCouponIdsJsonArray.getLongValue(i));
						}
						orderInfo.setHotelCouponCount(hotelCouponIds.size());
					}
				}
				if (detail.containsKey("hotelCouponDiscountAmt")) {
					orderInfo.setHotelCouponDiscountAmt(detail.getIntValue("hotelCouponDiscountAmt"));
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return orderInfo;
	}

	/**
	 * 
	 * @Title: getHousePrice @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         houseId @param dates @return @return List
	 *         <HousePrice> 返回类型 @throws
	 */
	public List<HousePrice> getHousePrice(int houseId, List<String> dates) {
		List<HousePrice> price = new ArrayList<HousePrice>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("houseId", houseId);
		if (null != dates)
			postData.put("dates", dates);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getHousePrice");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			if ("0".equals(code)) {
				net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(result.get("data"));
				JSONArray jsonArray = object.getJSONArray("prices");
				for (int i = 0; i < jsonArray.size(); i++) {
					net.sf.json.JSONObject indexJson = jsonArray.getJSONObject(i);
					HousePrice hou = new HousePrice();
					hou.setDate(indexJson.getString("date"));
					hou.setTotalAmt(indexJson.getInt("totalAmt"));
					hou.setFreezeAmt(indexJson.getInt("freezeAmt"));
					hou.setMemTotalAmt(indexJson.getInt("memTotalAmt"));
					hou.setMemFreezeAmt(indexJson.getInt("memFreezeAmt"));
					price.add(hou);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return price;
	}

	/**
	 * 
	 * @Title: getPhoneInfo @Description: 通过手机号获取用户是否注册，是否有充值记录，是否已实名认证 @param
	 *         uid @return @return Map<String,Integer> 返回类型 @throws
	 */
	public Map<String, Integer> getPhoneInfo(String phone) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", phone);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "isPhoneRegistered");
			String code = result.getString("code");
			if ("0".equals(code)) {
				net.sf.json.JSONObject jsonData = net.sf.json.JSONObject.fromObject(result.get("data"));
				map.put("registered", jsonData.getInt("registered"));
				map.put("recharged", jsonData.getInt("recharged"));
				map.put("verified", jsonData.getInt("verified"));
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return map;
	}

	/**
	 * 
	 * @Title: getFlowByPage @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         uid @param pageNum @param pageCount @param types @return @return
	 *         List<FlowInfo> 返回类型 @throws
	 */
	public List<FlowInfo> getFlowByPage(String uid, int pageNum, int pageCount, List<Integer> types) {
		List<FlowInfo> resList = new ArrayList<FlowInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		postData.put("pageNum", pageNum);
		postData.put("pageCount", pageCount);
		if (CollectionUtils.isNotEmpty(types))
			postData.put("types", types);
		postData.put("sortByTime", 1);
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getFlows");
			String code = result.getString("code");
			if ("0".equals(code)) {
				net.sf.json.JSONObject jsonData = net.sf.json.JSONObject.fromObject(result.get("data"));
				JSONArray arrayJson = jsonData.getJSONArray("flows");
				for (int i = 0; i < arrayJson.size(); i++) {
					FlowInfo index = new FlowInfo();
					net.sf.json.JSONObject indexJson = arrayJson.getJSONObject(i);
					index.setAmt(indexJson.getInt("amt"));
					index.setFlowId(indexJson.getInt("flowId"));
					index.setOperTime(indexJson.getString("operTime"));
					index.setShowDetail(indexJson.getString("showDetail"));
					index.setType(indexJson.getInt("type"));
					index.setTotalRecords(jsonData.getInt("totalNum"));
					resList.add(index);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("错误日志", e);
		} catch (IOException e) {
			logger.error("错误日志", e);
		}

		return resList;
	}

	public void insertOpenIdInfo(OpenIdInfo oi) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", oi.getPhone());
		postData.put("openId", oi.getOpenId());
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "insertOpenIdInfo");
			if (0 == result.getIntValue("code")) {
				logger.info("插入用户手机与微信号成功");
			} else {
				logger.info("插入用户手机与微信号错误:" + result.getString("msg"));
			}

		} catch (Exception e) {
			logger.error("插入用户手机与微信号联系信息异常", e);
		}
	}

	public Map<String, Integer> checkPhoneSms(String phone) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", phone);
		Map<String, Integer> res = new HashMap<String, Integer>();
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "checkPhone");
			net.sf.json.JSONObject detailJson = net.sf.json.JSONObject.fromObject(result.get("data"));
			res.put("isOutLimitCount", detailJson.getInt("isOutLimitCount"));
			res.put("isOutDifTime", detailJson.getInt("isOutDifTime"));
		} catch (Exception e) {
			logger.error("验证手机号信息异常", e);
		}
		return res;
	}

	public OpenIdInfo selectOpenIdInfo(OpenIdInfo oi) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("phone", oi.getPhone());
		postData.put("openId", oi.getOpenId());
		try {
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOpenIdInfo");
			net.sf.json.JSONObject dataJson = net.sf.json.JSONObject.fromObject(result.get("data"));
			if (dataJson.containsKey("phone"))
				oi.setPhone(dataJson.getString("phone"));
			if (dataJson.containsKey("openId"))
				oi.setOpenId(dataJson.getString("openId"));
		} catch (Exception e) {
			logger.error("查询用户手机与微信号联系信息异常", e);
		}
		return oi;
	}

	/**
	 * chenmeiyang 线下充值查询
	 */
	public OfflineChargeQueryResponse offlineChargeQuery(ChargeQueryRequest request) {
		OfflineChargeQueryResponse o = null;
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, appProperties.getChargeUrl() + "chargeFacade");
			o = facade.offlineChargeQuery(request);
			logger.info("支付系统返回" + o);
		} catch (Exception e) {
			logger.error("线下充值查询异常", e);
		}
		return o;
	}

	/**
	 * chenmeiyang 线上充值查询
	 */
	public ChargeQueryResponse chargeQuery(ChargeQueryRequest request) {
		ChargeQueryResponse o = null;
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			ChargeFacade facade = (ChargeFacade) factory.create(ChargeFacade.class, appProperties.getChargeUrl() + "chargeFacade");
			o = facade.chargeQuery(request);
			logger.info("支付系统返回" + o);
		} catch (Exception e) {
			logger.error("线下充值查询异常", e);
		}
		return o;
	}

	/**
	 * chenmeiyang 虚拟资产流水查询
	 */
	public VirtualFlowQueryResponse virtualFlowQuery(VirtualFlowQueryRequest request) {
		VirtualFlowQueryResponse o = null;
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, appProperties.getChargeUrl() + "virtualFacade");
			o = facade.virtualFlowQuery(request);
			logger.info("支付系统返回" + o);
		} catch (Exception e) {
			logger.error("线下充值查询异常", e);
		}
		return o;
	}

	/**
	 * 注册充值
	 * 
	 * @param uid
	 * @return
	 */
	public boolean sendReward(String uid) {
		RewardInsertResponse rewardInsertResponse = null;
		try {
			// 请求支付系统
			HessianProxyFactory factory = new HessianProxyFactory();
			RewardFacade facade = (RewardFacade) factory.create(RewardFacade.class, appProperties.getChargeUrl() + "rewardFacade");
			RewardInsertRequest rewardInsertRequest = new RewardInsertRequest();
			rewardInsertRequest.setMemberID(uid);
			rewardInsertRequest.setRewardType(RewardTypeEnum.REGISTER);
			rewardInsertRequest.setRewardNum(appProperties.getRegisterReward());
			rewardInsertRequest.setRemark("注册红包奖励50元");
			rewardInsertResponse = facade.insertReward(rewardInsertRequest);
			logger.info("支付系统返回:" + rewardInsertResponse);
		} catch (Exception e) {
			logger.error("用户注册送红包失败", e);
		}
		return rewardInsertResponse.isSuccess();
	}

	/**
	 * 注册创建账户
	 * 
	 * @param uid
	 * @return
	 */
	public boolean AccountBuild(String uid) {
		AccountBuildResponse accountBuildResponse = null;
		try {
			// 请求支付系统
			HessianProxyFactory factory = new HessianProxyFactory();
			VirtualFacade facade = (VirtualFacade) factory.create(VirtualFacade.class, appProperties.getChargeUrl() + "virtualFacade");
			AccountBuildRequest accountBuildRequest = new AccountBuildRequest();
			accountBuildRequest.setMemberID(uid);
			accountBuildResponse = facade.buildAccount(accountBuildRequest);
			logger.info("支付系统返回:" + accountBuildResponse);
		} catch (Exception e) {
			logger.error("用户注册创建账户错误", e);
		}
		return accountBuildResponse.isSuccess();
	}

	/**
	 * 获取用户积分等级
	 * 
	 * @param uid
	 * @return
	 */
	public Map<String, Object> getLevel(String uid) {
		List<VirtualFlowTypeEnum> types = new ArrayList<VirtualFlowTypeEnum>();
		List<VirtualFlowEntity> data = null;
		Map<String, Object> res = new HashMap<String, Object>();
		int level = 1;
		try {
			VirtualFlowQueryRequest vfqr = new VirtualFlowQueryRequest();
			vfqr.setMemberID(uid);
			types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_ONLINE);
			types.add(VirtualFlowTypeEnum.VIRTUAL_CHARGE_OFFLINE);
			vfqr.setType(types);
			VirtualFlowQueryResponse vfqs = this.virtualFlowQuery(vfqr);
			data = vfqs.getData();
			int amt = 0;
			for (VirtualFlowEntity v : data) {
				int money = Integer.parseInt(v.getMoney());
				amt += money;
			}
			if (amt >= 10000 && amt < 30000) {
				level = 2;
			} else if (amt >= 30000 && amt < 100000) {
				level = 3;
			} else if (amt >= 100000 && amt < 300000) {
				level = 4;
			} else if (amt >= 300000) {
				level = 5;
			}
			res.put("level", level);
			res.put("amt", amt);
		} catch (Exception e) {
			logger.error("获取等级异常", e);
		}
		return res;
	}

	/**
	 * 计算奖励积分
	 * 
	 * @param uid
	 * @param money
	 * @return
	 */
	public RewardCalResponse calcurateReword(String uid, int money) {
		RewardCalResponse rcr = null;
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			RewardFacade facade = (RewardFacade) factory.create(RewardFacade.class, appProperties.getChargeUrl() + "rewardFacade");
			RewardCalRequest rewardRequest = new RewardCalRequest();
			rewardRequest.setMemberID(uid);
			rewardRequest.setMoney(String.valueOf(money));
			rcr = facade.calReward(rewardRequest);
			logger.info("支付系统返回" + rcr);
		} catch (Exception e) {
			logger.error("获取等级异常", e);
		}
		return rcr;
	}

	/**
	 * 获得收藏列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<CollectsInfo> getCollects(String uid) {
		List<CollectsInfo> list = new LinkedList<CollectsInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		try {
			JSONObject json = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getCollects");
			JSONObject data = json.getJSONObject("data");
			JSONArray collects = JSONArray.fromObject(data.get("collects"));
			for (int i = 0; i < collects.size(); i++) {
				net.sf.json.JSONObject temp = collects.getJSONObject(i);
				CollectsInfo c = new CollectsInfo();
				c.setHouseId(temp.getInt("houseId"));
				c.setOperTime(temp.getString("operTime"));
				list.add(c);
			}
		} catch (Exception e) {
			logger.error("查询收藏记录异常", e);
		}
		return list;
	}

	/**
	 * 获取订单列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<OrderInfo> getOrders(String uid) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("uid", uid);
		try {
			JSONObject json = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getOrders");
			JSONObject data = json.getJSONObject("data");
			JSONArray orders = JSONArray.fromObject(data.get("orders"));
			for (int i = 0; i < orders.size(); i++) {
				net.sf.json.JSONObject temp = orders.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOperTime(temp.getString("operTime"));
				list.add(orderInfo);
			}
		} catch (Exception e) {
			logger.error("查询订单列表异常", e);
		}
		return list;
	}

	/**
	 * 获取开发商列表
	 * 
	 * @return
	 */
	public List<DeveloperInfo> getDevelops() {
		List<DeveloperInfo> list = new ArrayList<DeveloperInfo>();
		Map<String, Object> postData = new HashMap<String, Object>();
		try {
			JSONObject json = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "getDevelopers");
			JSONObject data = json.getJSONObject("data");
			JSONArray developers = JSONArray.fromObject(data.get("developers"));
			int index = 1;
			for (int i = 0; i < developers.size(); i++) {
				net.sf.json.JSONObject temp = developers.getJSONObject(i);
				DeveloperInfo delInfo = new DeveloperInfo();
				delInfo.setName(temp.getString("name"));
				delInfo.setIndex(String.valueOf(index));
				index++;
				list.add(delInfo);
			}
		} catch (Exception e) {
			logger.error("查询订单列表异常", e);
		}
		return list;
	}

	public JSONObject registerByMap(Map<String, Object> postMap) {
		JSONObject res = new JSONObject();
		if (null == postMap)
			return res;
		try {
			if (StringUtils.isEmpty((String) postMap.get("phone")) || StringUtils.isEmpty((String) postMap.get("password")))
				return res;
			res = HttpClientPostMethod.httpReqUrl(postMap, appProperties.getRequestRoot(), "register");
		} catch (Exception e) {
			logger.error("注册异常", e);
		}
		return res;
	}

	public Map<String, Object> isPhoneRegister(String phone) {
		Map<String, Object> postData = new HashMap<String, Object>();
		if (StringUtils.isEmpty(phone))
			return postData;
		try {
			postData.put("phone", phone);
			JSONObject result = HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(), "isPhoneRegistered");
			if (0 == result.getIntValue("code")) {
				JSONArray array = JSONArray.fromObject(result.get("data"));
				for (int i = 0; i < array.size(); i++) {
					net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
					postData.put("registered", jsonObject.getInt("registered"));
					postData.put("recharged", jsonObject.getInt("recharged"));
					postData.put("verified", jsonObject.getInt("verified"));
				}
			}
		} catch (Exception e) {
			logger.error("验证手机号异常", e);
		}
		return postData;
	}

	public static void testRequest(Map<String, Object> param, String inter, String type) {

		try {
			HttpClientPostMethod.httpReqUrl(param, "http://127.0.0.1:8889/mamaserver/customer", inter);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/*
		 * System.out.println("2015-10-20 00:37:03".compareTo(
		 * "2015-10-20 00:37:04")); if(1==1)
		 */
		// return;
		Map<String, Object> postData = new HashMap<String, Object>();
		// param.put("phone", "13700279488");
		// param.put("verifyCode", "630352");
		// param.put("password", "123456");
		// param.put("type", 1);
		postData.put("devId", 3);
		postData.put("cityId", 3);
		postData.put("provId", 3);
		postData.put("name", "123");
		postData.put("type", 3);
		postData.put("mark", "123");
		postData.put("showDetail", "{}");
		// postData.put("openId", "ohasqwAF0jGpnh5sJ_jzwvQJf2Fw");
		// postData.put("rewardamt", 100);
		// postData.put("showdetail", "线下充值");
		/*
		 * List<Integer> list =new ArrayList<Integer>(); list.add(0);
		 * postData.put("contactIds", list); Map<String,Object> map = new
		 * HashMap<String,Object>(); map.put("houseName", "李佳奇");
		 * postData.put("liveDetail", map); postData.put("totalRoomNum", 1);
		 */
		/*
		 * postData.put("sortByTime", 1); postData.put("startDate",
		 * "2015-10-20 00:37:04"); postData.put("endDate", "2015-10-20 00:37:04"
		 * );
		 */

		// param.put("nickName", "test");
		// param.put("email", "test@test.com");
		// param.put("name", "test");
		// param.put("idCard", "test@test.com");
		testRequest(postData, "addBuilding", "0");
	}
}
