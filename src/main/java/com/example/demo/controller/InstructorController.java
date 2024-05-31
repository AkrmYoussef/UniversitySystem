package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Instructor;
import com.example.demo.service.InstructorService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
  
    @Autowired
    private InstructorService instructorService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return userService.getAllUsers().stream()
                          .filter(user -> user instanceof Instructor)
                          .map(user -> (Instructor) user)
                          .toList();
    }

    @GetMapping("/{id}/getCourses")
    public List<Course> getCoursesTeachedByInstructor(@PathVariable Long id) {
        Instructor instructor =  (Instructor) userService.getUserById(id);
        return instructor.getCourses();
    }

    @PostMapping("/createInstructor")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return (Instructor) userService.createUser(instructor);
    }

    @DeleteMapping("/deleteInstructor/{id}")
    public void deleteInstructor(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/updateInstructor/{id}")
    public Instructor updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        return (Instructor) userService.updateUser(id, instructor);
    }
    
    @PutMapping("/{instructorId}/addCourse")
    public Instructor addCourseToInstructor(@PathVariable Long instructorId, @RequestBody Course course) {
        return instructorService.addCourseToInstructor(instructorId, course);
    }

    @PutMapping("/{instructorId}/removeCourse")
    public Instructor removeCourseFromInstructor(@PathVariable Long instructorId, @RequestBody Course course) {
        return instructorService.removeCourseFromInstructor(instructorId, course);
    }
    
}
