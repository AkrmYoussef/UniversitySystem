package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Course;

import com.example.demo.repository.CourseRepository;
import java.util.List;

@RestController

@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
   
    @GetMapping("/getCourses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

}
