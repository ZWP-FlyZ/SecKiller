package com.zwp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class SkRepoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SkRepoApplication.class, args);
	}

	@Autowired
	DataSource readDatasource;

	@Autowired
	DataSource writeDatasource;

	@Override
	public void run(String... args) throws Exception {
		System.err.println(readDatasource);
		System.err.println(writeDatasource);
		System.err.println(readDatasource==writeDatasource);
	}
}
