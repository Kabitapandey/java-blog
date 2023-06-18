package com.example.blogapp.serviceImpl;

import com.example.blogapp.config.UserDetailsClass;
import com.example.blogapp.dto.BlogDto;
import com.example.blogapp.entities.Blog;
import com.example.blogapp.entities.Users;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.mappers.BlogMapper;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.service.BlogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    private final BlogMapper blogMapper;


    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
    }

    @Override
    public void addABlog(BlogDto blogDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsClass userDetails = (UserDetailsClass) authentication.getPrincipal();
        int userId = userDetails.getUserId();

        Blog blog = this.blogMapper.toEntity(blogDto);
        Date date = new Date();

        blog.setUser(blog.getUser());
        blog.setDate(date);

        this.blogRepository.save(blog);
    }

    @Override
    public List<BlogDto> getBlogs() {
        List<Blog> blogs = this.blogRepository.findAll();

        return this.blogMapper.blogToList(blogs);
    }

    @Override
    public BlogDto updateBlog(BlogDto blogDto, Integer id) {
        Blog blogs = this.blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog"));

        if (blogDto.getTitle() != null) blogs.setTitle(blogDto.getTitle());
        if (blogDto.getDescription() != null) blogs.setDescription(blogDto.getDescription());
        if (blogDto.getUserId() != 0) {
            Users user = this.userRepository.findById(blogDto.getUserId()).orElseThrow(() -> new NotFoundException("User"));
            blogs.setUser(user);
        }

        Blog updatedBlog = this.blogRepository.save(blogs);

        return this.blogMapper.toDto(updatedBlog);
    }

    @Override
    public void deletedBlog(Integer id) {
        Blog blog = this.blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog"));

        this.blogRepository.delete(blog);
    }

    @Override
    public BlogDto getSingleBlog(Integer id) {
        Blog blog = this.blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog"));

        return this.blogMapper.toDto(blog);
    }

    @Override
    public List<BlogDto> getBlogByUser(Integer userId) {
        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<Blog> blog = this.blogRepository.findByUser(user);

        return this.blogMapper.blogToList(blog);
    }
}
