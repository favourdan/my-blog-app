package com.favourite.blogapp.exception;

import com.favourite.blogapp.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound
            (WebRequest webRequest , ResourceNotFound resourceNotFound){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),resourceNotFound.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException
            (WebRequest webRequest , ApiException apiException){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),apiException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExitException.class)
    public ResponseEntity<ErrorDetails> handleAlreadyExistException
            (WebRequest webRequest , AlreadyExitException exception){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException
            (WebRequest webRequest , Exception exception){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
