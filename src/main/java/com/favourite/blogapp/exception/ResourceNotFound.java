package com.favourite.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{
    private String resource;
    private String field;
    private Long fieldValue;

    public ResourceNotFound(String resource, String field, Long fieldValue) {
        super(String.format("'%s not found with %s: %s'",resource,field,fieldValue));
        this.resource = resource;
        this.field = field;
        this.fieldValue = fieldValue;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    public Long fieldValue() {
        return fieldValue;
    }
}
