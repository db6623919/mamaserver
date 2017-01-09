package com.mama.server.main.dao.mongodb.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mama.server.main.dao.model.mongodb.AggHouseId;
import com.mama.server.main.dao.model.mongodb.DateInventory;
import com.mama.server.main.dao.model.mongodb.HouseInventoryPo;
import com.mama.server.main.dao.model.mongodb.HouseNameIdPo;
import com.mama.server.main.dao.model.mongodb.HouseSearchPo;
import com.mama.server.main.dao.mongodb.IHouseSearchDao;
import com.mama.server.main.dao.vo.KeyWordVo;
import com.mama.server.main.dao.vo.QueryResultVo;
import com.mama.server.main.dao.vo.SearchCondition;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@Component
public class HouseSearchDaoImpl implements IHouseSearchDao {
	
	private static final long DAY_TIME = 24 * 60 * 60 * 1000;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
	
	@Override
	public void addHouseSource(HouseSearchPo houseSearch) {
		mongoTemplate.insert(houseSearch);
	}

	@Override
	public void updateHouseSource(HouseSearchPo houseSearch) {
		Query query = new Query(where("_id").is(houseSearch.getHouseId()));
		Update update = new Update();
		if(houseSearch.getRemoved() != 1) {
			update.set("cityId", houseSearch.getCityId());
			update.set("name", houseSearch.getName());
			update.set("businessAreaList", houseSearch.getBusinessAreaList());
			update.set("price", houseSearch.getPrice());
			update.set("priceRange", houseSearch.getPriceRange());
			update.set("personNum", houseSearch.getPersonNum());
			update.set("roomNum", houseSearch.getRoomNum());
			update.set("tagList", houseSearch.getTagList());
			update.set("keyWord", houseSearch.getKeyWord());
			update.set("imageUrl", houseSearch.getImageUrl());
			update.set("isRecommend", houseSearch.getIsRecommend());
			update.set("recommendTime", houseSearch.getRecommendTime());
			update.set("score", houseSearch.getScore());
			update.set("onLineTime", houseSearch.getOnLineTime());
			update.set("checkOutTime", houseSearch.getCheckOutTime());
			update.set("marketPrice", houseSearch.getMarketPrice());
		}
		update.set("removed", houseSearch.getRemoved());
		mongoTemplate.updateFirst(query, update, HouseSearchPo.class);
	}
	
	/** 根据房源id修改商圈名称 */
	public void updateLocationIdAndName(long houseId, List<Integer> businessAreaList, List<KeyWordVo> keyWord) {
		Query query = new Query(where("_id").is(houseId));
		Update update = new Update();
		update.set("businessAreaList", businessAreaList);
		update.set("keyWord", keyWord);
		mongoTemplate.updateFirst(query, update, HouseSearchPo.class);
	}

	@Override
	public void deleteHouseSourceById(long houseId) {
		Query query = new Query(where("_id").is(houseId));
		Update update = new Update();
		update.set("removed", 0);
		mongoTemplate.updateFirst(query, update, HouseSearchPo.class);
	}

	/** 搜索相关房源 */
	public QueryResultVo queryHouseSourceByCondition(SearchCondition searchCondition, int sortBy,
			int pageNo, int numPerPage) {

		QueryResultVo resultVo = new QueryResultVo();
		Query query = new Query();
		Criteria criatira = new Criteria();
		
		if(searchCondition.getCityId() > 0) {
			criatira.and("cityId").is(searchCondition.getCityId());
		}
		if(searchCondition.getMerLocationId() > 0){
			criatira.and("businessAreaList").is(searchCondition.getMerLocationId());
		}
		if(searchCondition.getPriceRange() > 0) {
			criatira.and("priceRange").is(searchCondition.getPriceRange());
		}
		if(searchCondition.getPersonNum() > 0) {
			criatira.and("personNum").gte(searchCondition.getPersonNum());
		}
		if(searchCondition.getRoomNum() > 0) {
			criatira.and("roomNum").gte(searchCondition.getRoomNum());
		}
		
		int[] houseIds = searchCondition.getHouseIds();
		if((houseIds != null) && (houseIds.length > 0)) {
		    BasicDBList values = new BasicDBList();
		    for (int houseId : houseIds) {
		    	values.add(houseId);
			}
			criatira.and("_id").in(values);
		}
		
		if(isListNotEmpty(searchCondition.getTagList())){
			Criteria[] queryCriteria = new Criteria[searchCondition.getTagList().size()];
			List<Criteria> querList = new ArrayList<Criteria>();
			for(int str : searchCondition.getTagList()) {
				querList.add(Criteria.where("tagList").is(str));
			}
			criatira.orOperator(querList.toArray(queryCriteria));
		}
		if(isStrNotEmpty(searchCondition.getKeyWord())) {
			criatira.and("keyWord.value").regex(searchCondition.getKeyWord());
		}
		criatira.and("removed").is(0);
		
		Sort sort;
		if(sortBy == 2) {
			sort = new Sort(Direction.ASC, "price");
		} else if(sortBy == 3) {
			sort = new Sort(Direction.DESC, "price");
		} else {
			sort = new Sort(Direction.DESC, "recommendTime");
		}
	        
		long totalNum = 0;
		long leavedTime = searchCondition.getLeavedTime();
		
		long livedTime = searchCondition.getLivedTime();
		long count = (leavedTime - livedTime) / DAY_TIME;
		
		if ((leavedTime > 0) && (livedTime > 0)){
			String levedT = simpleDateFormat.format(leavedTime);
			String livedT = simpleDateFormat.format(livedTime);
			criatira.and("onLineTime").lte(livedT.trim());
			criatira.and("checkOutTime").gte(levedT.trim());
				
			Query aggQuery = new Query();
			aggQuery.addCriteria(criatira);
			aggQuery.with(sort);
			if((null == houseIds) || (houseIds.length == 0)) {
				int skip = (pageNo - 1) * numPerPage;
				aggQuery.skip(skip);// skip相当于从那条记录开始  
				aggQuery.limit(numPerPage);// 从skip开始,取多少条记录 
			}
			List<HouseSearchPo> pos = mongoTemplate.find(aggQuery, HouseSearchPo.class);
			resultVo.setSourceList(pos);
			
			query.addCriteria(criatira);
			totalNum = mongoTemplate.count(query, HouseSearchPo.class);
			resultVo.setTotalNum(totalNum);
			return resultVo;
			
		}
		
		query.addCriteria(criatira);
		
		/** 总记录数 */
		totalNum = mongoTemplate.count(query, HouseSearchPo.class);  
		
		query.with(sort);
		
		if((null == houseIds) || (houseIds.length == 0)) {
			int skip = (pageNo - 1) * numPerPage;
			query.skip(skip);// skip相当于从那条记录开始  
	        query.limit(numPerPage);// 从skip开始,取多少条记录 
		}
        List<HouseSearchPo> list = mongoTemplate.find(query, HouseSearchPo.class);
        resultVo.setSourceList(list);
        resultVo.setTotalNum(totalNum);
		return resultVo;
	
	}
	
	/** 嘉飞写的以前的搜索相关房源 */
//	public QueryResultVo queryHouseSourceByCondition(SearchCondition searchCondition, int sortBy,
//			int pageNo, int numPerPage) {
//		QueryResultVo resultVo = new QueryResultVo();
//		Query query = new Query();
//		Criteria criatira = new Criteria();
//		
//		if(searchCondition.getCityId() > 0) {
//			criatira.and("cityId").is(searchCondition.getCityId());
//		}
//		if(searchCondition.getMerLocationId() > 0){
//			criatira.and("businessAreaList").is(searchCondition.getMerLocationId());
//		}
//		if(searchCondition.getPriceRange() > 0) {
//			criatira.and("priceRange").is(searchCondition.getPriceRange());
//		}
//		if(searchCondition.getPersonNum() > 0) {
//			criatira.and("personNum").gte(searchCondition.getPersonNum());
//		}
//		if(searchCondition.getRoomNum() > 0) {
//			criatira.and("roomNum").gte(searchCondition.getRoomNum());
//		}
//		
//		int[] houseIds = searchCondition.getHouseIds();
//		if((houseIds != null) && (houseIds.length > 0)) {
//		    BasicDBList values = new BasicDBList();
//		    for (int houseId : houseIds) {
//		    	values.add(houseId);
//			}
//			criatira.and("_id").in(values);
//		}
//		
//		if(isListNotEmpty(searchCondition.getTagList())){
//			Criteria[] queryCriteria = new Criteria[searchCondition.getTagList().size()];
//			List<Criteria> querList = new ArrayList<Criteria>();
//			for(int str : searchCondition.getTagList()) {
//				querList.add(Criteria.where("tagList").is(str));
//			}
//			criatira.orOperator(querList.toArray(queryCriteria));
//		}
//		if(isStrNotEmpty(searchCondition.getKeyWord())) {
//			criatira.and("keyWord.value").regex(searchCondition.getKeyWord());
//		}
//		criatira.and("removed").is(0);
//		
//		Sort sort;
//		if(sortBy == 2) {
//			sort = new Sort(Direction.ASC, "price");
//		} else if(sortBy == 3) {
//			sort = new Sort(Direction.DESC, "price");
//		} else {
//			sort = new Sort(Direction.DESC, "recommendTime");
//		}
//		
//		long totalNum = 0;
//		long leavedTime = searchCondition.getLeavedTime();
//		long livedTime = searchCondition.getLivedTime();
//		long count = (leavedTime - livedTime) / DAY_TIME;
//		if ((leavedTime > 0) && (livedTime > 0))
//		{
//			List<AggregationOperation> aggs = new ArrayList<AggregationOperation>();
//			aggs.add(Aggregation.match(criatira));
//			
//			//排序
//			aggs.add(Aggregation.sort(sort));			
//			
//			//按时间过滤
//			aggs.add(Aggregation.unwind("inventory"));
//			aggs.add(Aggregation.match(Criteria.where("inventory.date").lt(leavedTime).gte(livedTime)));
//			aggs.add(Aggregation.match(Criteria.where("inventory.inventory").ne(0)));
//			aggs.add(Aggregation.group("_id").count().as("count"));
//			aggs.add(Aggregation.match(Criteria.where("count").is(count)));
//			AggregationOperation groupCount = Aggregation.group("count").count().as("totalNum");
//			aggs.add(groupCount);
//			
//			Aggregation aggregation = Aggregation.newAggregation(aggs);
//			AggregationResults<BasicDBObject> countResults = mongoTemplate.aggregate(aggregation, "house.search", BasicDBObject.class);
//			List<BasicDBObject> countList = countResults.getMappedResults();
//			if (countList.size() > 0) {
//				totalNum = countList.get(0).getLong("totalNum");
//			}
//			
//			
//			aggs.remove(groupCount);
//			aggs.add(Aggregation.project().and("_id").as("houseId"));
////			aggs.add(Aggregation.skip((pageNo - 1) * numPerPage));
////			aggs.add(Aggregation.limit(numPerPage));
//			
//			Aggregation aggregation1 = Aggregation.newAggregation(aggs);
//			AggregationResults<BasicDBObject> idResults = mongoTemplate.aggregate(aggregation1, "house.search", BasicDBObject.class);
//			List<BasicDBObject> idList = idResults.getMappedResults();
//			if (CollectionUtils.isNotEmpty(idList))
//			{
//				BasicDBList idDbList = new BasicDBList();
//				for(BasicDBObject object : idList)
//				{
//					idDbList.add(object.getInt("houseId"));
//				}
//				
//				Query aggQuery = new Query();
//				aggQuery.addCriteria(Criteria.where("houseId").in(idDbList));
//				aggQuery.with(sort);
//				if((null == houseIds) || (houseIds.length == 0)) {
//					int skip = (pageNo - 1) * numPerPage;
//					aggQuery.skip(skip);// skip相当于从那条记录开始  
//					aggQuery.limit(numPerPage);// 从skip开始,取多少条记录 
//				}
//				List<HouseSearchPo> pos = mongoTemplate.find(aggQuery, HouseSearchPo.class);
//				resultVo.setSourceList(pos);
//				resultVo.setTotalNum(totalNum);
//				return resultVo;
//			}
//			
//		}
//		
//		query.addCriteria(criatira);
//		
//		/** 总记录数 */
//		totalNum = mongoTemplate.count(query, HouseSearchPo.class);  
//		
//		query.with(sort);
//		
//		if((null == houseIds) || (houseIds.length == 0)) {
//			int skip = (pageNo - 1) * numPerPage;
//			query.skip(skip);// skip相当于从那条记录开始  
//	        query.limit(numPerPage);// 从skip开始,取多少条记录 
//		}
//        List<HouseSearchPo> list = mongoTemplate.find(query, HouseSearchPo.class);
//        resultVo.setSourceList(list);
//        resultVo.setTotalNum(totalNum);
//		return resultVo;
//	}
	
	public HouseSearchPo findHouseSearchPoById(long houseId) {
		return mongoTemplate.findById(houseId, HouseSearchPo.class);
	}
	
	private boolean isStrNotEmpty(String str) {
		if(str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	private boolean isListNotEmpty(List list) {
		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<HouseNameIdPo> findByHouseName(String name)
	{
		Criteria criatira = new Criteria();
		Criteria criteriaKw = Criteria.where("key").is("name").and("value").regex(name);
		criatira.and("keyWord").elemMatch(criteriaKw);
		criatira.and("removed").is(0);
		Query query = new Query();
		query.addCriteria(criatira);
		query.skip(0);
		query.limit(10);
				
		List<HouseNameIdPo> pos = mongoTemplate.find(query, HouseNameIdPo.class);
		
		return pos;
	}

	@Override
	public void updateInventory(long houseId, Map<Long, Integer> inventory)
	{
		if (inventory == null)
		{
			return;
		}
		
		for(Map.Entry<Long, Integer> entry : inventory.entrySet())
		{
			Update update = new Update();
			update.inc("inventory.$.inventory", entry.getValue());
			
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("_id").is(houseId), Criteria.where("inventory").elemMatch(Criteria.where("date").is(entry.getKey())));
			Query query = Query.query(criteria);
			mongoTemplate.updateFirst(query, update, HouseInventoryPo.class);
		}
	}

	@Override
	public void addInventory(long houseId, Map<Long, Integer> inventory)
	{
		if (inventory == null)
		{
			return;
		}
		
		List<DateInventory> inventories = new ArrayList<DateInventory>();
		for(Map.Entry<Long, Integer> entry : inventory.entrySet())
		{
			DateInventory dateInventory = new DateInventory(entry.getKey(), entry.getValue());
			inventories.add(dateInventory);
		}
		
		Update update = new Update();
		update.push("inventory", inventories);
		Query query = new Query(where("_id").is(houseId));
		
		mongoTemplate.updateFirst(query, update, HouseInventoryPo.class);
	}

	@Override
	public void delInventory(long houseId, List<Long> dates)
	{
		if (CollectionUtils.isEmpty(dates))
		{
			return;
		}
		
		List<BasicDBObject> objects = new ArrayList<BasicDBObject>();
		Update update = new Update();
		update.pull("inventory", new BasicDBObject("date", new BasicDBObject("$in", dates)));
		
		Query query = Query.query(Criteria.where("_id").is(houseId));
		mongoTemplate.updateFirst(query, update, HouseInventoryPo.class);
	}
	
}
