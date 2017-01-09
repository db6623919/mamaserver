package com.mmzb.house.ext.service;

import com.mmzb.house.ext.request.MemberRequest;
import com.mmzb.house.ext.response.MemberResponse;
import com.netfinworks.ma.service.response.PersonalMemberInfo;
import com.netfinworks.ma.service.response.PersonalMemberResponse;

/**
 * 
 * @ClassName: MemberService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lijiaqi
 * @date 2015年12月14日 下午3:53:20
 */
public interface MemberService {

	/**
	 * 
	 * @Title: checkLoginPwd @Description: @param memberResponse @return @return
	 * MemberResponse 返回类型 @throws
	 */
	MemberResponse checkLoginPwd(MemberRequest memberRequest);

	PersonalMemberInfo checkToken(String token);
	
	void removeToken(String token);
	
	/**
	 * 查询个人会员信息
	 * @param memberId
	 * @return
	 */
	PersonalMemberResponse queryPersonalMember(String memberId);
	
}