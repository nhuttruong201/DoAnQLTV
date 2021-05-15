package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgetPasswordController {
    
    @Autowired 
    MailService mailService;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private NhanVienRepo nhanVienRepo;

    @GetMapping("/forget-password")
    public String forgetPassword(Model model){
        model.addAttribute("title", "Quên mật khẩu");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "forget-password");
        return "login";
    }

    @PostMapping("/forget-password")
    public String forgetPassword(Model model,
        @RequestParam(name = "email") String email){
        
        TaiKhoanEntity acount = taiKhoanRepo.findByEmail(email);
        NhanVienEntity temp = nhanVienRepo.findByManhanvien(acount.getManhanvien());
        
        String name = temp.getHoten();
        String content = acount.getMatkhau();
        
        mailService.sendMail(name, content, email);
        model.addAttribute("title", "Quên mật khẩu");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "get-pass-success");
        model.addAttribute("email", email);
        return "login";
    }
}
