package cn.smartrick.service;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */

import cn.smartrick.entity.User;
import cn.smartrick.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getById(Integer id) {
        return userMapper.selectById(id);
    }
    @Transactional(rollbackFor = {Exception.class})
    public boolean addUser(User user) {
        return userMapper.insert(user) == 1;
    }
}
