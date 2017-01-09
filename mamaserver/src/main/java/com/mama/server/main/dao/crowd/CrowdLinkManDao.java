package com.mama.server.main.dao.crowd;

import com.mama.server.main.dao.model.crowd.CrowdLinkManPo;
import com.mama.server.util.dao.mybatis.MyBatisDao;

/** 众筹联系人表对应dao类 */
@MyBatisDao
public interface CrowdLinkManDao{

	/** 新增众筹联系人表 */
	int insertCrowdLinkMan(CrowdLinkManPo crowdLinkManPo);
	
	/** 根据登录人账号id查询用户信息是否存在 */
	public CrowdLinkManPo queryCrowdLinkManBuUid(String uid);
}	