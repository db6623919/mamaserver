package com.mmzb.house.app.domain.member;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mmzb.house.app.common.util.StarUtil;
import com.mmzb.house.app.domain.enums.AccessChannel;
import com.mmzb.house.app.domain.enums.AccountType;
import com.mmzb.house.app.domain.enums.AuthResultStatus;
import com.mmzb.house.app.domain.enums.MemberLockStatus;
import com.mmzb.house.app.domain.enums.MemberPaypasswdStatus;
import com.mmzb.house.app.domain.enums.MemberStatus;
import com.mmzb.house.app.domain.enums.MemberType;


/**
 *
 * <p>
 * 会员基本信息
 * </p>
 *
 * @author qinde
 * @version $Id: BaseMember.java, v 0.1 2013-12-5 下午8:04:40 qinde Exp $
 */
public class BaseMember implements Serializable {
	/**
     *
     */
	private static final long serialVersionUID = 9203206221294683039L;
	/** 钱包会员id */
	private String memberId;
	/** 钱包会员名称 */
	private String memberName;
	/** 平台的userID */
	private String plateFormId;
	/** 平台的loginName */
	private String plateName;
	/** 手机号 */
	private String mobile;
	/** 手机号加星 */
	private String mobileStar;
	/** 手机号加密 */
	private String mobileTicket;
	/** 电子邮件 */
	private String email;
	/** 账户余额 */
	private List<MemberAccount> accounts;
	
    /**账户余额*/
    private MemberAccount         account;
    /**当前操作员Id*/
    private String                currentOperatorId;
	/** 会员卡数量 */
	private int cardCount;
	/** 最后登录时间 */
	private String lastLoginTime;
	/** 会员状态 */
	private MemberStatus status;
	/** 会员锁定状态 */
	private MemberLockStatus lockStatus;
	/** 会员交易密码状态 */
	private MemberPaypasswdStatus paypasswdstatus;
	/** 默认的操作员Id */
	private String operatorId;
	/** 缺省账户(基本户)Id */
	private String defaultAccountId;
	/** 保证金账户Id */
	private String depositAccountId;
	/** 贷款专用账户Id */
	private String creditAccountId;
	/** 理财专用账户Id */
    private String financeAccountId;
	/** 交易密码 */
	private String paypasswd;
	/** 会员类型 */
	private MemberType memberType;
	/** 头像路径 */
	private String faceImageUrl;
	/** 退出地址 */
	private String logoutUrl;
	/** 注册地址 */
	private String registUrl;
	/** 卡数量 */
	private int bankCardCount;
	/** loginName */
	private String loginName;
	/** login pwd */
	private String loginPasswd;
	private String emailStar;
	/** 实名认证信息 */
	private AuthResultStatus nameVerifyStatus;
	/** 访问端 web、wap */
	private AccessChannel accessChannel;
	/** 商户名称 */
	private String merchantName;
	
	private String memberIdentity;
	/**朋友码(自己)**/
	private String friendNum;
	/** 推荐人的朋友码**/
	private String recommendFriendNum;
	/** 认证错误信息 **/
	private String memo;
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setRecommendFriendNum(String recommendFriendNum) {
		this.recommendFriendNum = recommendFriendNum;
	}
	
	public String getRecommendFriendNum() {
		return recommendFriendNum;
	}
	
	public void setFriendNum(String friendNum) {
		this.friendNum = friendNum;
	}
	
	public String getFriendNum() {
		return friendNum;
	}

	public void setMemberIdentity(String memberIdentity) {
		this.memberIdentity = memberIdentity;
	}
	
	public String getMemberIdentity() {
		return memberIdentity;
	}
	
    public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPlateFormId() {
		return plateFormId;
	}

	public void setPlateFormId(String plateFormId) {
		this.plateFormId = plateFormId;
	}

	public String getPlateName() {
		return plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
		this.mobileStar = StarUtil.mockMobile(mobile);
	}

	public String getMobileStar() {
		return mobileStar;
	}

	public void setMobileStar(String mobileStar) {
		this.mobileStar = mobileStar;
	}

	public String getMobileTicket() {
		return mobileTicket;
	}

	public void setMobileTicket(String mobileTicket) {
		this.mobileTicket = mobileTicket;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCurrentOperatorId(String currentOperatorId) {
		this.currentOperatorId = currentOperatorId;
	}
	
	public String getCurrentOperatorId() {
		return currentOperatorId;
	}
	
	public List<MemberAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<MemberAccount> accounts) {
		this.accounts = accounts;
	}

	public int getCardCount() {
		return cardCount;
	}

	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getDefaultAccountId() {
		return defaultAccountId;
	}

	public void setDefaultAccountId(String defaultAccountId) {
		this.defaultAccountId = defaultAccountId;
	}

	public MemberLockStatus getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(MemberLockStatus lockStatus) {
		this.lockStatus = lockStatus;
	}

	public MemberPaypasswdStatus getPaypasswdstatus() {
		return paypasswdstatus;
	}

	public void setPaypasswdstatus(MemberPaypasswdStatus paypasswdstatus) {
		this.paypasswdstatus = paypasswdstatus;
	}

	public String getPaypasswd() {
		return paypasswd;
	}

	public void setPaypasswd(String paypasswd) {
		this.paypasswd = paypasswd;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public String getFaceImageUrl() {
		return faceImageUrl;
	}

	public void setFaceImageUrl(String faceImageUrl) {
		this.faceImageUrl = faceImageUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getRegistUrl() {
		return registUrl;
	}

	public void setRegistUrl(String registUrl) {
		this.registUrl = registUrl;
	}

	public int getBankCardCount() {
		return bankCardCount;
	}

	public void setBankCardCount(int bankCardCount) {
		this.bankCardCount = bankCardCount;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPasswd() {
		return loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

	public String getEmailStar() {
		return emailStar;
	}

	public void setEmailStar(String emailStar) {
		this.emailStar = emailStar;
	}

	public AuthResultStatus getNameVerifyStatus() {
		return nameVerifyStatus;
	}

	public void setNameVerifyStatus(AuthResultStatus nameVerifyStatus) {
		this.nameVerifyStatus = nameVerifyStatus;
	}

	public String getDepositAccountId() {
		return depositAccountId;
	}

	public void setDepositAccountId(String depositAccountId) {
		this.depositAccountId = depositAccountId;
	}

	
	public String getCreditAccountId() {
		return creditAccountId;
	}

	public void setCreditAccountId(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取基本户
	 *
	 * @return
	 */
	public MemberAccount getBaseAccount() {
		return getAccount(AccountType.PERSONAL_BASE.getCode());
	}

	/**
	 * 获取保证金户
	 *
	 * @return
	 */
	public MemberAccount getDepositAccount() {
		return getAccount(AccountType.LOAN_ACCOUNT.getCode());
	}

	/**
     * 获取贷款专用户
     *
     * @return
     */
    public MemberAccount getLoanAccount() {
        return getAccount(AccountType.LOAN_ACCOUNT.getCode());
    }
    /**
     * 获取理财专用户
     *
     * @return
     */
    public MemberAccount getFinanceAccount() {
        return getAccount(AccountType.FINANCE_ACCOUNT.getCode());
    }
	
	public String getFinanceAccountId() {
        return financeAccountId;
    }

    public void setFinanceAccountId(String financeAccountId) {
        this.financeAccountId = financeAccountId;
    }

    public MemberAccount getAccount(Long accountType) {
		if (CollectionUtils.isNotEmpty(accounts)) {
			for (MemberAccount ma : accounts) {
				if (ma.getAccountType() == accountType) {
					return ma;
				}
			}
		}
		return null;
	}

    public MemberAccount getAccount() {
        return account;
    }

    public void setAccount(MemberAccount account) {
        this.account = account;
    }
    
	public AccessChannel getAccessChannel() {
		return accessChannel;
	}

	public void setAccessChannel(AccessChannel accessChannel) {
		this.accessChannel = accessChannel;
	}
	

	public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
	public String toString() {
		return "BaseMember [memberId=" + memberId + ", memberName="
				+ memberName + ", plateFormId=" + plateFormId + ", plateName="
				+ plateName + ", mobile=" + mobile + ", mobileStar="
				+ mobileStar + ", mobileTicket=" + mobileTicket + ", email="
				+ email + ", accounts=" + accounts + ", cardCount=" + cardCount
				+ ", lastLoginTime=" + lastLoginTime + ", status=" + status
				+ ", lockStatus=" + lockStatus + ", paypasswdstatus="
				+ paypasswdstatus + ", operatorId=" + operatorId
				+ ", defaultAccountId=" + defaultAccountId
				+ ", depositAccountId=" + depositAccountId + ", memberType="
				+ memberType + ", faceImageUrl=" + faceImageUrl
				+ ", logoutUrl=" + logoutUrl + ", registUrl=" + registUrl
				+ ", bankCardCount=" + bankCardCount + ", loginName="
				+ loginName + ", emailStar=" + emailStar
				+ ", nameVerifyStatus=" + nameVerifyStatus + ", accessChannel="
				+ accessChannel + "]";
	}

}
