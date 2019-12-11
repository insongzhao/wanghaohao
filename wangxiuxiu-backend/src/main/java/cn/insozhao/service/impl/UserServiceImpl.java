package cn.insozhao.service.impl;

import cn.insozhao.beans.User;
import cn.insozhao.mapper.UserMapper;
import cn.insozhao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean login(User user) {
        return userMapper.login(user) == 1;
    }
}
