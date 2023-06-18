package com.example.blogapp.service;

import com.example.blogapp.dto.UserDto;

import java.util.List;

public interface UserService {
    void addUser(UserDto user);

    List<UserDto> getUser();

    UserDto updateUser(UserDto user, Integer id);

    void deleteUser(Integer id);

    UserDto getSingleUser(Integer id);

}
