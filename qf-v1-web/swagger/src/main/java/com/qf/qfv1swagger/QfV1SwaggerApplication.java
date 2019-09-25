package com.qf.qfv1swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QfV1SwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfV1SwaggerApplication.class, args);
	}

}

