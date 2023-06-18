package com.example.blogapp.config;

import com.example.blogapp.entities.Users;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceClass implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = this.userRepository.findByEmail(username);

        return user.map(UserDetailsClass::new).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
