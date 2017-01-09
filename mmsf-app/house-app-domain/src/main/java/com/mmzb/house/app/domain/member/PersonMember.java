package com.mmzb.house.app.domain.member;

import java.io.Serializable;

import com.mmzb.house.app.domain.EncryptData;
import com.mmzb.house.app.domain.enums.AuthResultStatus;

/**
 * <p>会员综合信息</p>
 * @author qinde
 * @version $Id: MemberAllInfo.java, v 0.1 2013-11-19 下午3:41:32 qinde Exp $
 */
public class PersonMember extends BaseMember implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2393418922180711911L;
    /**真实姓名*/
    private EncryptData       realName;

    /**实名认证信息*/
    private AuthResultStatus  nameVerifyStatus;
    
    private boolean isMerchant;

    /** 会员平台类型 */
    private String            platformType;

    /** 会员标识*/
    private String            memberIdentity;

    private Integer 		  memberAccountFlag;

    /** 真实姓名 */
    private String realName2;
    
    public AuthResultStatus getNameVerifyStatus() {
        return nameVerifyStatus;
    }

    public void setNameVerifyStatus(AuthResultStatus nameVerifyStatus) {
        this.nameVerifyStatus = nameVerifyStatus;
    }

    public EncryptData getRealName() {
        return realName;
    }

    public void setRealName(EncryptData realName) {
        this.realName = realName;
    }

    
    public boolean isMerchant() {
        return isMerchant;
    }
    
    public boolean getIsMerchant() {
    	return isMerchant;
    }

    public void setMerchant(boolean isMerchant) {
        this.isMerchant = isMerchant;
    }

    public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getMemberIdentity() {
		return memberIdentity;
	}

	public void setMemberIdentity(String memberIdentity) {
		this.memberIdentity = memberIdentity;
	}

	public Integer getMemberAccountFlag() {
		return memberAccountFlag;
	}

	public void setMemberAccountFlag(Integer memberAccountFlag) {
		this.memberAccountFlag = memberAccountFlag;
	}

	public String getRealName2() {
		return realName2;
	}

	public void setRealName2(String realName2) {
		this.realName2 = realName2;
	}

	@Override
    public String toString() {
        return "PersonMember [nameVerifyStatus=" + nameVerifyStatus + ", getMemberId()="
               + getMemberId() + ", getMemberName()=" + getMemberName() + ", getPlateFormId()="
               + getPlateFormId() + ", getPlateName()=" + getPlateName() + ", getMobileStar()="
               + getMobileStar() + ", getMobileTicket()=" + getMobileTicket() + ", getEmail()="
               + getEmail() + ", getAccounts()=" + getAccounts() + ", getCardCount()="
               + getCardCount() + ", getLastLoginTime()=" + getLastLoginTime() + ", getStatus()="
               + getStatus() + ", getOperatorId()=" + getOperatorId() + ", getDefaultAccountId()="
               + getDefaultAccountId() + ", getLockStatus()=" + getLockStatus()
               + ", getPaypasswdstatus()=" + getPaypasswdstatus() + ", getMemberType()="
               + getMemberType() + ", getFaceImageUrl()=" + getFaceImageUrl() + ", getLogoutUrl()="
               + getLogoutUrl() + ", getRegistUrl()=" + getRegistUrl() + ", toString()="
               + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
               + "]";
    }

}
