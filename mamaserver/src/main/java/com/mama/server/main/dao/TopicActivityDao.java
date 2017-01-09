package com.mama.server.main.dao;

import java.util.List;

import com.mama.server.main.dao.model.TopicActivityPo;
import com.mama.server.main.dao.model.TopicShopPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/**
 * 主题活动
 * @author Whl
 *
 * @version 20161107
 */

@MyBatisDao
public interface TopicActivityDao{

	int insertTopicActivity(TopicActivityPo topicActivity);
	
	List<TopicActivityPo> getTopicActivity(TopicActivityPo topicActivity);
	
	void updateTopicActivity(TopicActivityPo topicActivity);
	
	void removeTopicActivity(TopicActivityPo topicActivity);
	
	List<TopicShopPo> getTopicShopList(TopicShopPo topicShop);
	
	void removeTopicShop(TopicShopPo topicShop);
	
	int insertTopicShop(TopicShopPo topicShop);
}
