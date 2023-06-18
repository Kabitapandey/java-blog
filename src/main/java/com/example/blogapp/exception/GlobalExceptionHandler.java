package com.example.blogapp.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> notFoundExceptionHandler(NotFoundException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg", ex.getMessage());
        response.put("success", false);

        return response;
    }

    @ExceptionHandler(EmailExistsException.class)
    public Map<String, Object> unauthorizedExceptionHandler(EmailExistsException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg", ex.getMessage());
        response.put("success", false);

        return response;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();

        if (ex.hasErrors()) {
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMsg = error.getDefaultMessage();

                response.put(fieldName, errorMsg);
                response.put("success", false);
            });
        }
        return response;
    }
//
//    @ExceptionHandler(BindException.class)
//    public Map<String, Object> bindExceptionHandler(BindException ex) {
//        BindingResult bindingResult = ex.getBindingResult();
//        Map<String, Object> response = new HashMap<>();
//
//        bindingResult.getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMsg = error.getDefaultMessage();
//
//            response.put(fieldName, errorMsg);
//            response.put("success", false);
//        });
//
//        return response;
//    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, Object> usernameNotFoundExceptionHandler(UsernameNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg", ex.getMessage());
        response.put("success", false);

        return response;
    }


//    @ExceptionHandler(EmailExistsException.class)
//    public Map<String, Object> emailExistsExceptionHandler(EmailExistsException ex) {
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("msg", ex.getMessage());
//        response.put("success", false);
//
//        return response;
//    }
}
