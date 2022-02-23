package com.springcloud.web.config.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Autowired
    DBDefaultConfig dbDefaultConfig;
    @Autowired
    DBDevConfig dbDevConfig;

    private DataSource createDataSource(BaseDBConfig dbConfig){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbConfig.getDriverClassName());
        dataSource.setUsername(dbConfig.getUsername());
        dataSource.setPassword(dbConfig.getPassword());
        return dataSource;
    }

    public Map<Object,Object> dataSourceMap(){
        Map<Object,Object> sourceMap = new HashMap<>();
        sourceMap.put("dev",createDataSource(dbDevConfig));
        sourceMap.put("default",createDataSource(dbDefaultConfig));
        return sourceMap;
    }

    @Bean("DynamicDataSource")
    public DynamicDataSource dataSource(){
        DynamicDataSource dataSource = new DynamicDataSource();
        //需要设置默认数据库,否则在没有请求时，不知道连接哪个数据库，会报数据库连接错误
        dataSource.setDefaultTargetDataSource(createDataSource(dbDefaultConfig));
        dataSource.setTargetDataSources(dataSourceMap());
        return dataSource;
    }

    @Bean("jdbcTemplate")
    public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("DynamicDataSource") DynamicDataSource dataSource){
        return new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    }
}
