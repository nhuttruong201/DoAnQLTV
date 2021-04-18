package com.example.DoAnQLTV.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class forgetPasswordController {
    @GetMapping("/forget-password")
    public String forgetPassword(){
        return "forget-password";
    }
}
