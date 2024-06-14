package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Instructor;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{
  Optional<Instructor> findByEmail(String email);
}

