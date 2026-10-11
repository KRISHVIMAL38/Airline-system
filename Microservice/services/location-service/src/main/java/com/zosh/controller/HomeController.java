package com.zosh.controller;

import com.zosh.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    @GetMapping("/home")
    public ApiResponse homeController(){
        ApiResponse response = new ApiResponse();
        response.setMessage("Jai Shree Ram");
        return response;
    }
}
