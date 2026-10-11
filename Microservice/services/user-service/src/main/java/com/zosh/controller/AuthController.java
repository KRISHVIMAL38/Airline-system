package com.zosh.controller;

import com.zosh.dto.UserDTO;
import com.zosh.payload.request.LoginRequest;
import com.zosh.payload.response.AuthResponse;
import com.zosh.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerNormalUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(authenticationService.signUp(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest req) throws Exception {
        return ResponseEntity.ok(authenticationService.login(req.getEmail(),req.getPassword()));
    }
}
