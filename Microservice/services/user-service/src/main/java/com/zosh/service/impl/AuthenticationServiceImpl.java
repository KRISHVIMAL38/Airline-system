package com.zosh.service.impl;

import com.zosh.jwt.JwtService;
import com.zosh.repository.UserRepository;
import com.zosh.dto.UserDTO;
import com.zosh.enums.UserRole;
import com.zosh.mapper.UserMapper;
import com.zosh.model.User;
import com.zosh.payload.response.AuthResponse;
import com.zosh.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

//    1. check if email is already registered
//    2. Encode the password using BCrypt
//    3. Save the User
//    4. Generate the JWT token
//    5. Return token and user informations

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse signUp(UserDTO dto) throws Exception {
        User existingUser = userRepository.findByEmail(dto.getEmail());
        if(existingUser!=null){
            throw new Exception("User already registered");
        }

        if(dto.getRole() == UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("Can't register as system admin");
        }

        User newUser = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        AuthResponse response = UserMapper.toAuthResponse(savedUser);
        response.setTitle("Welcome "+savedUser.getFullName());
        response.setMessage("Register Success");

        return response;
    }


    // 1.load user by mail
    // 2. Compare Password with BCrypt
    // 3. Update last login
    // 4. Generate the JWT Token
    // 5. Return token and user information
    @Override
    public AuthResponse login(String email, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,password)
        );

        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("User not registered");
        }


        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String token=jwtService.generateToken(user);
        AuthResponse response = UserMapper.toAuthResponse(user);
        response.setTitle("Welcome Back"+user.getFullName());
        response.setMessage("Login Success");
        response.setJwtToken(token);
        return response;
    }


}
