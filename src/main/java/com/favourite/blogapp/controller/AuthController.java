package com.favourite.blogapp.controller;

import com.favourite.blogapp.dto.LoginDto;
import com.favourite.blogapp.dto.SignUpDto;
import com.favourite.blogapp.service.serviceImpl.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = {"/signUp","/register"})
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDto signUpDto){
        String user  = authService.createUser(signUpDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
@PostMapping(value = {"/logIn","signIn"})
    public ResponseEntity<String> Login (@Valid @RequestBody LoginDto loginDto){
        String response = authService.SignIn(loginDto);
        return ResponseEntity.ok(response);
    }

}
