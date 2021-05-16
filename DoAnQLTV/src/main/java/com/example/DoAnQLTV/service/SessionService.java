package com.example.DoAnQLTV.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.QuyenHanEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;

import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.This;

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
        // System.out.println("Đang đăng nhập với tài khoản: " + messages);
        // System.out.println("Đã đăng nhập");
        return true;
    }

    public static String getSession(HttpSession session){
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        return messages.get(0);
    }

    public static String getQuyenHan(QuyenHanRepo quyenHanRepo, TaiKhoanRepo taiKhoanRepo, String tentaikhoan){
        TaiKhoanEntity tk = taiKhoanRepo.findByTentaikhoan(tentaikhoan);
        QuyenHanEntity qh = quyenHanRepo.findByMaquyenhan(tk.getMaquyenhan());
        return qh.getTenquyenhan();
    }

    public static String getFullName(TaiKhoanRepo taiKhoanRepo, String tentaikhoan, NhanVienRepo nhanVienRepo){
        TaiKhoanEntity tk = taiKhoanRepo.findByTentaikhoan(tentaikhoan);
        NhanVienEntity nv = nhanVienRepo.findByManhanvien(tk.getManhanvien());
        return nv.getHoten();
    }

    public static Boolean CheckAdmin(HttpSession session, TaiKhoanRepo taiKhoanRepo){
        String tentaikhoan = getSession(session);
        TaiKhoanEntity acTemp = taiKhoanRepo.findByTentaikhoan(tentaikhoan);
        if(acTemp.getMaquyenhan()==1){
            return true;
        }
        return false;
    }

}
