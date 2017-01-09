package com.mama.server.main.dao;

import java.util.List;

import com.mama.server.main.dao.model.CustomerUserPo;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface CustomerDao extends GenericDao<CustomerUserPo> {
	List<CustomerUserPo> getUserInfoByAllParam(CustomerUserPo cup);
}
