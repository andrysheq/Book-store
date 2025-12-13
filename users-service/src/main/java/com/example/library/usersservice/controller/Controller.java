package com.example.library.usersservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class Controller {

    @GetMapping("/books")
    public String getAllBooks() {

        return "!!!!";
    }

    @GetMapping("/")
    public String root() {
        return "users-service";
    }
}
