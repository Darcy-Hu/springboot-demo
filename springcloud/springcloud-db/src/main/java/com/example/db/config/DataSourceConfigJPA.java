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
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
                        transactionManagerRef = "jpaTransactionManager",
                        basePackages = {"com.example.db.dao.jpa"})
public class DataSourceConfigJPA {
    @Autowired
    private DBDevConfig dbDevConfig;
    @Autowired
    private JpaProperties jpaProperties;

    @Bean("jpaDataSource")
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbDevConfig.getDriverClassName());
        config.setUsername(dbDevConfig.getUsername());
        config.setPassword(dbDevConfig.getPassword());
        config.setJdbcUrl(dbDevConfig.getUrl());
        config.setConnectionTestQuery(dbDevConfig.getValidationQuery());
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                       @Qualifier("jpaDataSource") DataSource dataSource){
        return entityManagerFactoryBuilder.dataSource(dataSource)
                                          .properties(jpaProperties.getProperties())
                                          .packages("com.example.db.bean.jpa") //实体类存放package
                                          .build();
    }

    public EntityManager entityManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return entityManagerFactory.getObject().createEntityManager();
    }

    @Bean("jpaTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }


}
