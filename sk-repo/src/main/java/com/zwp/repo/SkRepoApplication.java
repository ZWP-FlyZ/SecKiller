package com.zwp.repo;

import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.DynamicDatasourceAspect;
import com.zwp.repo.datasourceconfig.TestService;
import com.zwp.repo.datasourceconfig.UseDatasource;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication

public class SkRepoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SkRepoApplication.class, args);
	}

	@Autowired
	DataSource datasource;

	@Autowired
	DataSource readDatasource;

	@Autowired
	DataSource writeDatasource;

	@Autowired
	DynamicDatasourceAspect aspect;

	@Autowired
	TestService ts;

	@Override
	public void run(String... args) throws Exception {
		System.err.println(aspect);
		ts.doService();
		ts.doService2();
	}

}
