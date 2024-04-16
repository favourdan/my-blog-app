package com.favourite.blogapp.service;

import com.favourite.blogapp.dto.LoginDto;
import com.favourite.blogapp.dto.SignUpDto;
import com.favourite.blogapp.entity.Roles;
import com.favourite.blogapp.entity.User;
import com.favourite.blogapp.exception.ApiException;
import com.favourite.blogapp.repository.RoleRepository;
import com.favourite.blogapp.repository.UserRepository;
import com.favourite.blogapp.security.JwtTokenProvider;
import com.favourite.blogapp.service.serviceImpl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String createUser(SignUpDto signUpDto) {
        // check whether userName or Password exist
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"email exists");
        }
        if(userRepository.existsByUserName(signUpDto.getUserName())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"userName exists");
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUserName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//        Set<Roles> roles = new HashSet<>();
//        Roles role = roleRepository.findByName("ROLE_USER").get()
        Roles role = new Roles();
        role.setName("ROLE_USER");
      Set<Roles> roles = new HashSet<>();
      roles.add(role);
      user.setRoles(roles);
        userRepository.save(user);

        return "user has been successfully registered";
    }

    @Override
    public String SignIn(LoginDto loginDto) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUserNameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return token;


    }
}
