package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @CrossOrigin("*")
@RequestMapping(value = "/public/users")
public class PublicUserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String createUser(@RequestBody User user){
        return login(userService.createUser(user));
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getUsername(),user.getPassword())
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

}
