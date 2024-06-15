package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import java.util.List;



@DataJpaTest

public class UserRepositoryTest {
 
  @Autowired
  private UserRepository userRepository;

  @Test
  void saveUser() {
    userRepository.save(new User("ahmed helmy","ahmed.helmy@guc.edu.eg","netw","INSTRUCTOR"));
    assertThat(
        userRepository.findByEmail("ahmed.helmy@guc.edu.eg").isPresent()
    ).isTrue();
  }
  
  
  @Test
  void deleteUsers() {
     userRepository.save(new User("omar shehata", "omar.shehata@guc.edu.eg","optimization","INSTRUCTOR"));
     userRepository.deleteAll();
     assertThat(userRepository.count()).isEqualTo(0);
  }


  @Test
  void findByRole() {
    // Arrange
    userRepository.save(new User("ahmed helmy", "ahmed.helmy@guc.edu.eg", "netw", "INSTRUCTOR"));
    userRepository.save(new User("omar shehata", "omar.shehata@guc.edu.eg", "optimization", "INSTRUCTOR"));
    userRepository.save(new User("omar sameh", "omar.sameh@example.com", "omar", "STUDENT"));

    // Act
    List<User> instructors = userRepository.findByRole("INSTRUCTOR");
    List<User> students = userRepository.findByRole("STUDENT");

    // Assert
    assertThat(instructors).hasSize(2);
    assertThat(students).hasSize(1);
    assertThat(instructors).extracting(User::getEmail).containsExactlyInAnyOrder("ahmed.helmy@guc.edu.eg", "omar.shehata@guc.edu.eg");
    assertThat(students).extracting(User::getEmail).containsExactly("omar.sameh@example.com");
  }

}
