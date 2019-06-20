package com.zwp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.zwp"})
public class SkWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkWebApplication.class, args);
	}

}
