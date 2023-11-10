package com.example.seo_dot.user.controller;

import com.example.seo_dot.user.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/test")
    public String getUser() {
        return "Hello World";
    }
}
