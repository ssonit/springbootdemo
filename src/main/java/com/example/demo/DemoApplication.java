package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// docker run -d --rm --name mysql-spring-boot -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_USER=sonhn -e MYSQL_PASSWORD=123456  -e MYSQL_DATABASE=test_db -p 3309:3306 mysql:latest

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
