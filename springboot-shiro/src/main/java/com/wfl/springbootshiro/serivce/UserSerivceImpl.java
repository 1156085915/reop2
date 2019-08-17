package com.wfl.springbootshiro.serivce;


import com.wfl.springbootshiro.domain.User;
import com.wfl.springbootshiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerivceImpl implements UserSerivce {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUser(String name) {
        return userMapper.findByUser(name);
    }

    @Override
    public User findByUserId(int id) {
        return userMapper.findByUserId(id);
    }
}
