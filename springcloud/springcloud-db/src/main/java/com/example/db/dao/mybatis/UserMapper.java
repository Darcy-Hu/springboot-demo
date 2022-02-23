package com.example.db.dao.mybatis;

import com.example.db.bean.mybatis.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    List<User> getUserList(String name);
}
