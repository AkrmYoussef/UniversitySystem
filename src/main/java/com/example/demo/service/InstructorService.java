package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Instructor;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.exception.EmailAlreadyExistsException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Instructor createInstructor(Instructor instructor) {
       try{
        return instructorRepository.save(instructor);
       }
       catch(DataIntegrityViolationException e){
           throw new EmailAlreadyExistsException("Email already exists !");
       }
    }

    public Instructor addCourseToInstructor(Long instructorId, Course course) {
        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor != null) {
            instructor.addCourse(course);
            courseRepository.save(course);
            return instructorRepository.save(instructor);
        }
        return null;
    }

    public Instructor removeCourseFromInstructor(Long instructorId, Course course) {
        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor != null) {
            instructor.removeCourse(course);
            return instructorRepository.save(instructor);
        }
        return null;
    }
}
