package com.wupao.oneclickrelease.entity;

import com.wupao.oneclickrelease.enums.ResponseStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 响应结果类
 * <br>创建时间：2021/4/6
 *
 * @author 吴翔龙
 */
@Data
@NoArgsConstructor
public class ResponseResult {
    /**
     * 响应状态码
     */
    private Integer status;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 成功返回，带有数据的
     *
     * @param data 响应数据
     * @return Success ResponseResult
     */
    public static ResponseResult ok(Object data) {
        return new ResponseResult(data);
    }

    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入
     *
     * @return Success ResponseResult
     */
    public static ResponseResult ok() {
        return new ResponseResult(ResponseStatusEnum.SUCCESS);
    }

    /**
     * 错误返回，直接调用error方法即可
     * 在ResponseStatusEnum中自定义错误后再返回也可以
     *
     * @return Error ResponseResult
     */
    public static ResponseResult error() {
        return new ResponseResult(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息
     * 可以用于表单验证，把错误统一的全部返回出去
     *
     * @param map 多条错误信息
     * @return Error ResponseResult
     */
    public static ResponseResult errorMap(Map map) {
        return new ResponseResult(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     *
     * @param msg 错误提示信息
     * @return Error ResponseResult
     */
    public static ResponseResult errorMsg(String msg) {
        return new ResponseResult(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，
     * 可以到 {@link ResponseStatusEnum} 中自定义后再传入
     *
     * @param responseStatus 错误枚举
     * @return Error ResponseResult
     */
    public static ResponseResult errorCustom(ResponseStatusEnum responseStatus) {
        return new ResponseResult(responseStatus);
    }

    /**
     * 将异常信息返回，需要传入一个自定义的枚举，
     * 可以到 {@link ResponseStatusEnum} 中自定义后再传入
     *
     * @param responseStatus 错误枚举
     * @return Exception ResponseResult
     */
    public static ResponseResult exception(ResponseStatusEnum responseStatus) {
        return new ResponseResult(responseStatus);
    }

    /**
     * 成功返回
     *
     * @param data 响应数据
     */
    public ResponseResult(Object data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.data = data;
    }

    public ResponseResult(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
    }

    public ResponseResult(ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.data = data;
    }

    public ResponseResult(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
    }

}
