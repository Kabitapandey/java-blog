package com.example.blogapp.service;

import com.example.blogapp.dto.ReplyDto;

import java.util.List;

public interface ReplyService {
    void addReply(ReplyDto replyDto);

    List<ReplyDto> getReplies();

    ReplyDto updateReply(ReplyDto replyDto, Integer id);

    void deleteReply(Integer id);

    ReplyDto getSingleReply(Integer id);

    void addReplyToReply(ReplyDto replyDto);

    List<ReplyDto> getReplyByUser(Integer userId);
}
