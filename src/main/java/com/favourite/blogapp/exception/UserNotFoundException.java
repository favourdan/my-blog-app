package com.favourite.blogapp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotFoundException extends RuntimeException{


    public UserNotFoundException(String message) {
        super(message);

    }
}
