package com.example.blogapp.service;

import com.example.blogapp.dto.BlogDto;

import java.util.List;


public interface BlogService {
    void addABlog(BlogDto blog);

    List<BlogDto> getBlogs();

    BlogDto updateBlog(BlogDto blog, Integer id);

    void deletedBlog(Integer id);

    BlogDto getSingleBlog(Integer id);

    List<BlogDto> getBlogByUser(Integer userId);
}
