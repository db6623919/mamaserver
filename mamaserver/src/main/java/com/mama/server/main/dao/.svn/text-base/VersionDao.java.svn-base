package com.mama.server.main.dao;


import com.mama.server.main.dao.model.Version;
import com.mama.server.util.dao.GenericDao;
import com.mama.server.util.dao.mybatis.MyBatisDao;

@MyBatisDao
public interface VersionDao extends GenericDao<Version> {

	int insertVersion(Version version);
	Version getVersionByPar(int versionType);
	int updateVersion(Version version);
	
}