package com.wfl.springbootshiro.serivce;

import com.wfl.springbootshiro.domain.User;

public interface UserSerivce {

    public User findByUser(String name);

    User findByUserId(int id);
}
