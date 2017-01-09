package com.mmzb.house.web.action.dto;

public class City {
    
    private Integer cityId; //城市编号
    private String name;    //城市名
    private Integer provId;
    private Integer removed;
    private String showDetail;
    private Integer sort;
    private Integer type;
    
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProvId() {
		return provId;
	}
	public void setProvId(Integer provId) {
		this.provId = provId;
	}
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	public String getShowDetail() {
		return showDetail;
	}
	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}