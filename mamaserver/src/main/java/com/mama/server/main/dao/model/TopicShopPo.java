package com.mama.server.main.dao.model;

/**
 * 活动客栈关联表
 * @author whl
 *
 */
public class TopicShopPo {
	private int id;
	/** 活动ID*/
	private int topicID;
	/** 客栈ID*/
	private int shopId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	
}
