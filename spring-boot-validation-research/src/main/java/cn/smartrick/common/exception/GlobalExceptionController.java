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
 * @Description: TODO
 */
@ControllerAdvice
@RestController
public class GlobalExceptionController {

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
