package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.CourseFile;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

  
    @GetMapping("/getCourses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/createCourse")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PutMapping("/updateCourse/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @PostMapping("/{courseId}/uploadFile")
    public CourseFile uploadFile(@PathVariable Long courseId, @RequestParam("file") MultipartFile file) {
        return courseService.addFileToCourse(courseId, file);
    }

    @GetMapping("/{courseId}/files")
    public List<CourseFile> getCourseFiles(@PathVariable Long courseId) {
        return courseService.getCourseFiles(courseId);
    }

    //delete file from a certain course
    @DeleteMapping("/{courseId}/deleteFile/{fileId}")
    public void deleteCourseFile(@PathVariable Long courseId, @PathVariable Long fileId) {
        courseService.deleteCourseFile(courseId, fileId);
    }

}
