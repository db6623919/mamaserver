package com.mama.server.main.controller.handler.main.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.common.entity.NameIdEntity;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.CityPo;
import com.mama.server.main.dao.model.TradeArea;
import com.mama.server.main.service.IHouseSearchService;

/**
 * 根据关键字搜索
 * @author lenovo
 *
 */
@Component
public class KWSearchHandler extends BaseHandler
{
	//记录日志
	private static final Logger log = LoggerFactory.getLogger(KWSearchHandler.class);
	
	@Resource
	private IHouseSearchService houseSearchService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		log.info("start to run KWSearchHandler.keyWord="+(String) param.get("keyword")+"&type="+(String)param.get("type"));
		
		String keyWord = (String) param.get("keyword");
		String type  = (String)param.get("type");
		
		if (StringUtils.isEmpty(keyWord))
		{
			log.warn("the keyword is null,return null");
			genSuccOutputMap();
			return outputMap;
		}
		
		try 
		{
			//1.查找商圈
			dataMap.put("areas", getTradeAreas(keyWord));
			
			//2.查找城市
			if ("H5".equals(type)) {
				dataMap.put("cities", getCities(keyWord));
			}
			
			
			//3.查找房源
			dataMap.put("houses", houseSearchService.searchByName(keyWord));
			
			genSuccOutputMap();
			
		} 
		catch (Exception e) 
		{
			genErrOutputMap("查询失败");
			log.error("failed to search by keyword:{}.", keyWord);
		}
		
		log.info("end of KWSearchHandler,keyWord is {}.", keyWord);
		
		return outputMap;
	}

	private List<NameIdEntity> getTradeAreas(String keyWord)
	{
		TradeArea area = new TradeArea();
		area.setName(keyWord);
		List<TradeArea> tradeAreaPos = mainService.getTradeAreaByPar(area);
		if (CollectionUtils.isEmpty(tradeAreaPos))
		{
			log.warn("find no TradeAreas. keyWord is {}.", keyWord);
			return null;
		}
		
		List<NameIdEntity> tradeAreas = new ArrayList<NameIdEntity>();
		if (tradeAreaPos.size()>10) {
			tradeAreaPos.subList(10, tradeAreaPos.size()).clear();//取前十条
		}
		for(TradeArea po : tradeAreaPos){
			NameIdEntity entity = new NameIdEntity();
			entity.setId(po.getId());
			CityPo cityPo = mainService.getCityById(po.getCityId());
			entity.setName(po.getName()+" ("+cityPo.getName()+")");
			
			entity.setParentId(po.getCityId());
			tradeAreas.add(entity);
		}
		
		return tradeAreas;
	}
	
	private List<NameIdEntity> getCities(String keyWord)
	{
		CityPo cp = new CityPo();
		cp.setName(keyWord);
		List<CityPo> cityPos = mainService.getCityByName(cp);
		if (CollectionUtils.isEmpty(cityPos)) 
		{
			log.warn("find no citis. keyWord is {}.", keyWord);
			return null;
		}
		
		List<NameIdEntity> cities = new ArrayList<NameIdEntity>();
		for(CityPo po : cityPos)
		{
			NameIdEntity city = new NameIdEntity();
			city.setId(po.getCityId());
			city.setName(po.getName());
			city.setParentId(po.getCityId());
			cities.add(city);
		}
		
		return cities;		
	}
}
