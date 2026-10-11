package com.zosh.payload.response;

import com.zosh.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwtToken;
    private String message;
    private String title;
    private UserDTO userDTO;
}
