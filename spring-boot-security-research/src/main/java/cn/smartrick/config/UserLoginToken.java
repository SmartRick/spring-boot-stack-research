package cn.smartrick.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Date: 2021/12/13 13:16
 * @Author: SmartRick
 * @Description: 自定义认证令牌，其中因包含认证和授权时需要用到的用户权限信息。
 *               令牌一开始是对用户提交数据的封装，如果认证通过还会向其中添加权限信息
 *
 */
public class UserLoginToken extends AbstractAuthenticationToken {
    /**
     * 用户名称或主体标识，例如电话号码邮箱等等
     */
    public Object principal;
    /**
     * 用户密码或主体验证信息，如验证码等等
     */
    public Object credentials;
    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    public UserLoginToken(Object principal, Object credentials) {
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public UserLoginToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
