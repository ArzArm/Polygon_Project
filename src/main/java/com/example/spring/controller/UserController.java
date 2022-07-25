package com.example.spring.controller;

import com.example.spring.entity.User;
import com.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/convertor")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping(value = "/registration")
    public Integer registration(@Valid @RequestBody User user) {
        userService.saveUser(user);
        return user.getId();
    }


}
