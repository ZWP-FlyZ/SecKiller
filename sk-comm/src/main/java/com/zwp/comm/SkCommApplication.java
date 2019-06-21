package com.zwp.comm;

import com.zwp.comm.utils.MD5;
import com.zwp.comm.utils.TestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkCommApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SkCommApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TestUtils.doTest();

		System.err.println(MD5.encode("zwpasdfasdf567890asdf").substring(0,5));
	}


}
