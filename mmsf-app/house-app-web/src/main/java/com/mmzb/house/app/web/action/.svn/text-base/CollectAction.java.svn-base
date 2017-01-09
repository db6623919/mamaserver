package com.mmzb.house.app.web.action;

import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mmzb.house.app.common.Constants;
import com.mmzb.house.app.common.http.HttpClientPostMethod;
import com.mmzb.house.app.common.util.AppProperties;
import com.mmzb.house.app.common.vo.Response;
import com.mmzb.house.app.common.vo.ResponseOut;
import com.mmzb.house.app.entity.HouseEntity;
import com.mmzb.house.app.entity.HousePagerEntity;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.vo.CollectsPageVo;
import com.mmzb.house.app.vo.HouseSearchConditionVo;
import com.mmzb.house.app.vo.HouseSearchVo;
import com.mmzb.house.app.vo.HouseVo;
import com.mmzb.house.app.vo.Page;
import com.mmzb.house.app.vo.UserInfoVo;
import com.mmzb.house.app.web.util.CommonUtil;

/**
 * 收藏
 * @author whl
 *
 */
@Controller
public class CollectAction {

	@Resource(name = "appProperties")
	private AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(CollectAction.class);
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
    
    @Autowired
    private MemberTokenService memberTokenService;
    
	/**
	 * 房源收藏取消
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/my/setFavor", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<String> setFavor(HttpServletRequest request,HttpServletResponse response,int houseId,Boolean favor) {
		if (logger.isInfoEnabled()) {
			logger.info("start to run /app/setFavor");
		}
		
//		UserInfoVo userInfo = LoginCache.getLoginInfoFromRedis(request.getParameter("accessToken"));
		UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
		
		String memberId = userInfo.getMemberId();
		try {
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("houseId", houseId);
			dataMap.put("memberId", memberId);
			dataMap.put("favor", favor);
			Response  respose  = HttpClientPostMethod.httpReqUrl(dataMap, appProperties.getRequestRoot(), "addOrDeleCollect", null);
			if(respose.getCode().equals("0")) {
				if (logger.isInfoEnabled()) {
				    logger.info("/app/setFavor is finished,params is {}.");
				}
				return new ResponseOut<String>(Constants.APP_SUCCESSED,"","");
			} else if (respose.getCode().equals("13")) {
				return new ResponseOut<String>(Constants.APP_FAILED,"房源已收藏","");
			} else if (respose.getCode().equals("14")) {
				return new ResponseOut<String>(Constants.APP_FAILED,"房源已取消收藏","");
			}
		} catch (Exception e) {
			logger.error("setFavor:房源收藏取消失败.",e);
			return new ResponseOut<String>(Constants.APP_FAILED,"房源收藏取消失败","");
		}
		return new ResponseOut<String>(Constants.APP_SUCCESSED,"","");
	}
	
	@RequestMapping(value = "/app/my/myFavorList", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseOut<HouseSearchVo> myFavorList(HttpServletRequest request,HttpServletResponse response) {
		
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int sort = 0;
		if(!StringUtils.isEmpty(request.getParameter("sort"))) {
			sort = Integer.parseInt(request.getParameter("sort"));
		}
		UserInfoVo userInfo = memberTokenService.getLoginInfoFromRedis(request.getParameter("accessToken"));
		HouseSearchVo houseSearchVo = new HouseSearchVo();
		try {
			Map<String, Object> postData=new HashMap<String, Object>();
			postData.put("uid", userInfo.getMemberId());
	    	postData.put("currentPage", pageIndex);
	    	postData.put("pageSize", pageSize);
			Response<CollectsPageVo> result= HttpClientPostMethod.httpReqUrl(postData, appProperties.getRequestRoot(),"getCollectsPage",CollectsPageVo.class);
			if(result.getCode().equals("0") && result.getData().getPage().size() > 0) {
				CollectsPageVo collectsPage = (CollectsPageVo)result.getData();
				//房源id(1,2,3)
				int size = collectsPage.getPage().size();
				int[] houseIds = new int[size];
				for(int i = 0 ; i < size ; i ++) {
					houseIds[i] = collectsPage.getPage().get(i).getHouseId();
				}
				Map<String,Object> map = new HashMap<String, Object>();
				HouseSearchConditionVo houseSearchCondition = new HouseSearchConditionVo();
				houseSearchCondition.setHouseIds(houseIds);
				houseSearchCondition.setSort(sort);
				map.put("condition", houseSearchCondition);
				//房源列表信息
				Response<HousePagerEntity> rsp = HttpClientPostMethod.httpReqUrl(map, appProperties.getRequestRoot(), "appHouseSearch", HousePagerEntity.class);
				
				if (rsp != null && "0".equals(rsp.getCode())) {
					//获取房源列表信息
					HousePagerEntity housePagerEntity = rsp.getData();
					List<HouseEntity> houseEntities = null;
					int itemCount = 0;
					if (housePagerEntity != null) {
						itemCount = housePagerEntity.getItemCount();
						houseEntities = housePagerEntity.getHouses();
					}
					
					List<HouseVo> houseVos = null;
					if (houseEntities != null) {
						houseVos = convert2HouseVos(houseEntities);
						houseSearchVo.setHouses(houseVos);
					}
					
					if (logger.isDebugEnabled()) {
						logger.debug("get the houses info:{}", houseVos);
					}
					
	    			//设置分页信息
	    			Page pager = new Page();
	    			pager.setItemCount(collectsPage.getTotal());
	    			pager.setPageIndex(pageIndex);
	    			pager.setPageSize(pageSize);
	    			houseSearchVo.setPager(pager);
				}
			}
		} catch (Exception e) {
			logger.error("/app/myFavorList:房源收藏列失败.",e);
			return new ResponseOut<HouseSearchVo>(Constants.APP_FAILED,"房源收藏列表调用失败!",null);
		}
		return new ResponseOut<HouseSearchVo>(Constants.APP_SUCCESSED,"",houseSearchVo);
	}
	
	private List<HouseVo> convert2HouseVos(List<HouseEntity> houseEntities)
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
			houseVo.setIsFavor(true);
			houseVos.add(houseVo);
		}
		
		return houseVos;
	}
	
}
