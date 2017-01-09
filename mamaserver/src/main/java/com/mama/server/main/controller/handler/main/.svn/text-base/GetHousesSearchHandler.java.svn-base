package com.mama.server.main.controller.handler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mama.server.main.service.IHouseSearchService;

@Component
public class GetHousesSearchHandler extends BaseHandler {

	@Autowired
	private IHouseSearchService houseSearchService;
    
	@SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> handle(HashMap<String, Object> param) {
        try {
        	int[] houseIds = null;
        	if(param.get("houseIds") != null){
        		String houseIdStr = (String)param.get("houseIds");
        		String[] houseIdArray = houseIdStr.split(",");
        		houseIds = new int[houseIdArray.length];
        		for(int i = 0; i < houseIdArray.length; i++)
        		{
        			houseIds[i] = Integer.valueOf(houseIdArray[i]);
        		}
        		
        	}
        	
        	int cityId = -1;
        	if (param.get("cityId") != null) {
        		cityId = (Integer)param.get("cityId");
            }
        	
        	int merLocationId = -1;//商圈位置id
           	if (param.get("merLocationId") != null) {
           		merLocationId = (Integer)param.get("merLocationId");
            }
        	
        	int priceRange = -1;  //价格范围
        	if (param.get("priceRange") != null) {
        		priceRange = (Integer)param.get("priceRange");
            }
        	
        	int personNum = -1;    //人数
        	if (param.get("personNum") != null) {
        		personNum = (Integer)param.get("personNum");
            }
        	
        	int roomNum = -1;     //户型房间数 标签：{ " tagList ":["1","2","3"]}，1、2、3分别代表"经济型","舒适型","海景房"
        	if (param.get("roomNum") != null) {
        		roomNum = (Integer)param.get("roomNum");
            }
            
        	String keyWord = null;  //关键字
        	if (param.get("keyWord") != null) {
        		keyWord = (String)param.get("keyWord");
            }
        	
        	String tag = null;//标签
        	List<Integer> tagList = new ArrayList<Integer>();
        	if (param.get("tag") != null) {
				tag = (String)param.get("tag");
				String[] tagArr = tag.split(",");
				for (String str:tagArr) {
					if (null!=str && !"".equals(str)) {
						tagList.add(Integer.parseInt(str));
					}
				}
			}
        	
        	int sortBy = -1;//排序
        	if (param.get("sortBy") != null) {
        		sortBy = (Integer)param.get("sortBy");
			}
        	
        	int pageNo = -1;//第几页
        	if (param.get("pageNo") != null) {
        		pageNo = (Integer)param.get("pageNo");
			}
        	
        	int numPerPage = 10;//每页显示几条记录
        	
        	SearchCondition searchCondition = new SearchCondition();
        	searchCondition.setCityId(cityId);
        	searchCondition.setMerLocationId(merLocationId);
        	searchCondition.setPriceRange(priceRange);
        	searchCondition.setPersonNum(personNum);
        	searchCondition.setRoomNum(roomNum);
        	searchCondition.setKeyWord(keyWord);
        	searchCondition.setTagList(tagList);
        	searchCondition.setHouseIds(houseIds);
        	QueryResultVo queryResultVo = new QueryResultVo();
        	queryResultVo = houseSearchService.searchHouseSourceAdvanced(searchCondition, sortBy, pageNo, numPerPage);
        	/** 页房源列表 */
        	List<HouseSearchPo> houseSourceList = queryResultVo.getSourceList();
        	//总记录数
        	long totalNum = queryResultVo.getTotalNum();
        	
        	dataMap.put("totalPage", totalNum);
            dataMap.put("list", houseSourceList);
                
            genSuccOutputMap();

        } catch (Exception e) {
            genErrOutputMap("interface error");
        }
        return outputMap;
    }
}
