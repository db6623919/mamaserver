package com.mmzb.house.app.vo;

import java.util.Map;

/**
 * 发现详情房源Ids
 * @author whl
 *
 */
public class TopicHousesVo {
	private Map<Integer,Object> partnershipMap;
	//房源IDs
	private String houseIds;
	//房源总个数
	private int houseCount;
	
	
	public Map<Integer, Object> getPartnershipMap() {
		return partnershipMap;
	}
	public void setPartnershipMap(Map<Integer, Object> partnershipMap) {
		this.partnershipMap = partnershipMap;
	}
	public String getHouseIds() {
		return houseIds;
	}
	public void setHouseIds(String houseIds) {
		this.houseIds = houseIds;
	}
	public int getHouseCount() {
		return houseCount;
	}
	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}
	
}
