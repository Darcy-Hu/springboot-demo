package com.springcloud.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableJdbcHttpSession
public class SpringcloudWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudWebApplication.class, args);
	}

}
