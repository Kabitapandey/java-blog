package com.example.blogapp.controller;

import com.example.blogapp.dto.BlogDto;
import com.example.blogapp.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    @Operation(
            description = "Endpoint for adding blog",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
            }
    )
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
    @Operation(
            description = "Endpoint for list all blogs",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
            }
    )
    public List<BlogDto> getAllBlogs() {
        return this.blogService.getBlogs();
    }

    @PutMapping("/blog/{id}")
    @Operation(
            description = "Endpoint for updating blog",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public BlogDto updateBlog(@RequestBody BlogDto blogDto, @PathVariable Integer id) {
        return this.blogService.updateBlog(blogDto, id);
    }

    @DeleteMapping("/blog/{id}")
    @Operation(
            description = "Endpoint for deleting blog",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public Map<String, Object> deleteBlog(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.blogService.deletedBlog(id);

        response.put("msg", "Blog deleted successfully!");
        response.put("success", true);
        return response;
    }

    @GetMapping("/blog/{id}")
    @Operation(
            description = "Endpoint for listing a single blog",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public BlogDto getSingleBlog(@PathVariable Integer id) {
        return this.blogService.getSingleBlog(id);
    }

    @GetMapping("/blog/user/{userId}")
    @Operation(
            description = "Endpoint for listing blog or particular user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    ),
            }
    )
    public List<BlogDto> getBlogByUser(@PathVariable Integer userId) {
        return this.blogService.getBlogByUser(userId);
    }
}
