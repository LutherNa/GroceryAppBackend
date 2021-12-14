package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.UserAuthRequest;
import com.revature.vanqapp.model.UserAuthResponse;
import com.revature.vanqapp.security.TokenAuthProvider;
import com.revature.vanqapp.service.UserService;
import com.revature.vanqapp.util.JwtUtil;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController @CrossOrigin("*")
@RequestMapping(value = "/public/users")
public class PublicUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    /**
     * Creates user in database from RequestBody, then passes RequestBody to login.
     * @param userAuthRequest UserAuthRequest object containing Username and Password.
     * @return Returns ResponseEntity from login containing JWT.
     * @throws Exception Throws BadCredentialsException on login failure.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody UserAuthRequest userAuthRequest) throws Exception {
        User user = userService.createUser(userAuthRequest);
        return login(userAuthRequest);
    }

    /**
     * Returns JWT when provided correct credentials.
     * @param userAuthRequest UserAuthRequest object containing Username and Password.
     * @return Returns ResponseEntity containing JWT.
     * @throws Exception Throws BadCredentialsException on login failure.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest userAuthRequest) throws Exception {
        try {
            tokenAuthProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(), userAuthRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final User user = userService
                .loadUserByUsername(userAuthRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new UserAuthResponse(jwt));
//        return userService.login(userAuthRequest.getUsername(),userAuthRequest.getPassword())
//                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

}
