package com.example.blogapp.controller;

import com.example.blogapp.config.UserDetailsClass;
import com.example.blogapp.dto.AuthRequest;
import com.example.blogapp.dto.UserDto;
import com.example.blogapp.service.JwtService;
import com.example.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/user/add")
    public Map<String, Object> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((err) -> {
                String field = ((FieldError) err).getField();
                String msg = err.getDefaultMessage();

                response.put(field, msg);
            });

            return response;
        }

        this.userService.addUser(userDto);
        response.put("msg", "User added successfully");
        response.put("success", true);
        return response;
    }

    @GetMapping("/user")
    public List<UserDto> getAllUsers() {
        return this.userService.getUser();
    }

    @PutMapping("/user/{id}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Integer id) {
        return this.userService.updateUser(userDto, id);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Map<String, Object> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.userService.deleteUser(id);
        response.put("msg", "User deleted successfully");
        response.put("success", true);
        return response;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDto getSingleUser(@PathVariable Integer id) {
        return this.userService.getSingleUser(id);
    }

    @PostMapping(value = "/user/authenticate")
    public Map<String, Object> authenticate(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            response.put("token", token);
            response.put("success", true);
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }

        return response;
    }

    @GetMapping("/users")
    public UserDetailsClass getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsClass users = (UserDetailsClass) authentication.getPrincipal();

        return users;
    }
}
