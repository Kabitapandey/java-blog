package com.example.blogapp.controller;

import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    @Operation(
            description = "Endpoint for adding comment",
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
    public Map<String, Object> addComment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((err) -> {
                String field = ((FieldError) err).getField();
                String msg = err.getDefaultMessage();

                response.put(field, msg);
            });

            return response;
        }

        this.commentService.addComment(commentDto);

        response.put("msg", "Comment added successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/comment")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            description = "Endpoint for listing all comments",
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
    public List<CommentDto> getAllComments() {
        return this.commentService.getComments();
    }

    @PutMapping("/comment/{id}")
    @Operation(
            description = "Endpoint for updating comment",
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
    public CommentDto updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer id) {
        return this.commentService.updateComment(commentDto, id);
    }

    @DeleteMapping("/comment/{id}")
    @Operation(
            description = "Endpoint for deleting comment",
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
    public Map<String, Object> deleteComment(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.commentService.delteComment(id);
        System.out.println(id);

        response.put("msg", "Comment deleted successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/comment/{id}")
    @Operation(
            description = "Endpoint for listing single comment",
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
    public CommentDto getSingleComment(@PathVariable Integer id) {
        return this.commentService.getSingleComment(id);
    }

    @GetMapping("/comment/user/{userId}")
    @Operation(
            description = "Endpoint for listing comment according to user",
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
    public List<CommentDto> getCommentsByUser(@PathVariable Integer userId) {
        return this.commentService.getCommentsByUser(userId);
    }
}
