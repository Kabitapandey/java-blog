package com.example.blogapp.repository;

import com.example.blogapp.entities.Reply;
import com.example.blogapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findByUser(Users users);
}
