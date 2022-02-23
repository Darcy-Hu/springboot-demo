package com.springcloud.web.config.dataSource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "db.dev")
public class DBDevConfig extends BaseDBConfig {
}
