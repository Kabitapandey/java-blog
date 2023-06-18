package com.example.blogapp.mappers;

import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.dto.UserDto;
import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReplyMapper.class})
public interface CommentMapper {
    @Named("ignoreBlog")
    @Mapping(target = "blogs", ignore = true)
    UserDto toUserDto(Users users);

    Comment toEntity(CommentDto commentDto);

    @Mapping(target = "user", qualifiedByName = "ignoreBlog")
    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);

}
