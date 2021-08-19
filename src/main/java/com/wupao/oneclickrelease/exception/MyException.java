package com.wupao.oneclickrelease.exception;


import com.wupao.oneclickrelease.enums.ResponseStatusEnum;

/**
 * 统一封装处理异常
 *
 * @author wuxianglong
 */
public class MyException {

    public static void display(ResponseStatusEnum responseStatusEnum) {
        throw new MyCustomException(responseStatusEnum);
    }

}
