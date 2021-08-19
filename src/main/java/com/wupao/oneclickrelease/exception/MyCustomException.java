package com.wupao.oneclickrelease.exception;


import com.wupao.oneclickrelease.enums.ResponseStatusEnum;

/**
 * 自定义异常，统一处理异常，便于解耦
 * service与controller错误的解耦，不会被service返回的类型而限制
 *
 * @author wuxianglong
 */
public class MyCustomException extends RuntimeException {

    /**
     * 响应结果枚举类
     */
    private ResponseStatusEnum responseStatusEnum;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为：" + responseStatusEnum.status()
                + "；异常信息：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
