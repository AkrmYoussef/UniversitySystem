package com.example.demo.model;

import jakarta.persistence.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.CascadeType;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import enums.faculty;

import java.util.ArrayList;

@Entity
@Table(name = "instructors")
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {

    // add a field of type List<Course> to store the courses that the instructor
    // teaches
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(mappedBy = "instructor", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH })
    @JsonManagedReference
    private List<Course> courses;

    private String department;

    // faculty enum
    private faculty faculty;

    public Instructor() {
        super();
        this.setRole("INSTRUCTOR");

    }

    public Instructor(String name, String email, String password, String department, faculty faculty) {
        super(name, email, password, "INSTRUCTOR");
        courses = new ArrayList<>();
        this.department = department;
        this.faculty = faculty;
    }

    // Getters and Setters
    public List<Course> getCourses() {
        return courses;
    }

    // department getter and setter
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // faculty getter and setter
    public faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(faculty faculty) {
        this.faculty = faculty;
    }
}