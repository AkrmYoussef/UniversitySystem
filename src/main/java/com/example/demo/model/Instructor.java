package com.example.demo.model;

import jakarta.persistence.Entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//import jakarta.persistence.CascadeType;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@Entity
@Table(name = "instructors")
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {
    // add a field of type List<Course> to store the courses that the instructor teaches
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany
    @JsonManagedReference
    private List<Course> courses;

     
    public Instructor() {
        super();
        this.setRole("INSTRUCTOR"); 
    }

    public Instructor(String name, String email, String password) {
        super(name, email, password, "INSTRUCTOR");
        courses = new ArrayList<>();
    }

    // Getters and Setters
    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setInstructor(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setInstructor(null);
    }
}