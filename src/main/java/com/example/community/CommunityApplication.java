package com.example.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.example.community.mapper")
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
		Class<CommunityApplication> communityApplicationClass = CommunityApplication.class;
	}

}
