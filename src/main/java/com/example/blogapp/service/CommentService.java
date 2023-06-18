package com.example.blogapp.service;

import com.example.blogapp.dto.CommentDto;

import java.util.List;


public interface CommentService {

    void addComment(CommentDto comment);

    List<CommentDto> getComments();

    CommentDto updateComment(CommentDto comment, Integer id);

    void delteComment(Integer id);

    CommentDto getSingleComment(Integer id);

    List<CommentDto> getCommentsByUser(Integer userId);
}
