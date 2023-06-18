package com.example.blogapp.controller;

import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.service.CommentService;
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
    public List<CommentDto> getAllComments() {
        return this.commentService.getComments();
    }

    @PutMapping("/comment/{id}")
    public CommentDto updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer id) {
        return this.commentService.updateComment(commentDto, id);
    }

    @DeleteMapping("/comment/{id}")
    public Map<String, Object> deleteComment(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.commentService.delteComment(id);
        System.out.println(id);

        response.put("msg", "Comment deleted successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/comment/{id}")
    public CommentDto getSingleComment(@PathVariable Integer id) {
        return this.commentService.getSingleComment(id);
    }

    @GetMapping("/comment/user/{userId}")
    public List<CommentDto> getCommentsByUser(@PathVariable Integer userId) {
        return this.commentService.getCommentsByUser(userId);
    }
}
