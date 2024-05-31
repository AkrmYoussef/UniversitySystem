package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.example.demo.model.Course;

import com.example.demo.service.StudentService;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return userService.getAllUsers().stream()
                          .filter(user -> user instanceof Student)
                          .map(user -> (Student) user)
                          .toList();
    }

    @PostMapping("/createStudent")
    public Student createStudent(@RequestBody Student student) {
        return (Student) userService.createUser(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/updateStudent/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return (Student) userService.updateUser(id, student);
    }

    @PutMapping("/{studentId}/addCourse")
    public Student addCourseToStudent(@PathVariable Long studentId, @RequestBody Course course) {
        return studentService.addCourseToStudent(studentId, course);
    }

    @PutMapping("/{studentId}/removeCourse")
    public Student removeCourseFromStudent(@PathVariable Long studentId, @RequestBody Course course) {
        return studentService.removeCourseFromStudent(studentId, course);
    }

    //write a method to get all courses of a student
    @GetMapping("/{studentId}/getCourses")
    public List<Course> getCoursesOfStudent(@PathVariable Long studentId) {
        Student student = (Student) userService.getUserById(studentId);
        return student.getCourses();
    }
}
