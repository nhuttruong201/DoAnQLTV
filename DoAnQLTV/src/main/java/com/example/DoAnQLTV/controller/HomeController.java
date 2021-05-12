package com.example.DoAnQLTV.controller;

import java.util.List;
import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired private SachRepo sachRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;
    @Autowired private NhanVienRepo nhanVienRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    
    // Trang chủ
    @GetMapping("/trang-chu")
    public String Home(Model model){
        model.addAttribute("source", "home");
        model.addAttribute("fragment", "trang-chu");
        model.addAttribute("title", "Trang chủ");

        List<SachEntity> listBook = sachRepo.findAll();
        int sl = 0;
        for(SachEntity i : listBook){
            sl += i.getSoluong();
        }
        model.addAttribute("listBookSize", sl);
        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        model.addAttribute("listCardSize", listCard.size());
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        model.addAttribute("listNvSize", listNV.size());
        List<TaiKhoanEntity> listAc = taiKhoanRepo.findAll();
        model.addAttribute("listAcSize", listAc.size());
        return "index";
    }
}
