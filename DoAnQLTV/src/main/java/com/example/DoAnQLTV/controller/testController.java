package com.example.DoAnQLTV.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class testController {
    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("test", "Đây là test");
        return "test";
    }

    @PostMapping("/test")
    public String test(){
        return "test";
    }

}
