package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping
    public boolean verify(@RequestBody User user){
        try{
            return userService.validate(user);
        } catch (InvalidCredentialsException e){
            System.out.println("You trying to hack us or something?");
            return false;
        }
    }
}
