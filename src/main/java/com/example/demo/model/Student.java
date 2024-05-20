package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    // Additional fields and methods for Student
    public Student() {}

    public Student(String name, String email) {
        super(name, email);
    }
}