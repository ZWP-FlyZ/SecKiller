package com.zwp.repo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class,
								RedisRepositoriesAutoConfiguration.class})
public class SkRepoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SkRepoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
