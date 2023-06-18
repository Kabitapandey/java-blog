package com.example.blogapp.controller;


import com.example.blogapp.dto.ReplyDto;
import com.example.blogapp.service.ReplyService;
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
    public List<ReplyDto> getAllReply() {
        return this.replyService.getReplies();
    }

    @PutMapping("/reply/{id}")
    public ReplyDto updateReply(@RequestBody ReplyDto replyDto, @PathVariable Integer id) {
        return this.replyService.updateReply(replyDto, id);
    }

    @DeleteMapping("/reply/{id}")
    public Map<String, Object> deleteReply(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.replyService.deleteReply(id);

        response.put("msg", "Reply deleted successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping("/reply/{id}")
    public ReplyDto getSingleReply(@PathVariable Integer id) {
        return this.replyService.getSingleReply(id);
    }

    @PostMapping("/reply/reply")
    public Map<String, Object> addReplyToReply(@RequestBody ReplyDto replyDto) {
        Map<String, Object> response = new HashMap<>();

        this.replyService.addReplyToReply(replyDto);

        response.put("msg", "Reply added successfully");
        response.put("success", true);

        return response;
    }

    @GetMapping(value = "/reply/user/{userId}")
    List<ReplyDto> getReplyByUser(@PathVariable Integer userId) {
        return this.replyService.getReplyByUser(userId);
    }
}
