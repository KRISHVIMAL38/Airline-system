package com.zosh.service.impl;

import com.zosh.dto.UserDTO;
import com.zosh.mapper.UserMapper;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found with email");
        }

        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getUserById(Long Id) throws Exception {
        User user = userRepository.findById(Id)
                .orElseThrow(()->new Exception("User not found with given id"));

        return UserMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users =  userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for(User user:users){
            dtos.add(UserMapper.toUserDTO(user));
        }
        return dtos;
    }
}
