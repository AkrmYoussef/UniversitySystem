package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import static org.springframework.test.web.
servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.
request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testAuthentication() throws Exception {
    // Testing authentication with correct credentials
    this.mockMvc
        .perform(post("/api/login")
            .content("{\"username\":\"wassim.alexan@guc.edu.eg\",\"password\""
                + ":\"dld\"}")
            .header(HttpHeaders.CONTENT_TYPE, "application/json"))
        .andDo(print()).andExpect(status().isOk());
  }

}
