package com.zosh.controller;

import com.zosh.dto.UserDTO;
import com.zosh.model.User;
import com.zosh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("X-User-Email") String email) throws Exception {
        UserDTO dto = userService.getUserByEmail(email);
        return  ResponseEntity.ok(dto);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long Id) throws Exception {
        UserDTO dto = userService.getUserById(Id);
        return  ResponseEntity.ok(dto);
    }
}
