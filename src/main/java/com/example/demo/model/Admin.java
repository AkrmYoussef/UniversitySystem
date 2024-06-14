package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    // Additional fields and methods for Admin
    public Admin(){
        super();
        this.setRole("ADMIN");
    }

    public Admin(String name, String email, String password) {
        super(name, email, password ,"ADMIN");
    }

    
}