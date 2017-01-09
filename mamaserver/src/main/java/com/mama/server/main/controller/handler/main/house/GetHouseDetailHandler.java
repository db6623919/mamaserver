package com.mama.server.main.controller.handler.main.house;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mama.server.main.controller.ReturnCode;
import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.HouseShop;
import com.mama.server.main.service.CommentsService;
import com.mama.server.main.service.IHouseDetailService;
import com.mama.server.main.service.IHouseShopService;
import com.mama.server.main.vo.HouseCommentVo;
import com.mama.server.main.vo.HouseDetailVo;

/**
 * 获取房源详情
 * @author majiafei
 *
 */
@Component
public class GetHouseDetailHandler  extends BaseHandler
{
	@Resource
	private IHouseDetailService houseDetailService;

	@Resource
	private CommentsService commentsService;
	
	@Resource
	private IHouseShopService houseShopService;
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param)
	{
		try
		{
			if (log.isInfoEnabled()) 
			{
				log.info("start to run GetHouseDetailHandler:{}.", param);
			}
			
			
            if (param.get("houseId") == null)
            {
            	log.warn("failed to get house details, houseId is null");
                genErrOutputMapWithCode("param error, houseId required", ReturnCode.PARAM_ERROR);
                return outputMap;
            }
            
            int houseId = (Integer) param.get("houseId");
            
            //查询房源详情
            HouseDetailVo vo = houseDetailService.searchHouseDeatilPoById(houseId);
            if (vo == null)
            {
            	log.error("no house found, houseId={}.", houseId);
                genErrOutputMapWithCode("house not found", ReturnCode.HOUSE_NOT_FOUND_ERROR);
                return outputMap;
            }
            dataMap.put("houseInfo", vo);
            
            //获取房源详情推荐评论
            HouseCommentVo commentVo = commentsService.getHouseComments(houseId);
            if (commentVo != null)
            {
            	if (log.isDebugEnabled())
            	{
					log.debug("find comment of house", commentVo);
				}
            	dataMap.put("comment", commentVo);
			}
            
            //获取店铺信息
            if (vo.getShopId() > 0) 
            {
            	HouseShop shop = houseShopService.searchHouseShopPoById(String.valueOf(vo.getShopId()));
            	if(shop != null)
            	{
            		if (log.isDebugEnabled())
                	{
    					log.debug("find shop of house", shop);
    				}
            		dataMap.put("shop", shop);
            	}
			}
            
            genSuccOutputMap();
            
		}
		catch (Exception e)
		{
			log.error("failed to get house detail.", e);
			genErrOutputMap("查询失败");
		}
		
		return outputMap;
	}
	

}
