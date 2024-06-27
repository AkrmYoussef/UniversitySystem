package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Instructor;
import com.example.demo.service.InstructorService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
  
    @Autowired
    private InstructorService instructorService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/getInstructors")
    public List<User> getAllInstructors() {
        return userService.getAllInstructors();
    }

    @GetMapping("/getLoggedInInstructorCourses")
    public List<Course> getCoursesTeachedByInstructor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); // This should be the email of the logged-in user

        User currentUser = userService.findByEmail(currentUserEmail);
        if (currentUser == null || !"INSTRUCTOR".equals(currentUser.getRole())) {
            throw new RuntimeException("Unauthorized access: You can only access your own courses.");
        }

        Instructor instructor = instructorService.findByEmail(currentUserEmail);
        if (instructor == null) {
            throw new RuntimeException("Instructor not found with email " + currentUserEmail);
        
        }
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
    
    @PutMapping("/{instructorId}/{courseId}/addCourse")
    public Instructor addCourseToInstructor(@PathVariable Long instructorId, @PathVariable Long courseId) {
        return instructorService.addCourseToInstructor(instructorId, courseId);
    }

    @PutMapping("/{instructorId}/{courseId}/removeCourse")
    public Instructor removeCourseFromInstructor(@PathVariable Long instructorId, @PathVariable Long courseId) {
        return instructorService.removeCourseFromInstructor(instructorId, courseId);
    }
    
}
