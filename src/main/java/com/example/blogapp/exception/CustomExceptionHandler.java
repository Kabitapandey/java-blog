package com.example.blogapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(EmailExistsException.class)
//    public Map<String, Object> handleUnauthorizedException(EmailExistsException ex) {
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("msg", ex.getMessage());
//        response.put("success", false);
//
//        return response;
//    }
}
