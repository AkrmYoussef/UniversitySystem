package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.CourseFile;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.service.InstructorService;


import java.util.List;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

  
    @GetMapping("/getCourses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/getCourse/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/createCourse")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            throw new RuntimeException("Course not found with id " + id);
        }
        Long instructor_id = course.getInstructorId();
        instructorService.removeCourseFromInstructor(instructor_id, id);
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
    public void deleteCourseFile(@PathVariable Long courseId ,@PathVariable Long fileId) {
        courseService.deleteCourseFile(courseId, fileId);
    }

 
}
