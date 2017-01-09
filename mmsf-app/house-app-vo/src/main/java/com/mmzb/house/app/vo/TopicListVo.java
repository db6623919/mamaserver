package com.mmzb.house.app.vo;

import java.util.List;

import com.mmzb.house.app.entity.TopicActivityEntity;

/**
 * 主题活动列表VO
 * @author Administrator
 *
 */
public class TopicListVo {
	private List<TopicActivityEntity> topicList;

	public List<TopicActivityEntity> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<TopicActivityEntity> topicList) {
		this.topicList = topicList;
	}
	
}
