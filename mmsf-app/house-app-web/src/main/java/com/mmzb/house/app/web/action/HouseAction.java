package com.mmzb.house.app.web.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.server.common.util.PartnershipEnum;
import com.mama.server.common.entity.KWSearchResultEntity;
import com.mama.server.common.entity.RecommodHouse;
import com.mmzb.fdfs.facade.FileFacade;
import com.mmzb.fdfs.facade.entity.FileUploadRequest;
import com.mmzb.fdfs.facade.entity.FileUploadResponse;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.RedisKeyConstants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.util.JsonUtil;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.CommentPagerEntity;
import com.mmzb.house.app.entity.Facilities;
import com.mmzb.house.app.entity.HouseComment;
import com.mmzb.house.app.entity.HouseCommentEntity;
import com.mmzb.house.app.entity.HouseDetail;
import com.mmzb.house.app.entity.HouseDetailEntity;
import com.mmzb.house.app.entity.HouseEntity;
import com.mmzb.house.app.entity.HouseImage;
import com.mmzb.house.app.entity.HousePagerEntity;
import com.mmzb.house.app.entity.Other;
import com.mmzb.house.app.entity.RoomProperty;
import com.mmzb.house.app.entity.Supporting;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.CommentVo;
import com.mmzb.house.app.vo.HouseCommentVo;
import com.mmzb.house.app.vo.HouseDetailVo;
import com.mmzb.house.app.vo.HouseSearchConditionVo;
import com.mmzb.house.app.vo.HouseSearchVo;
import com.mmzb.house.app.vo.HouseSearchVo.HouseSearchOption;
import com.mmzb.house.app.vo.HouseSearchVo.NameIdVo;
import com.mmzb.house.app.vo.HouseVo;
import com.mmzb.house.app.vo.OrderCommentVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.RecommondHouseVo;
import com.mmzb.house.app.vo.RoomFacilityVo;
import com.mmzb.house.app.vo.TagVO;
import com.mmzb.house.app.vo.UserInfoVo;
import com.mmzb.house.app.web.util.CommonUtil;
import com.mmzb.house.app.web.util.Constant;

/**
 * 
 * @author majiafei
 *
 */
@Controller
@RequestMapping(value = "app")
public class HouseAction
{
	//记录日志
	private final Logger logger = LoggerFactory.getLogger(HouseAction.class);
	
	//配置文件
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Autowired
    private MemberTokenService memberTokenService;
	
	/**
	 * 搜索房源列表
	 * @param conditionVo
	 * @param model
	 */
	@RequestMapping(value = "/searchHouseList", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseOut<HouseSearchVo> searchHouses(HouseSearchConditionVo conditionVo, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("HouseAction get the request to searchHouses, param is {}", conditionVo);
		}
		
		ResponseOut<HouseSearchVo> response = new ResponseOut<HouseSearchVo>();
		try 
		{
			//查询房源列表信息
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			postDataMap.put("condition", conditionVo);
			Response<HousePagerEntity> rsp = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "appHouseSearch", HousePagerEntity.class);
			
			HouseSearchVo houseSearchVo = new HouseSearchVo();
			if ((rsp != null) && ("0".equals(rsp.getCode())))
			{
				
				//转化房源列表信息entity-->vo
				HousePagerEntity housePagerEntity = rsp.getData();
				List<HouseEntity> houseEntities = null;
				int itemCount = 0;
				if (housePagerEntity != null)
				{
					itemCount = housePagerEntity.getItemCount();
					houseEntities = housePagerEntity.getHouses();
				}
				
				if (houseEntities != null) 
				{
					List<HouseVo> houseVos = convert2HouseVos(houseEntities, accessToken);
					houseSearchVo.setHouses(houseVos);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("get the houses info:{}", houseVos);
					}
				}
				
    			//设置分页信息
    			Page pager = new Page();
    			pager.setItemCount(itemCount);
    			pager.setPageIndex(conditionVo.getPageIndex());
    			pager.setPageSize(conditionVo.getPageSize());
    			houseSearchVo.setPager(pager);
			}
			
			//获取搜索选项数据
			if (conditionVo.isNeedSearchOption()) 
			{
				houseSearchVo.setOption(getOptions(conditionVo));
			}
			
			response.setCode(Constants.APP_SUCCESSED);
			response.setMessage(rsp.getMsg());
			response.setResult(houseSearchVo);
		}
		catch (Exception e) 
		{
			response.setCode(Constants.APP_FAILED);
			response.setMessage(e.getMessage());
			logger.error("failed to get houses info", e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/houseDetail")
	@ResponseBody
	public ResponseOut<HouseDetailVo> getHouseDetail(Integer houseId, String accessToken){
		if (logger.isInfoEnabled())
		{
			logger.info("start to get house details, houseId is {}.", houseId);
		}
		
		if((houseId == null) || houseId <= 0)
		{
			return null;
		}
		
		ResponseOut<HouseDetailVo> response = new ResponseOut<HouseDetailVo>();
		
		try 
		{
			//获取房源详情
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			postDataMap.put("houseId", houseId);
			Response<HouseDetailEntity> rsp = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "getHouseDetail", HouseDetailEntity.class);
			
			if ((rsp != null) && ("0".equals(rsp.getCode())))
			{
				HouseDetailEntity detailEntity = rsp.getData();
				if (detailEntity != null)
				{
					HouseDetailVo houseDetailVo = convert2houseDetailVo(detailEntity);
					
					houseDetailVo.setFavor(isFavor(houseId, accessToken));
					response.setResult(houseDetailVo);
					
					return response;
				}
			}
			
			response.setMessage(rsp.getMsg());
			response.setCode(Constants.APP_FAILED);			
		}
		catch (Exception e) 
		{
			response.setCode(Constants.APP_FAILED);
			logger.error("failed to get house details", e);
		}
		
		return response;
	}

	private boolean isFavor(Integer houseId, String accessToken)
	{
		if (memberTokenService.isLoginByTokenId(accessToken))
		{
			UserInfoVo user = memberTokenService.getLoginInfoFromRedis(accessToken);
			if ((user == null) || (user.getMemberId() == null))
			{
				return false;
			}
			
			String uuid = user.getMemberId();
			RedisRequest redisRequest = new RedisRequest();
			redisRequest.setKey("mmsf:collect:"+houseId + uuid);
			String collectFlag = redisFacade.getValueByKey(redisRequest);
//			String collectFlag = RedisHelper.get(RedisConstants.COLLECT_KEY + houseId + uuid);
			if (StringUtils.isNotEmpty(collectFlag))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private HouseDetailVo convert2houseDetailVo(HouseDetailEntity detailEntity)
	{
		if (detailEntity == null)
		{
			return null;
		}
		
		HouseDetailVo detailVo = new HouseDetailVo();
		
		HouseDetail hDetail = detailEntity.getHouseInfo();
		if (hDetail != null)
		{
			detailVo.setHouseId(hDetail.getHouseId());
			detailVo.setHouseName(hDetail.getName());
			
			List<HouseImage> imgList = hDetail.getImgList();
			if (!CollectionUtils.isEmpty(imgList))
			{
				for(HouseImage image : imgList)
				{
					image.setImageUrl(CommonUtil.compressImageUrl(image.getImageUrl()));
				}
			}
			detailVo.setImgUrls(hDetail.getImgList());
			
			detailVo.setPrice(hDetail.getMemTotalAmt());
			detailVo.setHouseUnit("起/晚");
			detailVo.setShareTitle("我在妈妈送房上发现了一个超赞的【客栈/民宿/房间】：" + hDetail.getName());
			detailVo.setShareUrl("http://m.mmsfang.com/house/toDetail.htm?houseId=" + hDetail.getHouseId());
			StringBuffer subTitle = new StringBuffer();
			RoomProperty room = hDetail.getRoomList();
			subTitle.append(room.getRoom() + "室")
			        .append(room.getOffice() + "厅")
			        .append(room.getToilet() + "卫  ")
			        .append(room.getArea() + "平米  ")
			        .append(room.getBeds() + "床  ")
			        .append("宜住" + room.getPersonNum() + "人");
			detailVo.setShareSubTitle(subTitle.toString());
			detailVo.setShareImgURL(hDetail.getShareImg());			
			
			//获取房源标签
			detailVo.setTagNames(getTagNames(hDetail.getTagList()));
			detailVo.setTags(getTags(hDetail.getTagList()));
			
			detailVo.setDescription("");
			
			detailVo.setRoomString(room.getRoom() + "居" + room.getToilet() + "卫");
			detailVo.setPeopleCount(room.getPersonNum());
			detailVo.setBedCount(room.getBeds());
			detailVo.setBedInfo(room.getBedType());
			detailVo.setSquare(Integer.valueOf(room.getArea()));
			detailVo.setAddress(room.getAddress());
			
			Supporting supporting = hDetail.getSupportingList();
			detailVo.setWifiEnable((supporting.getWifi() == 1) ? true : false);
			
			detailVo.setLocation(hDetail.getGeographicalList());
			
			detailVo.setFacilities(getRoomFacilities(hDetail));
			
			detailVo.setAdditionalTags(getAdditionalTags(hDetail.getOtherList()));
			
		}
		if (detailEntity.getShop() != null) {
			if (PartnershipEnum.MemberInn.getCode().equals(detailEntity.getShop().getPartnership())) {
				detailEntity.getShop().setPartnership(PartnershipEnum.MemberInn.getMessage());//会员
			}else if (PartnershipEnum.Holding.getCode().equals(detailEntity.getShop().getPartnership())) {
				detailEntity.getShop().setPartnership(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(detailEntity.getShop().getPartnership())) {
				detailEntity.getShop().setPartnership(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(detailEntity.getShop().getPartnership())) {
				detailEntity.getShop().setPartnership(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}
		}
		
		detailVo.setShop(detailEntity.getShop());
		/*String description = detailEntity.getHouseInfo().getRoomList().getRoom()+"室"
				+detailEntity.getHouseInfo().getRoomList().getOffice()+"厅"+
				detailEntity.getHouseInfo().getRoomList().getBeds()+"床  宜住"+
				detailEntity.getHouseInfo().getRoomList().getPersonNum()+"人";*/
		String description = "";
		if (detailEntity.getHouseInfo().getRoomList().getRoom() != 0) {
			description += detailEntity.getHouseInfo().getRoomList().getRoom() + "室";
		}
		if (detailEntity.getHouseInfo().getRoomList().getOffice() != 0) {
			description += detailEntity.getHouseInfo().getRoomList().getOffice()+"厅";
		}
		if (detailEntity.getHouseInfo().getRoomList().getBeds() != 0) {
			description += detailEntity.getHouseInfo().getRoomList().getBeds() + "床  宜住";
		}
		if (detailEntity.getHouseInfo().getRoomList().getPersonNum() != 0) {
			description += detailEntity.getHouseInfo().getRoomList().getPersonNum() + "人";
		}
		
		detailVo.setDescription(description);
		HouseCommentEntity commentEntity = detailEntity.getComment();
		if (commentEntity != null)
		{
			HouseCommentVo commentVo = new HouseCommentVo();
			commentVo.setAverageScore(commentEntity.getAverageScore());
			commentVo.setSummary(commentEntity.getSummary());
			commentVo.setTotalCommentsNum(commentEntity.getTotalCommentsNum());
			detailVo.setComments(commentVo);
		}
		detailVo.setMarketPrice(detailEntity.getHouseInfo().getMarket_price());//市场价
		//折扣
		if (detailEntity.getHouseInfo().getMarket_price() > 0) {
			float  discount = (float)detailEntity.getHouseInfo().getMemTotalAmt()*10/(float)detailEntity.getHouseInfo().getMarket_price();
			BigDecimal   b   =   new   BigDecimal(discount);  
			float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
			detailVo.setDiscountDescription(f1+"折");
		}
		String memTotalAmt = String.valueOf(detailEntity.getHouseInfo().getMemTotalAmt());
		if (detailEntity.getHouseInfo().getMemTotalAmt()>=99) {
			if (detailEntity.getHouseInfo().getSpecials_stauts()!=0&&memTotalAmt.substring(memTotalAmt.length()-2, memTotalAmt.length()).equals("99")) {
				detailVo.setIsSpecial(true);
			}else {
				detailVo.setIsSpecial(false);
			}
		}else {
			detailVo.setIsSpecial(false);
		}
		
		return detailVo;
	}
	
	private List<String> getTagNames(List<Integer> houseTagId)
	{
		try 
		{
			RedisRequest redisRequest = new RedisRequest();
			redisRequest.setKey("mmsf:allHouseTag");
			String houseTag = redisFacade.getValueByKey(redisRequest);
//			String houseTag = RedisHelper.get(RedisConstants.HOUSE_TAG_KEY);
			if ((houseTag != null) && !CollectionUtils.isEmpty(houseTagId))
			{
				//只取前三个标签
				houseTag = "[" + houseTag + "]";
				JSONArray houseTags = JSONArray.parseArray(houseTag);
				List<String> tagNames = new ArrayList<String>();
				int tagSize = (houseTagId.size() > 3) ? 3 : houseTagId.size();
				for(int i = 0; i < tagSize; i++)
				{
					for(int y = 0 ; y < houseTags.size() ; y ++)
					{
						JSONObject tagObj = houseTags.getJSONObject(y);
						
						if (tagObj.getInteger("id") == houseTagId.get(i)) 
						{
							tagNames.add(tagObj.getString("name"));
							break;
						}
					}
				}
				
				return tagNames;
			}
		} 
		catch (Exception e) 
		{
			logger.error("failed to get tags", e);
		}
		
		return null;
	}
	
	private List<TagVO> getTags(List<Integer> houseTagId){
		try 
		{
//			String houseTag = RedisHelper.get(RedisConstants.HOUSE_TAG_KEY);
			RedisRequest redisRequest = new RedisRequest();
			redisRequest.setKey("mmsf:allHouseTag");
			String houseTag = redisFacade.getValueByKey(redisRequest);
			
			if ((houseTag != null) && !CollectionUtils.isEmpty(houseTagId))
			{
				//只取前三个标签
				houseTag = "[" + houseTag + "]";
				JSONArray houseTags = JSONArray.parseArray(houseTag);
				List<TagVO> tagsList = new ArrayList<TagVO>();
				int tagSize = (houseTagId.size() > 3) ? 3 : houseTagId.size();
				for(int i = 0; i < tagSize; i++)
				{
					TagVO tag = new TagVO();
					for(int y = 0 ; y < houseTags.size() ; y ++)
					{
						JSONObject tagObj = houseTags.getJSONObject(y);
						
						if (tagObj.getInteger("id") == houseTagId.get(i)) 
						{
							tag.setId(tagObj.getInteger("id"));
							tag.setName(tagObj.getString("name"));
							tagsList.add(tag);
							break;
						}
					}
				}
				
				return tagsList;
			}
		} 
		catch (Exception e) 
		{
			logger.error("failed to get tags", e);
		}
		
		return null;
	}

	private List<String> getAdditionalTags(Other other)
	{
		if (other == null)
		{
			return null;
		}
		
		List<String> otherName = new ArrayList<String>();
		if (other.getCook() == 1) {
			otherName.add("可做饭");
		}
		if (other.getParty() == 1){
			otherName.add("适合聚会");
		}
		if (other.getSmoking() == 1) {
			otherName.add("可吸烟");
		}
		if (other.getPet() == 1) {
			otherName.add("可带宠物");
		}
		if (other.getExtrabed() == 1) {
			otherName.add("可加床");
		}
		if (other.getGuests() == 1) {
			otherName.add("接待外宾");
		}
		if (other.getBreakfast() == 1) {
			otherName.add("提供早餐");
		}
		if (other.getChildrenstay() == 1) {
			otherName.add("欢迎孩子入住");
		}
		
		return otherName;
		
	}
	
	private List<RoomFacilityVo> getRoomFacilities(HouseDetail houseDetail)
	{
    	if (houseDetail == null)
    	{
			return null;
		}
    	
    	List<RoomFacilityVo> vos = new ArrayList<RoomFacilityVo>();
    	Facilities facilities = houseDetail.getFacilitiesList();
    	if (facilities != null)
    	{
			if (facilities.getConditioner() == 1) {
				vos.add(new RoomFacilityVo("空调", "http://file.88mmmoney.com/bfff3ff6-d1c8-4ad1-9cb1-24a88a049c84"));
			}
			if (facilities.getWashing() == 1) {
				vos.add(new RoomFacilityVo("洗衣机", "http://file.88mmmoney.com/e76ced03-fc50-4356-80fb-dfbc8b05edd2"));
			}
			if (facilities.getFridge() == 1) {
				vos.add(new RoomFacilityVo("冰箱", "http://file.88mmmoney.com/ce2cb645-0d8f-46e8-b99a-7b24863f336a"));
			}
			if (facilities.getDrinking() == 1) {
				vos.add(new RoomFacilityVo("饮水机", "http://file.88mmmoney.com/ba56223e-3e8a-48f4-aa9b-34159438cea3"));
			}
			if (facilities.getTv() == 1) {
				vos.add(new RoomFacilityVo("电视机", "http://file.88mmmoney.com/0d9009f3-1a13-4125-b255-794c3c500b7f"));
			}
			if (facilities.getTooth() == 1) {
				vos.add(new RoomFacilityVo("毛巾", "http://file.88mmmoney.com/2c90c0fb-a781-4dc5-8992-25baeb162bbe"));
			}
			if (facilities.getTooth() == 1) {
				vos.add(new RoomFacilityVo("牙具", "http://file.88mmmoney.com/775edd8a-5e1a-4376-8406-8af9367625c3"));
			}
			if (facilities.getSlipper() == 1) {
				vos.add(new RoomFacilityVo("拖鞋", "http://file.88mmmoney.com/47854342-b508-42da-8299-9c200a65bfb2"));
			}
			if (facilities.getShampoo() == 1) {
				vos.add(new RoomFacilityVo("洗发/沐浴露", "http://file.88mmmoney.com/a8b35256-33a8-4f65-8e43-28157cefffe1"));
			}
			if (facilities.getHairdrier() == 1) {
				vos.add(new RoomFacilityVo("吹风机", "http://file.88mmmoney.com/9b5e5e11-eba5-4d61-bf09-ab6d603d43aa"));
			}
			if (facilities.getShower() == 1) {
				vos.add(new RoomFacilityVo("浴缸", "http://file.88mmmoney.com/7a8787b6-dac6-4c34-bfc2-34785d429d75"));
			}
			if (facilities.getHeater() == 1) {
				vos.add(new RoomFacilityVo("热水器", "http://file.88mmmoney.com/ce3acf2e-8917-4b73-8920-4b7f2bd4f5cb"));
			}
			if (facilities.getDryer() == 1) {
				vos.add(new RoomFacilityVo("烘干机", "http://file.88mmmoney.com/db689372-18b7-41e5-967a-5166c7e31ad6"));
			}
			if (facilities.getSmokedetector() == 1) {
				vos.add(new RoomFacilityVo("烟雾探测器", "http://file.88mmmoney.com/840e88a5-6eda-43cd-a0d6-651c716171f0"));
			}
			if (facilities.getHeating() == 1) {
				vos.add(new RoomFacilityVo("暖气", "http://file.88mmmoney.com/2cb2bcf4-b821-49fa-95f5-7affd97d4967"));
			}
			if (facilities.getExtinguisher() == 1) {
				vos.add(new RoomFacilityVo("灭火器", "http://file.88mmmoney.com/ff5cf078-915f-45c7-8a68-b1cd69b96b60"));
			}
			if (facilities.getAidkit() == 1) {
				vos.add(new RoomFacilityVo("急救包", "http://file.88mmmoney.com/719a7fd6-657a-4745-af1f-da4e9684d468"));
			}
			if (facilities.getHotpot() == 1) {
				vos.add(new RoomFacilityVo("热水壶", "http://file.88mmmoney.com/88cd0f14-cc1b-48a8-95b5-8a9a5222c5d2"));
			}
		}
    	
    	Supporting supporting = houseDetail.getSupportingList();
    	if (supporting != null)
    	{
			if (supporting.getWifi() == 1) {
				vos.add(new RoomFacilityVo("wifi", "http://file.88mmmoney.com/15531bb9-6750-4876-87b8-90bd1f558b37"));
			}
			if (supporting.getBroadband() == 1) {
				vos.add(new RoomFacilityVo("宽带", "http://file.88mmmoney.com/47b43e0d-dfb9-4a95-ba61-afa274cc45ea"));
			}
			if (supporting.getElevator() == 1) {
				vos.add(new RoomFacilityVo("电梯", "http://file.88mmmoney.com/b549dde2-287d-4d3e-a229-2b3476c503b2"));
			}
			if (supporting.getSwimming() == 1) {
				vos.add(new RoomFacilityVo("游泳池", "http://file.88mmmoney.com/1d3ebf50-e208-4845-86ea-e15e6e43d10e"));
			}
			if (supporting.getAccesscard() == 1) {
				vos.add(new RoomFacilityVo("门禁卡", "http://file.88mmmoney.com/ebea6620-8a2d-4428-95dc-45ac832578c9"));
			}
			if (supporting.getSecuritystaff() == 1) {
				vos.add(new RoomFacilityVo("保安", "http://file.88mmmoney.com/86971bf6-7aeb-45cc-9224-575f57366901"));
			}
			if (supporting.getStore() == 1) {
				vos.add(new RoomFacilityVo("便利店", "http://file.88mmmoney.com/538cce2f-e945-42d6-98d2-89f0570ebbf5"));
			}
			if (supporting.getParking() == 1) {
				vos.add(new RoomFacilityVo("停车位", "http://file.88mmmoney.com/b202a115-f396-4470-bfce-7c51e1c33ae8"));
			}
			if (supporting.getGym() == 1) {
				vos.add(new RoomFacilityVo("健身房", "http://file.88mmmoney.com/caef8550-116f-49fa-b57b-98e579910d00"));
			}
			if (supporting.getPlayground() == 1) {
				vos.add(new RoomFacilityVo("儿童乐园", "http://file.88mmmoney.com/81179342-41ff-4430-b264-cdb6b7474185"));
			}
			if (supporting.getWheelchair() == 1) {
				vos.add(new RoomFacilityVo("无障碍设施", "http://file.88mmmoney.com/def3f9e0-1aa8-49ef-be32-720f7450dd99"));
			}
			if (supporting.getBuzzer() == 1) {
				vos.add(new RoomFacilityVo("无线对讲机", "http://file.88mmmoney.com/1bdb461d-0a07-4f98-af75-9b060c2f782e"));
			}
		}
    	
		return vos;
	}

	/**
	 * 查询评论列表
	 * @param houseId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/commentList", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseOut<OrderCommentVo> getComments(Integer houseId, Integer pageSize, Integer pageIndex)
	{
		ResponseOut<OrderCommentVo> response = new ResponseOut<OrderCommentVo>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", 1);//按推荐时间、评论时间排序
		param.put("status", "-1");//查询所有状态的评论
		param.put("houseId", houseId);
		param.put("currentPage", pageIndex);
		param.put("pageSize", pageSize);
		
		try 
		{
			Response<CommentPagerEntity> rsp = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getOrderComment", CommentPagerEntity.class);
			
			if ((rsp != null) && "0".equals(rsp.getCode())) 
			{
				int itemCount = 0;
				CommentPagerEntity entity = rsp.getData();
				OrderCommentVo vo = new OrderCommentVo();
				if (entity != null)
				{
					itemCount = entity.getTotalNum();
					vo.setComments(entity.getSourceList());
					
					//设置数据结果
					response.setResult(vo);
				}
				
				//设置分页信息
				Page page = new Page();
				page.setItemCount(itemCount);
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				vo.setPager(page);
				
				response.setMessage(rsp.getMsg());
				response.setCode(Constants.APP_SUCCESSED);
			}
			
		}
		catch (Exception e)
		{
			response.setCode(Constants.APP_FAILED);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}
	
	private List<HouseVo> convert2HouseVos(List<HouseEntity> houseEntities, String accessToken)
	{
		if (CollectionUtils.isEmpty(houseEntities)) 
		{
			return null;
		}

		List<HouseVo> houseVos = new ArrayList<HouseVo>();
		for(HouseEntity entity : houseEntities)
		{
			HouseVo houseVo = new HouseVo();
			houseVo.setHouseId(entity.getHouseId());
			houseVo.setHouseName(entity.getHouseName());
			
			houseVo.setImgUrl(CommonUtil.compressImageUrl(entity.getImgUrl().split(",")[0]));
			houseVo.setPrice(entity.getPrice());
			
			String description = entity.getRoomCount() + "室 宜"
					+ entity.getPeopleCount() + "人居住";
			houseVo.setDescription(description);
			houseVo.setIsFavor(isFavor(entity.getHouseId(), accessToken));
			houseVo.setHouseUnit("起/晚");
			//获取房源标签
			houseVo.setTagNames(getTagNames(entity.getTagList()));
			/*String partnership = "";
			if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.MemberInn.getCode())) {
				partnership = PartnershipEnum.MemberInn.getMessage(); //会员
			} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.Holding.getCode())) {
				partnership = PartnershipEnum.Holding.getMessage(); //控股
			} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.DepthCooperation.getCode())) {
				partnership = PartnershipEnum.DepthCooperation.getMessage();  //深度
			} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.GuesthouseInn.getCode())) {
				partnership = PartnershipEnum.GuesthouseInn.getMessage(); //民宿
			}
			houseVo.setSpecialTag(partnership);*/
			houseVo.setMarketPrice(entity.getMarketPrice());
			
			if (entity.getMarketPrice() != 0) {
				DecimalFormat df = new DecimalFormat("######0.0");
				double discount =  (double)entity.getPrice()/(double)entity.getMarketPrice() * 10;
				houseVo.setDiscountDescription(df.format(discount) + "折");
			}
			houseVos.add(houseVo);
		}
		
		return houseVos;
	}

	private HouseSearchOption getOptions(HouseSearchConditionVo conditionVo)
	{
		HouseSearchOption option = new HouseSearchOption();
		
		//1.获取城市所有商圈
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey("mmsf:tradeArea:"+ conditionVo.getCityId());
		String area = redisFacade.getValueByKey(redisRequest);
		
//		String area = RedisHelper.get(RedisKeyConstants.MMSF_TRADE_AREA_KEY + conditionVo.getCityId());
		if(StringUtils.isNotEmpty(area))
		{
			area = "[" + area + "]";
			List<NameIdVo> areas = JsonUtil.jsonToList(area, NameIdVo.class);
			option.setArea(areas);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("get the area from redis : {},cityId={}", area, conditionVo.getCityId());
		}
		
		//2.获取所价格区间
		List<NameIdVo> priceSections = new ArrayList<HouseSearchVo.NameIdVo>(); 
		priceSections.add(new NameIdVo(0, "不限"));
		priceSections.add(new NameIdVo(1, "200以下"));
		priceSections.add(new NameIdVo(2, "200-300"));
		priceSections.add(new NameIdVo(3, "300-500"));
		priceSections.add(new NameIdVo(4, "500以上 "));
		option.setPriceSections(priceSections);
		
		//3.获取房源标签
		RedisRequest req = new RedisRequest();
		req.setKey(RedisKeyConstants.MMSF_HOUSE_TAGS_KEY);
		String tag = redisFacade.getValueByKey(req);
		
//		String tag = RedisHelper.get(RedisKeyConstants.MMSF_HOUSE_TAGS_KEY);
		if(StringUtils.isNotEmpty(tag))
		{
			tag = "[" + tag + "]";
			List<NameIdVo> tags = JsonUtil.jsonToList(tag, NameIdVo.class);
			option.setTags(tags);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("get the tags from redis : {},cityId={}", tag, conditionVo.getCityId());
		}
		
		//4.获取排序数组
		List<NameIdVo> sorts = new ArrayList<HouseSearchVo.NameIdVo>();
//		sorts.add(new NameIdVo(0, "推荐排序"));
		sorts.add(new NameIdVo(3, "价格高到低"));
		sorts.add(new NameIdVo(2, "价格低到高"));
		option.setSorts(sorts);
		
		return option;
	}
	
	@RequestMapping(value = "/my/commentOrder", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseOut addComment(String accessToken, CommentVo vo)
    {
		ResponseOut responseOut = new ResponseOut();
		
    	try 
    	{
    		if (logger.isInfoEnabled())
    		{
				logger.info("start to add comment, orderId={}.", vo.getOrderId());
			}
    		
    		//校验评分
    		if (vo.getScore() <= 0) 
    		{
    			responseOut.setCode(Constants.APP_FAILED);
        		responseOut.setMessage("请评分");
        		return responseOut;
			}
    		
    		//校验评论内容
    		if (StringUtils.isEmpty(vo.getComments()))
    		{
    			responseOut.setCode(Constants.APP_FAILED);
        		responseOut.setMessage("请填写评论内容");
        		return responseOut;
			}
    		
    		//获取登陆用户信息
    		UserInfoVo loginUser = memberTokenService.getLoginInfoFromRedis(accessToken);
    		vo.setUserPhone(loginUser.getMobile());
    		vo.setUid(loginUser.getMemberId());
    		vo.setCreateTime(String.valueOf(new Date().getTime()));
    		
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("orderComment", vo);
    		
    		//添加评论
    		Response addResponse = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "addOrderComment", null);
    		if ("0".equals(addResponse.getCode()))
    		{
    			//更新订单状态
    			param.remove("orderComment");
    			param.put("status", 17);
    			param.put("orderId", vo.getOrderId());
    			Response updateResponse = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot() + "/merchant", "updateOrderStatus", null);
    			if(!"0".equals(updateResponse.getCode()))
    			{
    				logger.error("failed to update order status, msg is {}", updateResponse.getMsg());
    				responseOut.setCode(Constants.APP_FAILED);
        			responseOut.setMessage("订单状态更新失败");
        			//TODO:删除评论
        			Response delResponse = HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "delOrderComment", null);
        			if (!"0".equals(delResponse.getCode())) 
        			{
        				responseOut.setCode(Constants.APP_FAILED);
            			responseOut.setMessage("添加失败");
					}
    			}
			}
    		else
    		{
    			responseOut.setCode(Constants.APP_FAILED);
    			responseOut.setMessage(addResponse.getMsg());
    			
    			logger.error("failed to add comment,msg is {}", addResponse.getMsg());
			}
		} 
    	catch (Exception e) 
    	{
    		responseOut.setCode(Constants.APP_FAILED);
			responseOut.setMessage("添加失败");
    		
    		logger.error("failed to add comment", e);
		}
    	
    	return responseOut;
    }
	
	@RequestMapping(value = "/my/uploadImage")
    @ResponseBody
    public ResponseOut importCommentImg(HttpServletRequest request)
	{
    	if (logger.isInfoEnabled())
    	{
			logger.info("start to run importCommentImg()");
		}
    	
    	ResponseOut response = new ResponseOut();
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) 
		{
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			// 取得request中的所有文件名
			List<MultipartFile> iter = multiRequest.getFiles("imageData");
			int size = iter.size();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("recieved request to upload images, count is {}.", size);
			}
			
			for (int i = 0; i < size; i++)
			{
				String url = appProperties.getUpload_house_url();
				HessianProxyFactory factory = new HessianProxyFactory();
				try
				{
					FileFacade facade = (FileFacade) factory.create(FileFacade.class, url);
					FileUploadRequest req = new FileUploadRequest();
					req.setSource("妈妈送房网");
					req.setFileType("jpg");// jpg 、png
					req.setGroupName("group1");
					req.setFile(iter.get(i).getBytes());

					FileUploadResponse fileResponse = facade.uploadFile(req);
					
					String imgUrl = fileResponse.getFileUrl();
					if (logger.isDebugEnabled())
					{
						logger.debug("upload image successfully, url is {}.", imgUrl);
					}
					
					if (StringUtils.isNotEmpty(imgUrl))
					{
						HouseImage image = new HouseImage();
						image.setImageUrl(imgUrl);
						response.setResult(image);
					}
					
				}
				catch (Exception e) 
				{
					logger.error("failed to upload the imags.{}", e);
				}

			}
		}
		
		return response;
	}
	
	/**
	 * 获取房源评论信息
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/commentInfo", method = { RequestMethod.POST})
	@ResponseBody
	public ResponseOut<HouseCommentVo> commentInfo(Integer houseId) throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/commentInfo().");
		}
		
		try {
			Map<String,Object> param = new HashMap<String,Object>();
    		param.put("houseId", houseId);
    		//查询房源评论信息
    		Response<HouseComment> commentsResult =HttpClientPostMethod.httpReqUrl(param, appProperties.getRequestRoot(), "getHouseComments",HouseComment.class);
			if (commentsResult.getCode().equals("0")) {
				HouseComment houseComment = commentsResult.getData();
				HouseCommentVo vo = new HouseCommentVo();
				if (houseComment != null)
				{
					HouseCommentEntity entity = houseComment.getResult();
					if (entity != null) {
						vo.setAverageScore(entity.getAverageScore());
						vo.setSummary(entity.getSummary());
						vo.setTotalCommentsNum(entity.getTotalCommentsNum());

					}
                }
				
				//查询房源信息
				Map<String, Object> postDataMap = new HashMap<String, Object>();
				HouseSearchConditionVo conditionVo = new HouseSearchConditionVo();
				int[] houseIds = new int[]{houseId};
				conditionVo.setHouseIds(houseIds);
				postDataMap.put("condition", conditionVo);
				Response<HousePagerEntity> rsp = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "appHouseSearch", HousePagerEntity.class);
				if (rsp.getCode().equals("0")) {
					HouseEntity house = rsp.getData().getHouses().get(0);
					
					vo.setImgUrl(CommonUtil.compressImageUrl(house.getImgUrl().split(",")[0])); //房源图片
					vo.setHouseName(house.getHouseName());
				}
				return new ResponseOut<HouseCommentVo>(Constants.APP_SUCCESSED, "", vo);
			}
			
			return new ResponseOut<HouseCommentVo>(Constants.APP_FAILED,"获取房源评论信息失败.", null);
			
		} catch (Exception e) {
			logger.error("/app/commentInfo:获取房源评论信息失败.",e);
			return new ResponseOut<HouseCommentVo>(Constants.APP_FAILED,"获取房源评论信息失败.", null);
		}
		
	}
	
	@RequestMapping(value = "/searchResult", method = { RequestMethod.POST})
	@ResponseBody
	public ResponseOut<KWSearchResultEntity> searchByKeyword(String keyword)
	{
		logger.info("start to run searchByKeyword(),keyword:{},cityId:{}.", keyword);
		
		if(StringUtils.isEmpty(keyword))
		{
			return null;
		}
		
		try 
		{
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			postDataMap.put("keyword", keyword);
			Response<KWSearchResultEntity> resultResponse = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "kwSearch", KWSearchResultEntity.class);
			if(resultResponse.getCode().equals("0"))
			{
				KWSearchResultEntity entity = resultResponse.getData();
				return new ResponseOut<KWSearchResultEntity>(Constants.APP_SUCCESSED, "", entity);
			}
			else
			{
				return new ResponseOut<KWSearchResultEntity>(Constants.APP_FAILED, resultResponse.getMsg());
			}
			
		} 
		catch (Exception e)
		{
			logger.error("Failed to search by keyword", e);
		}
		
		return new ResponseOut<KWSearchResultEntity>(Constants.APP_FAILED, "未知异常");
	}
	
	/**
	 * 推荐客栈
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "/recommendHouseList", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseOut<Map> recommend_list(String accessToken){
		logger.info("start to run recommend_list");
		
		try 
		{
			Map<String, Object> postDataMap = new HashMap<String, Object>();
			JSONObject result = HttpClientPostMethod.httpReqUrl(postDataMap, appProperties.getRequestRoot(), "getRecommod");
			String msg = result.get("msg").toString();
			String code = result.getString("code");
			if(code.equals(Constant.CODE_SUCCESSED)){
				JSONObject jsonData = result.getJSONObject("data");
				JSONArray array = jsonData.getJSONArray("list");
				List<RecommodHouse> list = new ArrayList<RecommodHouse>();
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					RecommodHouse recommodHouse = new RecommodHouse();
					recommodHouse.setHouseId(Integer.parseInt(jsonObject.getString("houseId")));
					recommodHouse.setHouseshop_id(Integer.parseInt(jsonObject.getString("houseshop_id")));
					recommodHouse.setMarket_price(Integer.parseInt(jsonObject.getString("market_price")));
					recommodHouse.setMemTotalAmt(Integer.parseInt(jsonObject.getString("memTotalAmt")));
					recommodHouse.setPartnership(jsonObject.getString("partnership"));
					recommodHouse.setRoom(Integer.parseInt(jsonObject.getString("room")));
					recommodHouse.setSpecials_stauts(Integer.parseInt(jsonObject.getString("specials_stauts")));
					
					net.sf.json.JSONObject jObject = net.sf.json.JSONObject.fromObject(jsonObject.getString("summaryInfo"));
					String name = jObject.getString("houseName");
					recommodHouse.setName(name);
					
					net.sf.json.JSONObject jbject = net.sf.json.JSONObject.fromObject(jsonObject.getString("showDetail"));
					String imageUrl = jbject.getString("introductionImg").split(",")[0];
					recommodHouse.setImageUrl(imageUrl);
					list.add(recommodHouse);
				}
				List<RecommondHouseVo> recommondHouseList = console2Vo(list,accessToken);
				Map map = new HashMap();
				map.put("houses", recommondHouseList);
				return new ResponseOut<Map>(Constant.CODE00000000, "", map);
			}else{
				return new ResponseOut<Map>(Constant.CODE00000002, msg);
			}
			
		} 
		catch (Exception e)
		{
			logger.error("Failed to search by keyword", e);
		}
		
		return new ResponseOut<Map>(Constant.CODE99999999, "未知异常");
	}

	private List<RecommondHouseVo> console2Vo(List<RecommodHouse> list,String accessToken) {
		List<RecommondHouseVo> rhList = new ArrayList<RecommondHouseVo>();
		for (RecommodHouse recommodHouse:list) {
			RecommondHouseVo recommondHouseVo = new RecommondHouseVo();
			recommondHouseVo.setHouseId(recommodHouse.getHouseId());
			recommondHouseVo.setPrice(recommodHouse.getMemTotalAmt());
			recommondHouseVo.setMarketPrice(recommodHouse.getMarket_price());
			recommondHouseVo.setHouseName(recommodHouse.getName());
			recommondHouseVo.setImgUrl(recommodHouse.getImageUrl());
			
			String room = String.valueOf(recommodHouse.getRoom());
			if (room!=null && !"".equals(room)) {
				recommondHouseVo.setDescription(room.substring(0, 1)+"室"+room.substring(1, 2)+"厅"+room.substring(2, 3)+"卫  "+room.substring(3, 4)+"床"
						+room.substring(6, 7)+"人");
			}
			//合作关系
			if (PartnershipEnum.MemberInn.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.MemberInn.getMessage());
			}else if (PartnershipEnum.Holding.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.Holding.getMessage());//控股
			}else if (PartnershipEnum.GuesthouseInn.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.GuesthouseInn.getMessage());//民宿贷客栈
			}else if (PartnershipEnum.DepthCooperation.getCode().equals(recommodHouse.getPartnership())) {
				recommondHouseVo.setSpecialTag(PartnershipEnum.DepthCooperation.getMessage());//深度合作
			}else {
				recommondHouseVo.setSpecialTag(PartnershipEnum.MemberInn.getMessage());
			}
			
			//折扣
			if (recommodHouse.getHouseshop_id()!=0&&recommodHouse.getMarket_price()>0) {
				float  discount = (float)recommodHouse.getMemTotalAmt()*10/(float)recommodHouse.getMarket_price();
				BigDecimal   b   =   new   BigDecimal(discount);  
				float   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();  
				recommondHouseVo.setDiscountDescription(f1+"折");
			}
			//收藏
			recommondHouseVo.setFavor(isFavor(recommodHouse.getHouseId(), accessToken));
			
			rhList.add(recommondHouseVo);
		}
		
		return rhList;
	}
	
}
