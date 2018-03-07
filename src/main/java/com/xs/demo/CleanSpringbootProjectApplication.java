package com.xs.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CleanSpringbootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanSpringbootProjectApplication.class, args);
	}
}
