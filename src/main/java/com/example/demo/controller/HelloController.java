package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        log.info("Hello, Spring Boot!");
        return "Hello, Spring Boot!";
    }
}