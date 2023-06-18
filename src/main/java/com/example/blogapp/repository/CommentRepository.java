package com.example.blogapp.repository;

import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(Users users);
}
