package com.qf.qfv1userservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.qf.v1.mapper")
public class QfV1UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfV1UserServiceApplication.class, args);
	}

}
