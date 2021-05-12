package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {
    @Autowired 
    MailService mailService;


    @PostMapping("/send-mail")
    public void sendMail(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "content") String content,
        @RequestParam(name = "address") String address){
        mailService.sendMail(name, content, address);
    }
    
}
