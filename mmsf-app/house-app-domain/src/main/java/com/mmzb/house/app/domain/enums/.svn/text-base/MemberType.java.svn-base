package com.mmzb.house.app.domain.enums;

/**
 * <p>会员类型枚举</p>
 * @author netfinworks
 * @version $Id: MemberTypeEnum.java 33158 2013-01-08 10:31:39Z fangjilue $
 */
public enum MemberType {
    PERSONAL("1", "个人", 101L), ENTERPRISE("2", "企业", 201L);

    /** 代码 */
    private final String code;
    /** 信息 */
    private final String message;
    /** 基本户类型 */
    private final Long   baseAccount;

    MemberType(String code, String message, Long baseAccount) {
        this.code = code;
        this.message = message;
        this.baseAccount = baseAccount;
    }

    /**
     * 通过代码获取枚举项
     * @param code
     * @return
     */
    public static MemberType getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (MemberType memberType : MemberType.values()) {
            if (memberType.getCode().equals(code)) {
                return memberType;
            }
        }

        return null;
    }
    
    public static MemberType getByCode(Long code) {
        if (code == null) {
            return null;
        }
        return getByCode(code.toString());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Long getBaseAccount() {
        return baseAccount;
    }
}
