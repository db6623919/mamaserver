package com.mama.server.main.dao;

import java.util.List;
import java.util.Map;

import com.mama.server.main.dao.model.ActivityEnrollPo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface ActivityEnrollDao extends GenericDao<ActivityEnrollPo> {

	List<ActivityEnrollPo> getActivityEnrollByPar(Map map);
	int updateActivityEnrollPo(ActivityEnrollPo activityEnrollPo);
	
	int insertActivityEnroll(ActivityEnrollPo activityEnroll);
	
}