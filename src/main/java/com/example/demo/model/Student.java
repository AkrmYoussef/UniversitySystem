package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.DiscriminatorValue;
import java.util.List;



import java.util.ArrayList;



@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    // Additional fields and methods for Student
    @OneToMany
    private List<Course> courses;

    public Student(){
     super();
     this.setRole("STUDENT");
    }
    public Student(String name, String email, String password) {
        super(name, email, password, "STUDENT");
        courses = new ArrayList<>();
    }

    // Getters and Setters
    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
    
}