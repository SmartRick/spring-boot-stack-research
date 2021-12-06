package cn.smartrick.service;

import cn.smartrick.entity.SysUser;
import cn.smartrick.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2021/12/4
 * @Author: SmartRick
 * @Description: TODO
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("username",username);
        SysUser sysUser = userMapper.selectOne(sysUserQueryWrapper);
        if (sysUser==null){
            throw new UsernameNotFoundException(username+"未找到");
        }
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("roles");
        return new User(sysUser.getUsername(),passwordEncoder.encode(sysUser.getPassword()),roles);
    }
}
