package com.example.blogapp.serviceImpl;

import com.example.blogapp.config.UserDetailsClass;
import com.example.blogapp.dto.ReplyDto;
import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Reply;
import com.example.blogapp.entities.Users;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.mappers.ReplyMapper;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.ReplyRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.service.ReplyService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyMapper replyMapper;

    public ReplyServiceImpl(ReplyRepository replyRepository, CommentRepository commentRepository, UserRepository userRepository, ReplyMapper replyMapper) {
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.replyMapper = replyMapper;
    }

    @Override
    public void addReply(ReplyDto replyDto) {
        UserDetailsClass userDetailsClass = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userDetailsClass.getUserId();
        Reply reply = this.replyMapper.toEntity(replyDto);

        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        if (replyDto.getCommentId() != 0) {
            Comment comment = this.commentRepository.findById(replyDto.getCommentId()).orElseThrow(() -> new NotFoundException("Comment"));
            reply.setComment(comment);
        }

        reply.setUser(user);
        this.replyRepository.save(reply);
    }

    @Override
    public List<ReplyDto> getReplies() {
        List<Reply> replies = this.replyRepository.findAll();
        List<ReplyDto> replyDtos = new ArrayList<>();

        replies.forEach((r) -> replyDtos.add(this.replyMapper.toDto(r)));

        return replyDtos;
    }

    @Override
    public ReplyDto updateReply(ReplyDto replyDto, Integer id) {
        Reply reply = this.replyRepository.findById(id).orElseThrow(() -> new NotFoundException("Reply"));

        if (replyDto.getReply() != null) reply.setReply(replyDto.getReply());
        if (replyDto.getCommentId() != 0) {
            Comment comment = this.commentRepository.findById(replyDto.getCommentId()).orElseThrow(() -> new NotFoundException("Comment"));
            reply.setComment(comment);
        }

        reply.setUser(reply.getUser());
        Reply updatedReply = this.replyRepository.save(reply);

        return this.replyMapper.toDto(updatedReply);
    }

    @Override
    public void deleteReply(Integer id) {
        Reply reply = this.replyRepository.findById(id).orElseThrow(() -> new NotFoundException("Reply"));

        this.replyRepository.delete(reply);
    }

    @Override
    public ReplyDto getSingleReply(Integer id) {
        Reply reply = this.replyRepository.findById(id).orElseThrow(() -> new NotFoundException("Reply"));

        return this.replyMapper.toDto(reply);
    }

    @Override
    public void addReplyToReply(ReplyDto replyDto) {
        UserDetailsClass userDetailsClass = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userDetailsClass.getUserId();

        Reply reply = this.replyRepository.findById(replyDto.getReplyId()).orElseThrow(() -> new NotFoundException("Reply not found"));
        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        replyDto.setUser(this.replyMapper.toUserDto(user));
        reply.getReply_replies().add(this.replyMapper.toEntity(replyDto));

        this.replyRepository.save(reply);
    }

    @Override
    public List<ReplyDto> getReplyByUser(Integer userId) {
        Users users = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        return this.replyMapper.toDtoList(this.replyRepository.findByUser(users));
    }
}
