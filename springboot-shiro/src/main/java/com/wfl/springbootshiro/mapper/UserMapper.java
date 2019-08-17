package com.wfl.springbootshiro.mapper;

import com.wfl.springbootshiro.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where name=#{name}")
    User findByUser(String name);

    @Select("select * from user where id=#{id}")
    User findByUserId(int id);
}
