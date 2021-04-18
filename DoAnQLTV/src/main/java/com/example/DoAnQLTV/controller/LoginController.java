package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;


    @GetMapping(value = {"/", "/login"})
    public String login(){
        System.out.println("Có người đang đăng nhập!");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name="username", required = true, defaultValue = "") String userName,
            @RequestParam(name="password", required = true, defaultValue = "") String passWord){
        System.out.println("username = " + userName + "\npassword = " + passWord);

        List<TaiKhoanEntity> taiKhoanEntityList = taiKhoanRepo.findAll();
        for(TaiKhoanEntity i : taiKhoanEntityList){
            if(i.getTentaikhoan().equals(userName) && i.getMatkhau().equals(passWord)){
                System.out.println("Thành công!");
                // tạm thời return lại trang login
                return "login";
            }
        }

        System.out.println("Thất bại!");
        // tạm thời return lại trang login
        return "login";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }


}
