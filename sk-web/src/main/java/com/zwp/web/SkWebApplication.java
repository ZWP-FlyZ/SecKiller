package com.zwp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;

@SpringBootApplication(scanBasePackages={"com.zwp"},
						exclude = {DataSourceAutoConfiguration.class})
public class SkWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkWebApplication.class, args);
	}

}
