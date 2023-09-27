package com.libraryportal.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.libraryportal.restapi.data_loader.PostgreSQLJDBC;

@SpringBootTest
class RestApiApplicationTests {

	@Test
	void contextLoads() {
		PostgreSQLJDBC.execute();
	}

}
