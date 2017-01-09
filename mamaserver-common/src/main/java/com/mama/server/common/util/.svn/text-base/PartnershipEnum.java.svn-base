package com.mama.server.common.util;

/**
 * 合作关系枚举
 * @author dengbiao
 *
 */
public enum PartnershipEnum {

	MemberInn("0","会员客栈"),//会员客栈
	Holding("1","控股客栈"),//控股客栈
	DepthCooperation("2","深度合作"),//深度合作
	GuesthouseInn("3","民宿贷客栈"),//民宿贷客栈
	JointBusinessInn("4","联合运营客栈");//联合运营客栈 
	
	   /** 代码 */
    private final String code;
    /** 信息 */
    private final String message;

    PartnershipEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过代码获取枚举项
     * @param code
     * @return
     */
    public static PartnershipEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (PartnershipEnum item : PartnershipEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}