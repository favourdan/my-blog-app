package com.favourite.blogapp.security;

import com.favourite.blogapp.entity.User;
import com.favourite.blogapp.exception.ResourceNotFound;
import com.favourite.blogapp.exception.UserNotFoundException;
import com.favourite.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(
                ()->new UserNotFoundException("user with this email or userName is not found"));
        Set<GrantedAuthority> authorities = user.getRoles().stream().map((roles)-> new
                SimpleGrantedAuthority(roles.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),authorities);
    }


}
