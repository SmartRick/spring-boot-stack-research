package cn.smartrick.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date: 2021/12/13 13:48
 * @Author: SmartRick
 * @Description: 授权异常切入点，当用户访问一个自身未授权或者说权限不够的资源时会通过这个反馈一个权限不足的信息。
 *               注意与AuthenticationEntryPoint的区别，AuthenticationEntryPoint处理未认证情况，而这个处理的是认证后权限不足的情况
 *
 *               执行时机是通过ExceptionTranslationFilter捕获到授权异常时调用的：
 *                  参考源码org.springframework.security.web.access.ExceptionTranslationFilter的handleAccessDeniedException方法
 */
public class SecureAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

    }
}
