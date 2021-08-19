package com.wupao.oneclickrelease.enums;

/**
 * 响应结果枚举
 *
 * @author wuxianglong
 */
public enum ResponseStatusEnum {

    /**
     * 成功或失败
     */
    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),
    ACCOUNT_NOT_EXIST(501, false, "账号不存在"),
    CAN_NOT_DELETE_YOURSELF(502, false, "不能删除自己"),
    CAN_NOT_DISABLE_YOURSELF(503, false, "不能禁用自己"),
    MOBILE_EXIST(504, false, "手机号已存在"),
    ROLE_NOT_EXIST(505, false, "角色不存在"),
    MENU_NOT_EXIST(506, false, "菜单不存在"),
    MENU_NAME_EXIST(507, false, "角色名已存在"),
    LOGIN_FAIL(508, false, "账号或密码错误");

    /**
     * 响应业务状态
     */
    private final Integer status;
    /**
     * 调用是否成功
     */
    private final Boolean success;
    /**
     * 响应消息，可以为成功或者失败的消息
     */
    private final String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }

    public Boolean success() {
        return success;
    }

    public String msg() {
        return msg;
    }

}
