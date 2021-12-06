package cn.smartrick.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.security.krb5.Realm;

import javax.annotation.Resource;

/**
 * @Date: 2021/12/4
 * @Author: SmartRick
 * @Description: SpringSecurity配置，继承配置适配器选择性的重写自定义配置
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * SpringSecurity MVC核心配置方法
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表示即将对登录处理配置
        http.formLogin()
                //登录页面，未认证、认证失败时自动跳转到该页面
                .loginPage("login.html")
                //登录处理后端接口
                .loginProcessingUrl("/user/login")
                //登录提交的用户名参数
                .usernameParameter("username")
                //登录提交的密码参数
                .passwordParameter("password")
                //登录成功后跳转的url
                .defaultSuccessUrl("/login/success")
                .permitAll()
                //登录成功后的处理器，与登录成功的url二选一就行
//                .successHandler();
                //叠加其他类型配置，springSecurity采用链式编程配置方式
                .and()
                //表示即将对授权请求进行配置
                .authorizeRequests()
                //设置匹配器
//                .antMatchers("/", "/hello").permitAll()
//                .antMatchers("/buy").hasAnyAuthority("user:admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
