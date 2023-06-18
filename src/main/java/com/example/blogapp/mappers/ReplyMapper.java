package com.example.blogapp.mappers;

import com.example.blogapp.dto.ReplyDto;
import com.example.blogapp.dto.UserDto;
import com.example.blogapp.entities.Reply;
import com.example.blogapp.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    @Named("noBlogs")
    @Mapping(target = "blogs", ignore = true)
    UserDto toUserDto(Users users);

    @Mapping(target = "user", qualifiedByName = "noBlogs")
    ReplyDto toDto(Reply reply);

    Reply toEntity(ReplyDto replyDto);

    List<ReplyDto> toDtoList(List<Reply> replies);
}
