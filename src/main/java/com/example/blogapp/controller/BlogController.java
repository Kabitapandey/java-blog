package com.example.blogapp.controller;

import com.example.blogapp.dto.BlogDto;
import com.example.blogapp.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog")
    public Map<String, Object> addBlog(@Valid @RequestBody BlogDto blogDto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((err) -> {
                String field = ((FieldError) err).getField();
                String msg = err.getDefaultMessage();

                response.put(field, msg);
            });
            return response;
        }

        this.blogService.addABlog(blogDto);

        response.put("msg", "Blog added successfully!");
        response.put("success", true);
        return response;
    }

    @GetMapping("/blog/get")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<BlogDto> getAllBlogs() {
        return this.blogService.getBlogs();
    }

    @PutMapping("/blog/{id}")
    public BlogDto updateBlog(@RequestBody BlogDto blogDto, @PathVariable Integer id) {
        return this.blogService.updateBlog(blogDto, id);
    }

    @DeleteMapping("/blog/{id}")
    public Map<String, Object> deleteBlog(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.blogService.deletedBlog(id);

        response.put("msg", "Blog deleted successfully!");
        response.put("success", true);
        return response;
    }

    @GetMapping("/blog/{id}")
    public BlogDto getSingleBlog(@PathVariable Integer id) {
        return this.blogService.getSingleBlog(id);
    }

    @GetMapping("/blog/user/{userId}")
    public List<BlogDto> getBlogByUser(@PathVariable Integer userId) {
        return this.blogService.getBlogByUser(userId);
    }
}
