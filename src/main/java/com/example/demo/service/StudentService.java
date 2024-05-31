package com.example.demo.service;

import com.example.demo.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.StudentRepository;

import org.springframework.stereotype.Service;


import com.example.demo.model.Student;

import com.example.demo.model.Course;

@Service
public class StudentService {
  
  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;


  public Student addCourseToStudent(Long studentId, Course course) {
    Student student = studentRepository.findById(studentId).orElse(null);
    if (student != null) {
      student.addCourse(course);
      courseRepository.save(course);
      return studentRepository.save(student);
    } 
    return null;
  }

  public Student removeCourseFromStudent(Long studentId, Course course) {
    Student student = studentRepository.findById(studentId).orElse(null); 
    if (student != null) {
      student.removeCourse(course);
      return studentRepository.save(student);
    }
    return null;
  }

}
