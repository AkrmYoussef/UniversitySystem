package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Instructor;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.exception.EmailAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public Instructor findByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }

    public Instructor createInstructor(Instructor instructor) {
        try {
            userService.createUser(instructor);
            return instructorRepository.save(instructor);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Email already exists !");
        }
    }

    public Instructor addCourseToInstructor(Long instructorId, Long courseId) {
        System.out.println("Adding Course to Instructor: " + instructorId + " : " + courseId);

        // Fetch the instructor and course from the database
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id " + instructorId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));

        if (course.getInstructor() != null) {
            Instructor existingInstructor = course.getInstructor();
            if (!existingInstructor.getId().equals(instructor.getId())) {
                // Remove the course from the existing instructor
                existingInstructor.getCourses().remove(course);
                course.setInstructor(null);
                course.setInstructorId(null);
                course.setInstructorName(null);
                instructorRepository.save(existingInstructor);
                courseRepository.save(course); 
            }
        }

        // Associate the course with the new instructor
        course.setInstructor(instructor);
        course.setInstructorId(instructor.getId());
        course.setInstructorName(instructor.getName());
        instructor.getCourses().add(course);

        // Save both the instructor and the course
        courseRepository.save(course);
        instructorRepository.save(instructor);

        System.out.println("Course " + courseId + " assigned to Instructor " + instructorId);
        return instructor;
    }

    // write a method to update the instructor of a course

    public Instructor removeCourseFromInstructor(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (instructor != null) {
            if (course != null) {
                instructor.getCourses().remove(course);
                course.setInstructor(null);
            } else {
                throw new RuntimeException("Course not found with id " + courseId);
            }
            return instructorRepository.save(instructor);
        }
        return null;

    }
}
