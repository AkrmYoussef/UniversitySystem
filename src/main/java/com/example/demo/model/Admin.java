package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    // Additional fields and methods for Admin
    

    public Admin(String name, String email) {
        super(name, email);
    }

    
}