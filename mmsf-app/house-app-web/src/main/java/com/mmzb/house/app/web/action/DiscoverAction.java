package com.mmzb.house.app.web.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.server.common.errorCode.ReturnCode;
import com.mama.server.common.util.PartnershipEnum;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.HouseEntity;
import com.mmzb.house.app.entity.HousePagerEntity;
import com.mmzb.house.app.entity.TopicActivityEntity;
import com.mmzb.house.app.entity.TopicEntity;
import com.mmzb.house.app.vo.HouseSearchConditionVo;
import com.mmzb.house.app.vo.HouseSearchVo;
import com.mmzb.house.app.vo.HouseVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.TopicHousesVo;
import com.mmzb.house.app.vo.TopicListVo;
import com.mmzb.house.app.vo.TopicsVo;
import com.mmzb.house.app.web.util.CommonUtil;
import com.netfinworks.common.lang.StringUtil;

/**
 * 发现
 * @author whl
 *
 */
@Controller
public class DiscoverAction {

	//参数配置
	@Resource(name = "appProperties")
	private AppProperties appProperties;
	
    //日志打印
	private Logger logger = LoggerFactory.getLogger(CollectAction.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
	/**
	 * 发现首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "app/discoverIndex", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public ResponseOut<TopicsVo> index(HttpServletRequest request, HttpServletResponse response)  {
		logger.info("run start to app/discoverIndex.");
		TopicsVo topicsVo = new TopicsVo();
		try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Response<TopicListVo> respose = HttpClientPostMethod.httpReqUrl(dataMap, appProperties.getRequestRoot(), "getTopics", TopicListVo.class);
			if (ReturnCode.OK == Integer.parseInt(respose.getCode())) {
				TopicListVo topicListVo = respose.getData(); 
				List<TopicEntity> topicList = this.changeVo(topicListVo.getTopicList());
				topicsVo.setTopics(topicList);
			}
		} catch (Exception e) {
			logger.error("app/discoverIndex:发现首页获取失败.",e);
			return new ResponseOut<TopicsVo>(Constants.APP_FAILED,"发现首页列表获取失败.",null);
		}
		logger.info("run end to app/discoverIndex.");
		return new ResponseOut<TopicsVo>(Constants.APP_SUCCESSED,"",topicsVo);
	}
	
	//vo轉換
	public List<TopicEntity> changeVo(List<TopicActivityEntity> topicActivityList) {
		List<TopicEntity> topicList = new ArrayList<TopicEntity>();
		for (int i = 0 ; i < topicActivityList.size() ; i ++) {
			TopicEntity topic = new TopicEntity();
			topic.setName(topicActivityList.get(i).getActivityName());
			topic.setDescription(topicActivityList.get(i).getIntroduction());
			topic.setHouseCount(topicActivityList.get(i).getShopCount());
			topic.setImgUrl(topicActivityList.get(i).getImgUrl());
			topic.setTopicId(topicActivityList.get(i).getId());
			topic.setSubTitle(topicActivityList.get(i).getTitle());
			topicList.add(topic);
		}
		return topicList;
	}
	
	/**
	 * 发现详情页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "app/discoverList", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public ResponseOut<HouseSearchVo> detail(HttpServletRequest request)  {
		logger.info("run star to app/discoverList.");
		HouseSearchVo houseSearchVo = new HouseSearchVo();
		try {
			int topicId = Integer.parseInt(request.getParameter("topicId")); //主题活动ID
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			//获取活动相关房源ID
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("topicId", topicId);
			dataMap.put("pageNum", pageIndex);
			dataMap.put("pageSize", pageSize);
			Response<TopicHousesVo> response = HttpClientPostMethod.httpReqUrl(dataMap, appProperties.getRequestRoot(), "getTopicHouses",TopicHousesVo.class);
			if (ReturnCode.OK == Integer.parseInt(response.getCode())) {
				TopicHousesVo topicHouses = response.getData();
				
				if (!StringUtil.isEmpty(topicHouses.getHouseIds())) {
					Map<Integer,Object> partnershipMap = topicHouses.getPartnershipMap();
					//房源ids字符串为数组
					String[] houseIds = topicHouses.getHouseIds().split(",");
					int[] ids = new int[houseIds.length];
					for(int i = 0 ; i < houseIds.length ; i ++) {
						ids[i] = Integer.parseInt(houseIds[i]);
					}
					Map<String,Object> map = new HashMap<String, Object>();
					HouseSearchConditionVo houseSearchCondition = new HouseSearchConditionVo();
					houseSearchCondition.setHouseIds(ids);
					map.put("condition", houseSearchCondition);
					//房源列表信息
					Response<HousePagerEntity> rsp = HttpClientPostMethod.httpReqUrl(map, appProperties.getRequestRoot(), "appHouseSearch", HousePagerEntity.class);
					if (rsp != null && ReturnCode.OK == Integer.parseInt(rsp.getCode())) {
						//获取房源列表信息
						HousePagerEntity housePagerEntity = rsp.getData();
						List<HouseEntity> houseEntities = null;
						if (housePagerEntity != null) {
							houseEntities = housePagerEntity.getHouses();
						}
						
						List<HouseVo> houseVos = null;
						if (houseEntities != null) {
							houseVos = convert2HouseVos(houseEntities,partnershipMap);
							houseSearchVo.setHouses(houseVos);
						}
				}
				
					
	    			//设置分页信息
	    			Page pager = new Page();
	    			pager.setItemCount(response.getData().getHouseCount());
	    			pager.setPageIndex(pageIndex);
	    			pager.setPageSize(pageSize);
	    			houseSearchVo.setPager(pager);
				}	
			}
			
		} catch (Exception e) {
			logger.error("app/discoverList:发现详情页获取数据异常.",e);
			return new ResponseOut<HouseSearchVo>(Constants.APP_FAILED,"发现详情页获取数据异常.",null);
		}
		logger.info("run end to app/discoverList.");
		return new ResponseOut<HouseSearchVo>(Constants.APP_SUCCESSED,"",houseSearchVo);
	}
		
	/**
	 * Vo转换
	 * @param houseEntities
	 * @return
	 */
		private List<HouseVo> convert2HouseVos(List<HouseEntity> houseEntities,Map<Integer,Object> partnershipMap)
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
				String description = entity.getRoomCount() + "室 宜" + entity.getPeopleCount() + "人居住";
				houseVo.setDescription(description);
				houseVo.setHouseUnit("起/晚");
				//获取房源标签
				houseVo.setTagNames(getTagNames(entity.getTagList()));
				String partnership = "";
				if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.MemberInn.getCode())) {
					partnership = PartnershipEnum.MemberInn.getMessage(); //会员
				} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.Holding.getCode())) {
					partnership = PartnershipEnum.Holding.getMessage(); //控股
				} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.DepthCooperation.getCode())) {
					partnership = PartnershipEnum.DepthCooperation.getMessage();  //深度
				} else if (String.valueOf(partnershipMap.get(entity.getHouseId())).equals(PartnershipEnum.GuesthouseInn.getCode())) {
					partnership = PartnershipEnum.GuesthouseInn.getMessage(); //民宿
				} else if (String.valueOf(partnershipMap.get(String.valueOf(entity.getHouseId()))).equals(PartnershipEnum.JointBusinessInn.getCode())) {
					partnership = PartnershipEnum.JointBusinessInn.getMessage();//联合运营客栈
				}
				houseVo.setSpecialTag(partnership);
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
		
		/**
		 * 获取标签
		 * @param houseTagId
		 * @return
		 */
		private List<String> getTagNames(List<Integer> houseTagId) {
			try {
				//String houseTag = RedisHelper.get(RedisConstants.HOUSE_TAG_KEY);
				RedisRequest redisRequest = new RedisRequest(); 
				redisRequest.setKey("mmsf:allHouseTag");
				String houseTag = redisFacade.getValueByKey(redisRequest);
				if ((houseTag != null) && !CollectionUtils.isEmpty(houseTagId)) {
					//只取前三个标签
					houseTag = "[" + houseTag + "]";
					JSONArray houseTags = JSONArray.parseArray(houseTag);
					List<String> tagNames = new ArrayList<String>();
					int tagSize = (houseTagId.size() > 3) ? 3 : houseTagId.size();
					for(int i = 0; i < tagSize; i++) {
						for(int y = 0 ; y < houseTags.size() ; y ++) {
							com.alibaba.fastjson.JSONObject tagObj = houseTags.getJSONObject(y);
							if (tagObj.getInteger("id") == houseTagId.get(i)) {
								tagNames.add(tagObj.getString("name"));
								break;
							}
						}
					}
					return tagNames;
				}
			} catch (Exception e) {
				logger.error("failed to get tags", e);
			}
			return null;
		}
}
