package com.zosh.service;

import com.zosh.dto.UserDTO;
import com.zosh.model.User;

import java.util.List;

public interface UserService {
     UserDTO getUserByEmail(String email) throws Exception;
     UserDTO getUserById(Long Id) throws Exception;
     List<UserDTO> getAllUsers();
}
