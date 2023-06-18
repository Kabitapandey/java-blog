package com.example.blogapp.mappers;

import com.example.blogapp.dto.UserDto;
import com.example.blogapp.entities.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BlogMapper.class})
public interface UserMapper {
    Users toEntity(UserDto userDto);


    UserDto toDto(Users users);

    List<UserDto> userDtoList(List<Users> users);
}
