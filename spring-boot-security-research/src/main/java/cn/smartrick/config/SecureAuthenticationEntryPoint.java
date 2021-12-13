package cn.smartrick.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date: 2021/12/13 13:47
 * @Author: SmartRick
 * @Description: 认证异常切入点，当用户访问一个需要认证的资源但未认证时会走这个逻辑。
 *               注意和AuthenticationFailureHandler的区别，failureHandler是用户主动发起认证但认证失败才会执行的。
 *               两种都在处理认证相关异常，但时机不同。
 *
 *               执行时机是通过ExceptionTranslationFilter捕获到认证异常时调用的：
 *  *                  参考源码org.springframework.security.web.access.ExceptionTranslationFilter的handleAuthenticationException方法
 */
public class SecureAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

    }
}
