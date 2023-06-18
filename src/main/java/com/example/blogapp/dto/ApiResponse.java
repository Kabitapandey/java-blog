package com.example.blogapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private String msg;

    private boolean success;

    public ApiResponse(String msg, Boolean success) {
        this.msg = msg;
        this.success = success;
    }
}
