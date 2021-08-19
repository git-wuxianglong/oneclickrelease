package com.wupao.oneclickrelease.enums;

/**
 * 用户类型
 *
 * @author wuxianglong
 */
public enum UserTypeEnum {

    /**
     * 系统管理员admin
     */
    SYSTEM_ADMIN(0),

    /**
     * 系统的普通用户
     */
    SYSTEM_USER(1);

    private int typeCode;

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
