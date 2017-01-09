package com.console.service;

import org.apache.log4j.Logger;


public class BaseService{
	//@Resource
	//protected IBaseDao baseDao;

	protected Logger logger = Logger.getLogger(BaseService.class);
	/**
	 * 保存日志
	 * @param operUser
	 * @param operType
	 * @param description
	 * @param systemType
	 */
	public void saveRecordOperLog(String operUser,String operType,String description,String systemType){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存日志失败，失败原因:"+e.getMessage());
		}
	}

}
