package com.example.db.controller;


import com.example.db.bean.mybatis.User;
import com.example.db.dao.mybatis.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> getUser(@RequestParam(required = false) String name){

        return userMapper.getUserList(name);
    }
}
