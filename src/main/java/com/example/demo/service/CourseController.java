package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.CourseRepository;


@RestController

@RequestMapping("/api/courses")
public class CourseController {
     @Autowired
    private CourseRepository courseRepository;
   

}
