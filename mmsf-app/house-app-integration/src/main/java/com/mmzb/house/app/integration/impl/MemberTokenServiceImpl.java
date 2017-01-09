package com.mmzb.house.app.integration.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mama.jedis.service.facade.RedisFacade;
import com.mama.jedis.service.facade.request.RedisRequest;
import com.mmzb.house.app.integration.MemberTokenService;
import com.mmzb.house.app.integration.convert.MemberTokenVOConvert;
import com.mmzb.house.app.vo.UserInfoVo;
import com.netfinworks.authorize.ws.clientservice.IMemberTokenService;
import com.netfinworks.authorize.ws.request.impl.GetMemberTokenByAccessTokenRequest;
import com.netfinworks.authorize.ws.response.impl.GetMemberTokenResponse;
import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.ma.service.facade.IMemberFacade;
import com.netfinworks.ma.service.request.PersonalMemberQueryRequest;
import com.netfinworks.ma.service.response.IdentityInfo;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.ma.service.response.PersonalMemberResponse;

@Service("memberTokenService")
public class MemberTokenServiceImpl implements MemberTokenService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "iMemberTokenService")
	private IMemberTokenService iMemberTokenService;
	
    @Resource(name = "redisFacade")
    private RedisFacade      redisFacade;
	
	@Resource
	private IMemberFacade memberFacade;
	
	@Override
	public GetMemberTokenResponse selectTokenRecord(String tokenId) {
		String opMsg = "查询令牌";
		try {
			long beginTime = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
				logger.info(opMsg + "，请求参数：authVO:{}", tokenId);
			}
			GetMemberTokenByAccessTokenRequest request = MemberTokenVOConvert.selectMemberTokenRequest(tokenId);
			GetMemberTokenResponse response = iMemberTokenService.selectTokenRecord(request);
			long consumeTime = System.currentTimeMillis() - beginTime;
			if (logger.isInfoEnabled()) {
				logger.info(opMsg + "， 耗时:{} (ms); 响应结果:{} ", new Object[] { consumeTime, response });
			}
			if ("0".equals(response.getResponse().getResponseCode())) {
				return response;
			}
			return null;
		} catch (Exception e) {
			logger.error(tokenId + "查询过程中出现异常！", e);
			throw new RuntimeException("SYSTEM_ERROR", e.getCause());
		}
	}

	/** 根据会员id查询手机号码 */
    public String queryMobileByMemberId(String memberId) {
    	String mobile = "";
    	OperationEnvironment environment = new OperationEnvironment();
    	PersonalMemberQueryRequest request = new PersonalMemberQueryRequest(memberId);
    	PersonalMemberResponse response = memberFacade.queryPersonalMember(environment, request);
    	PersonalMemberInfo memberInfo = response.getPersonalMemberInfo();
    	if(memberInfo != null) {
    		List<IdentityInfo> identitys = memberInfo.getIdentitys();
    		if(identitys != null && identitys.size() > 0) {
    			for(IdentityInfo identity : identitys) {
    				if(identity.getIdentityType() == 2) {
    					mobile = identity.getIdentity();
    				}
    			}
    		}
    	}
    	return mobile;
    }

    /**
     * 保存用户信息TO REDIS
     */
	@Override
	public void saveLoginInfoToRedis(String accessToken, UserInfoVo vo) {
		String key = "STR:USER:MMSF:" + accessToken;
		String jsonStr = JSON.toJSONString(vo);
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey(key);
		redisRequest.setValue(jsonStr);
		redisFacade.putKeyAndValue(redisRequest);
	}
	
	/** 根据tokenId获取用户登录信息 */
	public UserInfoVo getLoginInfoFromRedis(String accessToken) {
		UserInfoVo vo = null;
		String key = "STR:USER:MMSF:" + accessToken;
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey(key);
		String userInfo = redisFacade.getValueByKey(redisRequest);
		
		if(userInfo != null && userInfo.trim().length() > 0) {
			vo = JSON.parseObject(userInfo, UserInfoVo.class);
		}
		return vo;
	}
	
	/** 将用户信息从redis中删除 */
	public  void delLoginInfoFromRedis(String accessToken) {
		String key = "STR:USER:MMSF:" + accessToken;
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey(key);
		redisFacade.removeByKey(redisRequest);
	}
	
	/** 根据tokenId判断是否登录 */
	public boolean isLoginByTokenId(String tokenId) {
		String key = "STR:USER:MMSF:" + tokenId;
		RedisRequest redisRequest = new RedisRequest();
		redisRequest.setKey(key);
		String isExist = redisFacade.getValueByKey(redisRequest);
		if(null!=isExist&&!"".equals(isExist)) {
			return true;
		}
		return false;
	}
	
}