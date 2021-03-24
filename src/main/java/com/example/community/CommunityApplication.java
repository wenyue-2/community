package com.example.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Proxy;

@SpringBootApplication
@MapperScan(basePackages = "com.example.community.mapper")
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
		Class<CommunityApplication> communityApplicationClass = CommunityApplication.class;
	}

}
