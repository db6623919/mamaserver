package com.console.framework.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.console.entity.TSysResource;
import com.console.entity.TSysRole;
import com.console.framework.dao.MyBatisDao;


/**
 *  加载资源与权限的对应关系(1)
 *	@ClassName:   MyAccessDecisionManager
 *	@Description: TODO
 *	@author YY
 *	@date   2013-9-28 上午11:40:05
 */

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	//public static Logger logger = Logger.getLogger(MySecurityMetadataSource.class);
	
	private MyBatisDao myBatisDao;
	public MyBatisDao getMyBatisDao() {
		return myBatisDao;
	}
	public void setMyBatisDao(MyBatisDao myBatisDao) {
		this.myBatisDao = myBatisDao;
	}

	//由spring调用
	public MySecurityMetadataSource(MyBatisDao myBatisDao) {
		this.myBatisDao = myBatisDao;
		loadResourceDefine();
	}

	private Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	public boolean supports(Class<?> clazz) {
		return true;
	}
	

	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		//logger.info("********************加载所有资源与权限的关系********************");
		if(resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		    //查询所有的Resources
		    List<TSysResource> resources = myBatisDao.getList("resourceMapper.getResource");
		    for (TSysResource resource : resources) {

		    	//查询Resources的Roles
		    	List<TSysRole> roleList = myBatisDao.getList("roleMapper.getRolesByResourceid",resource.getId());
		    	Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		    	for (TSysRole role : roleList) {
		    		ConfigAttribute configAttribute = new SecurityConfig(role.getName());
		    		configAttributes.add(configAttribute);
				}
		    	resourceMap.put(resource.getPrivilege(), configAttributes);
			
			}
		}
		
		//Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();
		//Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();
		
	}
	//返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(null!=requestUrl && !requestUrl.equals("")){
			if(requestUrl.charAt(0)=='/'){
				requestUrl=requestUrl.substring(1);
			}
		}
		if(resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}

}
