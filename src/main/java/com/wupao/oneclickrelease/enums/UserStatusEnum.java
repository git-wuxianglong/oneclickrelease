package com.wupao.oneclickrelease.enums;

/**
 * 用户状态
 *
 * @author wuxianglong
 */
public enum UserStatusEnum {
    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 可用
     */
    AVAILABLE(1);

    private int statusCode;

    UserStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
