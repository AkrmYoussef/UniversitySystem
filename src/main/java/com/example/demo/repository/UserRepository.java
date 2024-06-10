package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String username);
}