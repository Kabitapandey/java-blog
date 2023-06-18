package com.example.blogapp.serviceImpl;

import com.example.blogapp.config.UserDetailsClass;
import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.entities.Blog;
import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Users;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.mappers.CommentMapper;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.service.CommentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    private final CommentMapper commentMapper;


    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public void addComment(CommentDto commentDto) {
        UserDetailsClass userDetailsClass = (UserDetailsClass) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userDetailsClass.getUserId();

        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        Blog blog = this.blogRepository.findById(commentDto.getBlogId()).orElseThrow(() -> new NotFoundException("Blog"));

        Comment comment = this.commentMapper.toEntity(commentDto);
        comment.setUser(user);
        comment.setBlog(blog);

        this.commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getComments() {
        List<Comment> comments = this.commentRepository.findAll();

        return this.commentMapper.toDtoList(comments);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer id) {
        Comment comments = this.commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment"));

        comments.setUser(comments.getUser());
        if (commentDto.getComment() != null) comments.setComment(commentDto.getComment());
        if (commentDto.getBlogId() != 0) {
            Blog blog = this.blogRepository.findById(commentDto.getBlogId()).orElseThrow(() -> new NotFoundException("Blog"));
            comments.setBlog(blog);
        }

        Comment updatedComment = this.commentRepository.save(comments);

        return this.commentMapper.toDto(updatedComment);
    }

    @Override
    public void delteComment(Integer id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment"));

        this.commentRepository.delete(comment);
    }

    @Override
    public CommentDto getSingleComment(Integer id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment"));

        return this.commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByUser(Integer userId) {
        Users users = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        return this.commentMapper.toDtoList(this.commentRepository.findByUser(users));
    }
}
