package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;


import com.example.demo.controller.CourseController;

@SpringBootTest
class DemoApplicationTests {
  @Autowired
  private CourseController courseController;

	@Test
	@DisplayName("CourseController context loads")
	void contextLoads() {
		assertThat(courseController).isNotNull();
	}

	

}
