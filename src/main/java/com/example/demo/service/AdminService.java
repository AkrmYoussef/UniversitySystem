package com.example.demo.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.repository.AdminRepository;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.model.Admin;


@Service
public class AdminService {

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private UserService userService;

  public Admin createAdmin(Admin admin) {
    try {
      userService.createUser(admin);
      return adminRepository.save(admin);
    } catch (DataIntegrityViolationException e) {
      throw new EmailAlreadyExistsException("Email already exists !");
    }

  }

}
