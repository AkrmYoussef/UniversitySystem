package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Instructor")
public class Instructor extends User {
    
    public Instructor() {}

    public Instructor(String name, String email) {
        super(name, email);
    }
}