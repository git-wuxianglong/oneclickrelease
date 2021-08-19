package com.wupao.oneclickrelease.exception;

import com.wupao.oneclickrelease.entity.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一异常拦截处理，
 * 可以针对异常的类型进行捕获，然后返回json信息到前端
 *
 * @author wuxianglong
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 以json格式返回异常信息
     *
     * @param e {@link MyCustomException}
     * @return {@link ResponseResult} exception
     */
    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public ResponseResult returnMyException(MyCustomException e) {
        e.printStackTrace();
        return ResponseResult.exception(e.getResponseStatusEnum());
    }

    /**
     * 提交参数统一校验，只需要在接口上加@Valid注解即可
     * 不符合规定的参数会直接返回对应的错误信息
     * 适用于不分组的情况下，分组校验使用ValidationUtils工具类
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return 提交参数不正确，所有的错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult returnException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> maps = getErrors(result);
        return ResponseResult.errorMap(maps);
    }

    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>(16);
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 所对应的某个参数
            String field = error.getField();
            // 验证的错误消息
            String msg = error.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }

}
