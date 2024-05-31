package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return userService.getAllUsers().stream()
                          .filter(user -> user instanceof Admin)
                          .map(user -> (Admin) user)
                          .toList();
    }

    @PostMapping("/createAdmin")
    public Admin createAdmin(@RequestBody Admin admin) {
        return (Admin) userService.createUser(admin);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/updateAdmin/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return (Admin) userService.updateUser(id, admin);
    }
}
