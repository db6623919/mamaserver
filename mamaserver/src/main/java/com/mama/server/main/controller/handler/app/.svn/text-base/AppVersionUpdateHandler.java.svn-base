package com.mama.server.main.controller.handler.app;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mama.server.main.controller.handler.BaseHandler;
import com.mama.server.main.dao.model.AppVersionPo;

/**
 * app版本更新检查
 * @author whl
 *
 */
@Component
public class AppVersionUpdateHandler extends BaseHandler {

	private static final Logger logger = LoggerFactory.getLogger(AppVersionUpdateHandler.class);
	
	@Override
	public HashMap<String, Object> handle(HashMap<String, Object> param) {
		logger.info("start to run AppVersionUpdateHandler.");
		try {
			String version_no = (String)param.get("version_no");//版本号
			String source = (String)param.get("source");//来源
			String product_type = (String)param.get("product_type");//类型
			
			String versionBool = "0"; //0:不更新 1：更新
			AppVersionPo appVersion = new AppVersionPo();
			appVersion.setSource(source);
			appVersion.setProduct_type(product_type);
			appVersion.setVersionStatus(1); //1:生效版本 0：失效
			List<AppVersionPo> appVersionList = mainService.getAppVersion(appVersion);
			if (appVersionList.size() > 0) {
				//false:更新 true:不更新
				if (versionVerifyAll(appVersionList.get(0).getVersion_no(),version_no) == false) {
					versionBool = "1";
					dataMap.put("appVersion", appVersionList.get(0));
				} 
			}
			dataMap.put("versionBool", versionBool);
			logger.info("end to run AppVersionUpdateHandler.");
			genSuccOutputMap();
		} catch (Exception e) {
			logger.error("AppVersionUpdateHandler is error.",e);
			genErrOutputMap("AppVersionUpdateHandler is error.");
		}
		return outputMap;
	}

	 /**
	   * 验证主版本号和子版本号，将对应的版本号转换为整数然后比较大小,大于等于的时候返回true
	   * @param base 后台服务器版本
	   * @param version
	   * @return
	   */
	  public static boolean versionVerifyAll(String base , String version){
	    
	    try{
	         String[] baseArray = base.split("\\.");
	         String[] vArray    = version.split("\\.");
	             int size = baseArray.length>vArray.length?vArray.length:baseArray.length;
	                                       
	             boolean isSameVersion = false;
	             int index = 0;
	             for( ; index < size ; index ++){
	               if(Integer.parseInt( vArray[index] ) > Integer.parseInt( baseArray[index] )){
	                 isSameVersion = true;
	                 break;
	              }else if(Integer.parseInt( vArray[index] ) < Integer.parseInt( baseArray[index] )){
	                isSameVersion = false;
	                break;
	              }else if(Integer.parseInt( vArray[index] ) ==Integer.parseInt( baseArray[index] )){
	                if(index==size-1){
	                  isSameVersion = true;
	                     break; 
	                }
	                continue;
	              }
	             }             
	             return isSameVersion;
	    }catch(Exception e){
	      e.printStackTrace();
	      logger.error("对照版本号出错", e);
	      return false;
	    }    
	    
	  }
}
