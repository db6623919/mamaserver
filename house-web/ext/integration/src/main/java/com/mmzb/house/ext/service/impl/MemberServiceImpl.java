package com.mmzb.house.ext.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mmzb.house.ext.common.ResponseCodeEnum;
import com.mmzb.house.ext.common.ValueConsts;
import com.mmzb.house.ext.request.MemberRequest;
import com.mmzb.house.ext.response.MemberResponse;
import com.mmzb.house.ext.service.MemberService;
import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.ma.service.base.enums.MemberLoginChannelEnum;
import com.netfinworks.ma.service.facade.ILoginPwdFacade;
import com.netfinworks.ma.service.facade.IMemberFacade;
import com.netfinworks.ma.service.facade.ITokenFacade;
import com.netfinworks.ma.service.request.PersonalLoginPwdRequest;
import com.netfinworks.ma.service.request.PersonalMemberQueryRequest;
import com.netfinworks.ma.service.request.TokenReqeust;
import com.netfinworks.ma.service.response.LoginPwdResponse;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.ma.service.response.PersonalMemberResponse;
import com.netfinworks.ma.service.response.TokenResponse;
import com.netfinworks.ues.util.UesUtils;

/**
 * 
 * @ClassName: MemberServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lijiaqi
 * @date 2015年12月14日 下午5:06:00
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Resource(name = "loginPwdFacade")
	private ILoginPwdFacade loginPwdFacade;

	@Resource(name = "memberFacade")
	private IMemberFacade memberFacade;

	@Resource(name = "tokenFacade")
	private ITokenFacade tokenFacade;

	@Override
	public MemberResponse checkLoginPwd(MemberRequest memberRequest) {
		OperationEnvironment environment = new OperationEnvironment();
		MemberResponse memberResponse = new MemberResponse();
		PersonalLoginPwdRequest request = new PersonalLoginPwdRequest();
		try {
			this.validateRequestRequest(memberRequest);
			request.setMemberIdentity(memberRequest.getPhone());
			request.setPlatformType(memberRequest.getPlatformType());
			request.setPassword(UesUtils.hashSignContent(UesUtils.hashSignContent(memberRequest.getPassword()) + ValueConsts.UES_KEY));
			request.setSalt(ValueConsts.UES_KEY);
			request.setChannel(MemberLoginChannelEnum.MM_SFANG.getCode());
			LoginPwdResponse res = loginPwdFacade.checkPersonalLoginPwd(environment, request);
			logger.info("会员系统返回:" + res);
			if (res.getResponseCode().equals(ResponseCodeEnum.SUCCESS.getCode())) {
				memberResponse.setSuccess(true);
				memberResponse.setMsg("验证成功");
				memberResponse.setMemberId(res.getMemberId());
				memberResponse.setMemberName(getMemberName(res.getMemberId(), environment));
				memberResponse.setToken(res.getToken());
			} else {
				memberResponse.setSuccess(false);
				memberResponse.setMsg(res.getResponseMessage());
			}
		} catch (Exception e) {
			logger.error("请求会员系统异常" + e);
			memberResponse.setSuccess(false);
			memberResponse.setMsg(e.getMessage());
		}
		return memberResponse;
	}

	@Override
	public PersonalMemberInfo checkToken(String token) {
		OperationEnvironment environment = new OperationEnvironment();
		TokenReqeust request = new TokenReqeust();
		request.setToken(token);
		TokenResponse tokenResponse = tokenFacade.checkToken(environment, request);
		if (tokenResponse != null && tokenResponse.getMap() != null) {
			String memberId = (String) tokenResponse.getMap().get("memberId");
			if (memberId != null && StringUtils.isNotBlank(memberId)) {
				return getMemberInfo(memberId, environment);
			}
		}
		return null;
	}

	@Override
	public void removeToken(String token) {
		OperationEnvironment environment = new OperationEnvironment();
		TokenReqeust request = new TokenReqeust();
		request.setToken(token);
		tokenFacade.removeToken(environment, request);

	}

	private String getMemberName(String memberId, OperationEnvironment environment) {
		PersonalMemberInfo memberInfo = getMemberInfo(memberId, environment);
		if (memberInfo != null)
			return memberInfo.getMemberName();
		return null;
	}

	private PersonalMemberInfo getMemberInfo(String memberId, OperationEnvironment environment) {
		PersonalMemberQueryRequest request = new PersonalMemberQueryRequest();
		PersonalMemberResponse pmr = new PersonalMemberResponse();
		try {
			request.setMemberId(memberId);
			pmr = memberFacade.queryPersonalMember(environment, request);
			logger.info("会员系统返回:" + pmr);
			return pmr.getPersonalMemberInfo();
		} catch (Exception e) {
			logger.error("请求会员系统异常" + e);
			return null;
		}
	}

	private void validateRequestRequest(MemberRequest memberRequest) {
		if (StringUtils.isBlank(memberRequest.getPhone()))
			throw new IllegalArgumentException("手机号不能为空");
		if (StringUtils.isBlank(memberRequest.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		if (StringUtils.isBlank(memberRequest.getPlatformType()))
			throw new IllegalArgumentException("标示不能为空");
	}

	@Override
	public PersonalMemberResponse queryPersonalMember(String memberId) {
		try {
            long beginTime = 0L;
            if (logger.isInfoEnabled()) {
                logger.info("[house_web-->member]查询个人会员信息:请求参数：{}", memberId);
                beginTime = System.currentTimeMillis();
            }
            
            PersonalMemberResponse response = memberFacade.queryPersonalMember(getEnv(), new PersonalMemberQueryRequest(memberId));
            if (logger.isInfoEnabled()) {
                long consumeTime = System.currentTimeMillis() - beginTime;
                logger.info("远程查询个人会员信息， 耗时:{} (ms); 响应结果:{} ",
                    new Object[] { consumeTime, response });
            }
           return response;
        } catch (Exception e) {
            logger.error("[house_web-->member]查询个人会员信息异常", e);
           return null;
        }
	}
	
	private OperationEnvironment getEnv() {
		OperationEnvironment operationenvironment = new OperationEnvironment();
		operationenvironment.setClientId(getClientId());
		
		return operationenvironment;
	}
	
	private String getClientId(){
		return String.format("house_web:%s", UUID.randomUUID().toString());
	}

}