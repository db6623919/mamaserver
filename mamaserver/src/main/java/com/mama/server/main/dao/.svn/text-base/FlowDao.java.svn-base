package com.mama.server.main.dao;

import java.util.List;

import com.mama.server.main.dao.model.FlowPo;
import com.mama.server.main.dao.model.HousePo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface FlowDao extends GenericDao<FlowPo> {
    int save(FlowPo fp);
	List<FlowPo> getFlowByAllParam(FlowPo fp);
}
