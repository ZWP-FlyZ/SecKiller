package com.zwp.service;

import ch.qos.logback.core.util.TimeUtil;
import com.zwp.service.rabbitmq.RabbitMqSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

//@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.zwp",
						exclude = {RedisAutoConfiguration.class,
									RedisRepositoriesAutoConfiguration.class})
public class SkServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SkServiceApplication.class, args);
	}

	@Autowired
	RabbitMqSendService service;


	@Scheduled(cron = "0/2 * *  * * ?")
	public void send(){
		service.sendStringMessage("mmmm");
		System.err.println("sent");
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
