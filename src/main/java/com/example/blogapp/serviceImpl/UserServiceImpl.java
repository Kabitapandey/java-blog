package com.example.blogapp.serviceImpl;

import com.example.blogapp.dto.UserDto;
import com.example.blogapp.entities.Address;
import com.example.blogapp.entities.Users;
import com.example.blogapp.exception.EmailExistsException;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.mappers.UserMapper;
import com.example.blogapp.repository.AddressRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
    public void addUser(UserDto userDto) {
        Users user = this.userMapper.toEntity(userDto);
        Optional<Users> getUser = this.userRepository.findByEmail(userDto.getEmail());

        if (!getUser.isEmpty()) {
            throw new EmailExistsException("Email already taken");
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles("ROLE_USER");

        List<Address> address = this.addressRepository.findAllById(userDto.getAddressId());
        user.setAddress(address);
        this.userRepository.save(user);
    }

    @Override
    public List<UserDto> getUser() {
        List<Users> users = this.userRepository.findAll();

        return this.userMapper.userDtoList(users);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        Users users = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));

        if (userDto.getFirstName() != null) users.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) users.setLastName(userDto.getLastName());
        if (userDto.getPassword() != null) users.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoles() != null) users.setRoles(userDto.getRoles());
        if (userDto.getEmail() != null) users.setEmail(userDto.getEmail());
        if (userDto.getAddressId() != null) {
            List<Address> address = this.addressRepository.findAllById(userDto.getAddressId());
            users.setAddress(address);
        }

        Users updatedUser = this.userRepository.save(users);

        return this.userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        Users user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));

        this.userRepository.delete(user);
    }

    @Override
    public UserDto getSingleUser(Integer id) {
        Users user = this.userRepository.findById(id
        ).orElseThrow(() -> new NotFoundException("User"));

        return this.userMapper.toDto(user);
    }
}
