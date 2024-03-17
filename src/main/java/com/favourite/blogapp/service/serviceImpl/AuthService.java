package com.favourite.blogapp.service.serviceImpl;

import com.favourite.blogapp.dto.LoginDto;
import com.favourite.blogapp.dto.SignUpDto;

public interface AuthService{
    String createUser(SignUpDto signUpDto);

    String SignIn(LoginDto loginDto);
}
