package com.example.demo.repository;

import com.example.demo.model.CourseFile;
import com.example.demo.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseFileRepository extends JpaRepository<CourseFile, Long> {
  List<CourseFile> findByCourse(Course course);
}