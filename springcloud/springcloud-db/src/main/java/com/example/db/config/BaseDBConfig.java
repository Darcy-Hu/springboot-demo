package com.example.db.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDBConfig {
    private String username;
    private String url;
    private String password;
    private String driverClassName;
    private boolean testOnBorrow;
    private String validationQuery;
}
