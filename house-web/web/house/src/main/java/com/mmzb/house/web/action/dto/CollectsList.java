package com.mmzb.house.web.action.dto;

import java.util.List;

public class CollectsList {
   private Integer hourseId;
	private List<CollectsInfo> collectsList;

	public Integer getHourseId() {
		return hourseId;
	}

	public void setHourseId(Integer hourseId) {
		this.hourseId = hourseId;
	}

	public List<CollectsInfo> getCollectsList() {
		return collectsList;
	}

	public void setCollectsList(List<CollectsInfo> collectsList) {
		this.collectsList = collectsList;
	}
}
