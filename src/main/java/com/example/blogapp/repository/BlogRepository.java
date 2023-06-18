package com.example.blogapp.repository;

import com.example.blogapp.entities.Blog;
import com.example.blogapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByUser(Users users);
}
