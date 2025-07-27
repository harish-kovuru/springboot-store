package com.springbootcode.store.controllers;

import com.springbootcode.store.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handlingErrors(
            MethodArgumentNotValidException ex
    ){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnreadableMessage(){
        return ResponseEntity.badRequest().body(
                new ErrorDto("Invalid request type")
        );
    }

}
