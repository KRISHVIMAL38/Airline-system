package com.zosh.mapper;

import com.zosh.dto.UserDTO;
import com.zosh.model.User;
import com.zosh.payload.response.AuthResponse;

public class UserMapper {
    public static AuthResponse toAuthResponse(User user){
        AuthResponse response = new AuthResponse();
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin()).build();

        response.setUserDTO(dto);
        return response;
    }

    public static UserDTO toUserDTO(User user){
        //UserDTO dto = new UserDTO();
        UserDTO dto = UserDTO.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin()).build();

        return dto;
    }
}
