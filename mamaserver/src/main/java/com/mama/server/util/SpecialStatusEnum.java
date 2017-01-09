package com.mama.server.util;

/**
 * 特价状态枚举
 * @author dengbiao
 *
 */
public enum SpecialStatusEnum {

	Ordinary("ordinary",0),//普通
	Special("Special",1),//特价
	Home_Special("Home_Special",2);//首页特价
    

    /** 代码 */
    private final String code;
    /** 信息 */
    private final int message;

    SpecialStatusEnum(String code, int message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过代码获取枚举项
     * @param code
     * @return
     */
    public static SpecialStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (SpecialStatusEnum item : SpecialStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public int getMessage() {
        return message;
    }
    
}