package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.controller.UserController;

@SpringBootTest
class DemoApplicationTests {
  @Autowired
  private UserController userController;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	

}
