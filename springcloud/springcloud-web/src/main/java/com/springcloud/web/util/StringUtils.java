package com.springcloud.web.util;

import lombok.Getter;

import java.util.Map;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private Map<String,Object> map;

    public StringUtils(Map<String,Object> map){
        this.map = map;
    }


    public Map<String,Object> getMap() {
        return this.map;
    }
}
