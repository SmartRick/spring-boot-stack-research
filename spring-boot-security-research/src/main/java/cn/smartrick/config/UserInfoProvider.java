package cn.smartrick.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Date: 2021/12/13 13:28
 * @Author: SmartRick
 * @Description: 用户详情身份提供者，调用dao进行具体认证校验逻辑
 */
public class UserInfoProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (this.supports(authentication.getClass())) {
            UserLoginToken loginToken = (UserLoginToken) authentication;
            Object username = loginToken.getCredentials();
            Object password = loginToken.getPrincipal();
            //TODO 查询数据库校验令牌是否通过

        }
        //如果令牌类型不支持就返回空
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserLoginToken.class.isAssignableFrom(aClass);
    }
}
