package com.example.blogapp.mappers;

import com.example.blogapp.dto.BlogDto;
import com.example.blogapp.entities.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface BlogMapper {
    Blog toEntity(BlogDto blogDto);

    @Mapping(target = "comments")
    BlogDto toDto(Blog blog);

    List<BlogDto> blogToList(List<Blog> blogs);

}
