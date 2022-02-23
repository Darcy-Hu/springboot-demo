package com.springcloud.web.controller;

import com.springcloud.web.config.dataSource.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/api")
public class DataSourceController {

  /*  @Autowired
    UserMapper userMapper;

    @GetMapping("/dataSource")
    public Map<String,Object> testDataSourceType(String key){
        DynamicDataSourceContextHolder.setDataSourceKey(key);
        return userMapper.getUser();
    }*/
}
