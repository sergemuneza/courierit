package com.courierit.controllers;

import com.courierit.payloads.SignInRequest;
import com.courierit.payloads.SignInResponse;
import com.courierit.payloads.SignUpRequest;
import com.courierit.payloads.SignUpResponse;
import com.courierit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            SignUpResponse response = userService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        return userService.signIn(request);
    }
}