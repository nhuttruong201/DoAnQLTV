package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginController {
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @GetMapping("/")
    public String redirectLogin(){
        return"redirect:/login";
    }
    @GetMapping(value = {"/login"})
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
            Model model,
            HttpServletRequest request){
            System.out.println("username = " + username + "\npassword = " + password);

            List<TaiKhoanEntity> taiKhoanEntityList = taiKhoanRepo.findAll();
            for(TaiKhoanEntity i : taiKhoanEntityList){
                if(i.getTentaikhoan().equals(username) && i.getMatkhau().equals(password)){
                //todo: đăng nhập thành công
                @SuppressWarnings("unchecked")
                List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
                if (messages == null) {
                    messages = new ArrayList<>();
                    request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                }   
                // clear để test
                messages.clear();
                messages.add(username);
		        request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                return "redirect:/trang-chu";
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
