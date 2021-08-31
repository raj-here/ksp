package com.ksp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KnowledgeSharePointApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeSharePointApplication.class, args);
	}

}
