package com.wupao.oneclickrelease.enums;

/**
 * 角色状态
 *
 * @author wuxianglong
 */
public enum RoleStatusEnum {
    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 启用
     */
    AVAILABLE(1);

    private int statusCode;

    RoleStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
