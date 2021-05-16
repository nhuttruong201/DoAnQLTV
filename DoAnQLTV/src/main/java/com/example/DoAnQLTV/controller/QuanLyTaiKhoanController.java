package com.example.DoAnQLTV.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.display.AccountDisplay;
import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuanLyTaiKhoanController {
    
    @Autowired private QuyenHanRepo quyenHanRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private NhanVienRepo nhanVienRepo;

    @GetMapping("/quan-ly-tai-khoan/{type}")
    public String QuanLyTaiKhoan(HttpSession session, Model model, @PathVariable(name = "type") String type){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Quản lý tài khoản");
        model.addAttribute("source", "quan-ly-tai-khoan");
        model.addAttribute("fragment", "quan-ly-tai-khoan");
        model.addAttribute("type", type);

        List<TaiKhoanEntity> listAccount = new ArrayList<TaiKhoanEntity>();
        if(type.equals("tat-ca-tai-khoan")){
            listAccount = taiKhoanRepo.findAll();
        }

        List<AccountDisplay> listAccountDisplay = new ArrayList<AccountDisplay>();
        for(var i : listAccount){
            AccountDisplay temp = new AccountDisplay();
            temp.setId(i.getId());
            temp.setTentaikhoan(i.getTentaikhoan());
            temp.setMatkhau(i.getMatkhau());
            temp.setHoten(i.getHoTen(nhanVienRepo));
            temp.setEmail(i.getEmail());
            temp.setTenquyenhan(i.getTenQuyenHan());
            listAccountDisplay.add(temp);
        }

        Collections.reverse(listAccountDisplay);
        model.addAttribute("listAccount", listAccountDisplay);
        
        return "index";
    }

    // todo: thêm tài khoản
    @GetMapping("/them-tai-khoan")
    public String ThemTaiKhoan(HttpSession session, Model model){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Thêm tài khoản");
        model.addAttribute("source", "them-tai-khoan");
        model.addAttribute("fragment", "them-tai-khoan");
        model.addAttribute("account", new TaiKhoanEntity());
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        Collections.reverse(listNV);
        model.addAttribute("listNV", listNV);
        return "index";
    }

    @PostMapping("/them-tai-khoan")
    public String ThemTaiKhoan(HttpSession session, Model model, @ModelAttribute TaiKhoanEntity newAc){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));                                                            
        model.addAttribute("title", "Thêm tài khoản");
        model.addAttribute("source", "them-tai-khoan");
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        Collections.reverse(listNV);
        model.addAttribute("listNV", listNV);
        // todo: check username và email đã sử dụng chưa
        List<TaiKhoanEntity> listAc = taiKhoanRepo.findAll();
        for(var i : listAc){
            if(i.getTentaikhoan().equals(newAc.getTentaikhoan())){
                model.addAttribute("account", newAc);
                model.addAttribute("fragment", "username-da-su-dung");
                return "index";
            }
            if(i.getEmail().equals(newAc.getEmail())){
                model.addAttribute("account", newAc);
                model.addAttribute("fragment", "email-da-su-dung");
                return "index";
            }
        }
        taiKhoanRepo.save(newAc);
        model.addAttribute("fragment", "them-tai-khoan-thanh-cong");
        model.addAttribute("account", new TaiKhoanEntity());
        return "index";
    }

    // todo: Sửa tài khoản
    @GetMapping("/edit-account/{id}")
    public String EditAccount(HttpSession session, Model model, @PathVariable(name = "id") int id){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));                                                            
        model.addAttribute("title", "Sửa tài khoản");
        model.addAttribute("source", "edit-account");
        model.addAttribute("fragment", "sua-tai-khoan");
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        Collections.reverse(listNV);
        model.addAttribute("listNV", listNV);

        // todo: tìm thông tin tài khoản cần sửa
        TaiKhoanEntity acEdit = taiKhoanRepo.findById(id);
        model.addAttribute("account", acEdit);

        return "index";
    }

    @PostMapping("/edit-account")
    public String EditAccount(HttpSession session, Model model, @ModelAttribute TaiKhoanEntity acEdit){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));                                                            
        model.addAttribute("title", "Sửa tài khoản");
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        Collections.reverse(listNV);
        model.addAttribute("listNV", listNV);

        // todo: check username và email đã sử dụng chưa
        List<TaiKhoanEntity> listAc = taiKhoanRepo.findAll();
        for(var i : listAc){
            if(i.getId()!=acEdit.getId()){
                if(i.getTentaikhoan().equals(acEdit.getTentaikhoan())){
                    model.addAttribute("source", "edit-account");
                    model.addAttribute("account", acEdit);
                    model.addAttribute("fragment", "username-da-su-dung");
                    return "index";
                }
                if(i.getEmail().equals(acEdit.getEmail())){
                    model.addAttribute("source", "edit-account");
                    model.addAttribute("account", acEdit);
                    model.addAttribute("fragment", "email-da-su-dung");
                    return "index";
                }
            }
        }
        taiKhoanRepo.save(acEdit);
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Cập nhật thông tin tài khoản thành công!");
        model.addAttribute("linkBack", "/quan-ly-tai-khoan/tat-ca-tai-khoan");
        return "index";
    }

}
