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
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @GetMapping("/")
    public String redirectLogin() {
        return "redirect:/login";
    }

    @GetMapping(value = { "/login" })
    public String login(Model model) {
        System.out.println("Có người đang đăng nhập!");
        model.addAttribute("title", "Đăng nhập");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "login");
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model,
            @RequestParam(name = "username", required = true, defaultValue = "") String username,
            @RequestParam(name = "password", required = true, defaultValue = "") String password) {
        System.out.println("username = " + username + "\npassword = " + password);
        List<TaiKhoanEntity> taiKhoanEntityList = taiKhoanRepo.findAll();
        for (TaiKhoanEntity i : taiKhoanEntityList) {
            if (i.getTentaikhoan().equals(username) && i.getMatkhau().equals(password)) {
                // todo: check tài khoản bí khóa
                TaiKhoanEntity acCheck = taiKhoanRepo.findByTentaikhoan(username);
                if (acCheck.getStatus().equals("ON")) {
                    // todo: đăng nhập thành công
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
                } else {
                    // todo: tài khoản bị khóa
                    model.addAttribute("alert", "Tài khoản của bạn đã bị khóa!");
                    break;
                }

            }else{
                //todo: tài khoản hoặc mật khẩu không chính xác
                model.addAttribute("alert", "Tài khoản hoặc mật khẩu không chính xác!");
            }
        }
        //todo: thất bại
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("title", "Đăng nhập");
        model.addAttribute("source", "fragment-login");
        model.addAttribute("fragment", "login-fail");
        return "login";

    }

}
