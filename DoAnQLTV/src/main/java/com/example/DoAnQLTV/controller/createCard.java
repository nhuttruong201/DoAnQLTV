package com.example.DoAnQLTV.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class createCard {
    

    @GetMapping("/create-library-card")
    public String createLibraryCard(){
        return "create-library-card";
    }

}
