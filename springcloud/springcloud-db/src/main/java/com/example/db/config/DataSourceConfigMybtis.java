package com.example.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.db.dao.mybatis"},sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class DataSourceConfigMybtis {
    @Autowired
    DBDefaultConfig dbDefaultConfig;

    @Primary
    @Bean("mybatisDataSource")
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbDefaultConfig.getDriverClassName());
        config.setUsername(dbDefaultConfig.getUsername());
        config.setPassword(dbDefaultConfig.getPassword());
        config.setJdbcUrl(dbDefaultConfig.getUrl());
        config.setConnectionTestQuery(dbDefaultConfig.getValidationQuery());
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean("mybatisSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mybatisDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("mybatisTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("mybatisDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("mybatisSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
