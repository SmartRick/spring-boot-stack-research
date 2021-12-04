package cn.smartrick.common.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Date: 2021/12/2 15:51
 * @Author: SmartRick
 * @Description: 全局异常处理器，SpringMvc组件用于补货指定异常并在处理后相应
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    /**
     * @ResponseStatus 设置HTTP相应状态码
     * @ExceptionHandler 捕获指定异常并处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validateHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errMap = new HashMap<>();
        for (ObjectError err : bindingResult.getAllErrors()) {
            errMap.put(((DefaultMessageSourceResolvable) err.getArguments()[0]).getDefaultMessage(), err.getDefaultMessage());
        }
        return errMap;
    }
}
