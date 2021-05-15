package com.example.DoAnQLTV.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public static Boolean CheckLogin(HttpSession session){
        //todo: check login - note: truyền HttpSession session
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
            // System.out.println("Chưa đăng nhập");
            return false;
        }
        // System.out.println("Đã đăng nhập");
        return true;
    }
}
