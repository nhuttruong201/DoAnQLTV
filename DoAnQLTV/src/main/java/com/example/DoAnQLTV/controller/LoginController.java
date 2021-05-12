package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;


    @GetMapping(value = {"/", "/login"})
    public String login(Model model){
        System.out.println("Có người đang đăng nhập!");
        model.addAttribute("title", "Đăng nhập");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "login");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name="username", required = true, defaultValue = "") String username,
            @RequestParam(name="password", required = true, defaultValue = "") String password,
            Model model){
        System.out.println("username = " + username + "\npassword = " + password);

        List<TaiKhoanEntity> taiKhoanEntityList = taiKhoanRepo.findAll();
        for(TaiKhoanEntity i : taiKhoanEntityList){
            if(i.getTentaikhoan().equals(username) && i.getMatkhau().equals(password)){
                // thành công
                model.addAttribute("title", "Trang chủ");
                model.addAttribute("source", "home");
                model.addAttribute("fragment", "trang-chu");
                return "index";
            }
        }
        // thất bại
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("title", "Đăng nhập");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "login-fail");
        return "login";

    }



}
