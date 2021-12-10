package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

//    Tutorial has this, not 100% sure what it does for the program - NL
    @GetMapping("/current")
    User getCurrent(@AuthenticationPrincipal User user) {
        System.out.println(user.toString());
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal User user) {
        userService.logout(user);
        return true;
    }

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
