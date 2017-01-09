package com.mmzb.house.web.model;

import java.util.List;

import com.mmzb.house.web.action.dto.CityInfo;

/**
 * 热门城市返回Vo
 * @author dengbiao
 *
 */
public class HotCityVo {

	private List<CityInfo> hotList;//热门
	private List<CityInfo> allList;//所有
	
	public List<CityInfo> getHotList() {
		return hotList;
	}
	public void setHotList(List<CityInfo> hotList) {
		this.hotList = hotList;
	}
	public List<CityInfo> getAllList() {
		return allList;
	}
	public void setAllList(List<CityInfo> allList) {
		this.allList = allList;
	}
	
}