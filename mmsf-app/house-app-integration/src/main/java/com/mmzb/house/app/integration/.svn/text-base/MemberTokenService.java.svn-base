package com.mmzb.house.app.integration;

import com.mmzb.house.app.vo.UserInfoVo;
import com.netfinworks.authorize.ws.response.impl.GetMemberTokenResponse;

/** 会员令牌管理 */
public interface MemberTokenService {
	/**
     * 根据令牌获取用户令牌日志信息
     * @param tokenId
     * @return
     */
    public GetMemberTokenResponse selectTokenRecord(String tokenId);
    
    /** 根据会员id查询手机号码 */
    public String queryMobileByMemberId(String memberId);
    
	/** 将用户信息保存至redis中 */
	public  void saveLoginInfoToRedis(String accessToken, UserInfoVo vo);
	
	/**  获取用户信息*/
	public UserInfoVo getLoginInfoFromRedis(String accessToken);
	
	/** 将用户信息从redis中删除 */
	public  void delLoginInfoFromRedis(String accessToken);
	
	/** 根据tokenId判断是否登录 */
	public boolean isLoginByTokenId(String tokenId);
}
