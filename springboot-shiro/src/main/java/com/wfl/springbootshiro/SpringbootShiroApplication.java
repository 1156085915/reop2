package com.wfl.springbootshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wfl.springbootshiro.mapper")
public class SpringbootShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootShiroApplication.class, args);
	}

}
