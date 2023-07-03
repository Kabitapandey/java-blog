package com.example.blogapp.controller;


import com.example.blogapp.dto.ReplyDto;
import com.example.blogapp.service.ReplyService;
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
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply")
    @Operation(
            description = "Endpoint for adding reply",
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
    public Map<String, Object> addReply(@Valid @RequestBody ReplyDto replyDto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((err) -> {
                String field = ((FieldError) err).getField();
                String msg = err.getDefaultMessage();

                response.put(field, msg);
            });
            return response;
        }

        this.replyService.addReply(replyDto);

        response.put("msg", "Reply added successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/reply")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            description = "Endpoint for listing all replies",
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
    public List<ReplyDto> getAllReply() {
        return this.replyService.getReplies();
    }

    @PutMapping("/reply/{id}")
    @Operation(
            description = "Endpoint for updating a reply",
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
    public ReplyDto updateReply(@RequestBody ReplyDto replyDto, @PathVariable Integer id) {
        return this.replyService.updateReply(replyDto, id);
    }

    @DeleteMapping("/reply/{id}")
    @Operation(
            description = "Endpoint for deleting a reply",
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
    public Map<String, Object> deleteReply(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.replyService.deleteReply(id);

        response.put("msg", "Reply deleted successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/reply/{id}")
    @Operation(
            description = "Endpoint for listing a single reply",
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
    public ReplyDto getSingleReply(@PathVariable Integer id) {
        return this.replyService.getSingleReply(id);
    }

    @PostMapping("/reply/reply")
    @Operation(
            description = "Endpoint for adding a reply to a reply",
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
    public Map<String, Object> addReplyToReply(@RequestBody ReplyDto replyDto) {
        Map<String, Object> response = new HashMap<>();

        this.replyService.addReplyToReply(replyDto);

        response.put("msg", "Reply added successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping(value = "/reply/user/{userId}")
    @Operation(
            description = "Endpoint for updating a reply",
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
    List<ReplyDto> getReplyByUser(@PathVariable Integer userId) {
        return this.replyService.getReplyByUser(userId);
    }
}
