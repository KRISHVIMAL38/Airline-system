package com.zosh.service;

import com.zosh.dto.UserDTO;
import com.zosh.payload.response.AuthResponse;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signUp(UserDTO dto) throws Exception;
}
