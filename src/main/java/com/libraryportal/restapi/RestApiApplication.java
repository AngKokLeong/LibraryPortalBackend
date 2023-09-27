package com.libraryportal.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libraryportal.restapi.data_loader.PostgreSQLJDBC;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(RestApiApplication.class, args);
	}

}
