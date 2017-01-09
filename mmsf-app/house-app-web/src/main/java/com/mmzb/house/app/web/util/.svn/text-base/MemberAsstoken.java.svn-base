package com.mmzb.house.app.web.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mama.jedis.service.facade.response.ListResponse;
import com.mmzb.house.app.domain.member.PersonMember;

public class MemberAsstoken {
	
	public static  Map<String, PersonMember> memberAsstokenMap=new ConcurrentHashMap<String, PersonMember>();
	
	public static void putMember(RedisFacade redisFacade , String token , PersonMember member , Logger log){
		
		try{
		
		log.info("=================开始调用redis数据库服务===token==="+token+"=====PersonMember=="+member);
        
        //会员编号
        if(!StringUtils.isEmpty(member.getMemberId())){
        	RedisRequest request = new RedisRequest();
        	request.setKey("member:"+token);
        	request.setFiled("memberID");
        	request.setValue(member.getMemberId());
        	
        	Long response = (Long)redisFacade.putKeyAndFieldAndValue(request);
        	log.info("======【会员编号】==========插入数据结果response====="+response);
        }
        
        //会员姓名
        if(!StringUtils.isEmpty(member.getRealName2())){
        	RedisRequest request = new RedisRequest();
        	request.setKey("member:"+token);
        	request.setFiled("realName");
        	request.setValue(member.getRealName2());
        	
        	Long response = (Long)redisFacade.putKeyAndFieldAndValue(request);
        	log.info("=======【真实姓名】=========插入数据结果response====="+response);
        }
        
        if(!StringUtils.isEmpty(member.getMemberType().getCode())){
        	RedisRequest request = new RedisRequest();
        	request.setKey("member:"+token);
        	request.setFiled("memberType");
        	request.setValue(member.getMemberType().getCode());
        	
        	Long response = (Long)redisFacade.putKeyAndFieldAndValue(request);
        	log.info("=======【会员类型】=========插入数据结果response====="+response);
        }
        
        log.info("======================数据操作完毕=================");
        
		}catch(Exception e){
			log.error("=======redis数据库服务调用异常=======", e);
		}
		
	}
	
	public static String getMemberByKey(RedisFacade redisFacade , String token , Logger log){
		
		try{
		
		log.info("=================redis开始取数据===token==="+token);
		RedisRequest request = new RedisRequest();
		request.setKey("member:"+token);
		request.setFiled("memberID");
		
		String responseValue = (String)redisFacade.getValueByKeyAndField(request);
        
        log.info("=============redis取数据=====token==="+token+"==========响应为：=="+responseValue);
        
        return responseValue;
        
		}catch(Exception e){
			log.error("=======redis获取数据出现异常=======", e);
		}
		
		return null;
	}

	public static String getMemberTypeByKey(RedisFacade redisFacade , String token , Logger log){
		
		try{
		
		log.info("=================redis数据库服务开始取数据===token==="+token);
		
		RedisRequest request = new RedisRequest();
		request.setKey("member:"+token);
		request.setFiled("memberType");
		
		String responseValue = (String)redisFacade.getValueByKeyAndField(request);
        
        log.info("=============redis数据库服务取数据=====token==="+token+"==========响应为：=="+responseValue);
        
        return responseValue;
        
		}catch(Exception e){
			e.printStackTrace();
			log.error("=======redis获取数据出现异常=======", e);
			return null;
		}
		
	}
	
	/**
	 * chenmeiyang
	 * token的格式为   "member:"+token
	 */
	public static Long removeKey(RedisFacade redisFacade , String key , Logger log){
		
		try{
		
		log.info("=================redis开始删除数据===key==="+key);

        RedisRequest request = new RedisRequest();
        request.setKey(key);
        
        ListResponse response = redisFacade.removeByKey(request);
        
        log.info("======删除key结果========="+response);
        
		}catch(Exception e){
			log.error("=======redis删除数据出现异常=======", e);
		}
		
		return null;
	}
}
