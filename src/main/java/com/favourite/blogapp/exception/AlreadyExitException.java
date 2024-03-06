package com.favourite.blogapp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlreadyExitException extends RuntimeException{

    private String ResourceMessage;

    public AlreadyExitException(String message) {
        super(message);
    }

}
