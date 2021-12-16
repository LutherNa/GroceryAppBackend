package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

@RestController @CrossOrigin("*")
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Verifies a submitted JWT and returns User object corresponding to submitted JWT.
     * @param token JWT from prior successful login attempt.
     * @return User object corresponding to JWT in RequestHeader.
     */
    @GetMapping("/current")
    ResponseEntity<?> getCurrent(@RequestHeader("Authorization") String token) {
        String tokenParsed = token.replace("Bearer", "").trim();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(tokenParsed);
        try {
            User user = userService.findByToken(tokenParsed);
            user.setPassword(null);
            return new ResponseEntity<User>(user, responseHeaders, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
