package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Instructor;
public interface InstructorRepository extends JpaRepository<Instructor, Long>{
  
}
