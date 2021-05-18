package com.example.DoAnQLTV.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.entity.TrangThaiTheEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.repository.TrangThaiTheRepo;
import com.example.DoAnQLTV.service.CardBorrow;
import com.example.DoAnQLTV.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuanLyTheController {
    @Autowired
    private TheThuVienRepo theThuVienRepo;
    @Autowired
    private PhieuMuonRepo phieuMuonRepo;
    @Autowired private QuyenHanRepo quyenHanRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private NhanVienRepo nhanVienRepo;
    @Autowired private TrangThaiTheRepo trangThaiTheRepo;

    // todo: Quản lý thẻ - Thống kê
    @GetMapping("/quan-ly-the-thu-vien/{type}")
    public String QuanLyTheThuVien(Model model, @PathVariable(name = "type") String type, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Quản lý thẻ thư viện");
        model.addAttribute("source", "quan-ly-the-thu-vien");
        model.addAttribute("fragment", "quan-ly-the-thu-vien");
        model.addAttribute("type", type);

        List<TheThuVienEntity> listCard = new ArrayList<TheThuVienEntity>();
        List<CardBorrow> listCardBorrows = new ArrayList<CardBorrow>();
        if (type.equals("tat-ca-the")) {
            // todo: tât cả thẻ
            listCard = theThuVienRepo.findAll();
        } else if (type.equals("the-bi-khoa")) {
            // todo: thẻ bị khóa
            listCard = theThuVienRepo.findByMatrangthai("lock");
        } else if (type.equals("the-dang-muon")) {
            // todo: Thẻ đang mượn
            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
            for (PhieuMuonEntity i : listBill) {
                CardBorrow temp = new CardBorrow();
                temp.setMathe(i.getMathe());
                temp.setHoten(i.getHoTenDocGia(theThuVienRepo, i.getMathe()));
                temp.setMaphieumuon(i.getMaphieumuon());
                temp.setNgaymuon(i.getNgaymuon());
                temp.setHantra(i.getHantra());
                listCardBorrows.add(temp);
            }
        } else if(type.equals("the-het-han")){
            LocalDate today = LocalDate.now();
            List<TheThuVienEntity> listAllCard = theThuVienRepo.findAll();
            for(var i : listAllCard){
                String hansudung = i.getHansudung().toString();
                if(LocalDate.parse(hansudung).compareTo(today)<0){
                    listCard.add(i);
                }
            }
        }
        Collections.reverse(listCard);
        model.addAttribute("listCard", listCard);
        model.addAttribute("listCardBorrow", listCardBorrows);
        return "index";
    }

    // todo: Làm thẻ thư viện
    @GetMapping("/lam-the-thu-vien")
    public String LamTheThuVien(Model model, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Làm thẻ thư viện");
        model.addAttribute("source", "lam-the-thu-vien");
        model.addAttribute("fragment", "lam-the-thu-vien");
        model.addAttribute("card", new TheThuVienEntity());
        return "index";
    }

    //todo: làm thẻ có truyền sdt sẵn
    @GetMapping("/lam-the-thu-vien/{sdt}")
    public String LamTheThuVien(Model model, @PathVariable(name = "sdt") String sdt, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Làm thẻ thư viện");
        model.addAttribute("source", "lam-the-thu-vien");
        model.addAttribute("fragment", "lam-the-thu-vien");
        TheThuVienEntity card = new TheThuVienEntity();
        card.setSodienthoai(sdt);
        model.addAttribute("card", card);
        return "index";
    }

    @PostMapping("/lam-the-thu-vien")
    public String LamTheThuVien(Model model, @ModelAttribute TheThuVienEntity card, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));

        model.addAttribute("title", "Làm thẻ thư viện");
        model.addAttribute("source", "lam-the-thu-vien");
        //todo: Kiểm tra sdt đã được sử dụng chưa
        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        for(var i : listCard){
            if(i.getSodienthoai().equals(card.getSodienthoai())){
                // Nếu sdt đã được sử dụng
                model.addAttribute("fragment", "sdt-da-su-dung");
                model.addAttribute("card", card);
                return "index";
            }
        }
        //todo: Xử lý dữ liệu nếu sdt hợp lệ
        card.setMatrangthai("open");
        theThuVienRepo.save(card);
        
        model.addAttribute("fragment", "lam-the-thanh-cong");
        model.addAttribute("card", new TheThuVienEntity());
        
        return "index";
    }

    // todo: Sửa thẻ thư viện
    @GetMapping("/edit-card/{id}")
    public String EditCard(Model model, @PathVariable(name = "id") int mathe, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Sửa thẻ thư viện");
        model.addAttribute("source", "edit-card");
        model.addAttribute("fragment", "sua-the-thu-vien");
        
        TheThuVienEntity cardEdit = theThuVienRepo.findByMathe(mathe);
        model.addAttribute("card", cardEdit);
        List<TrangThaiTheEntity> listStatus = trangThaiTheRepo.findAll();
        model.addAttribute("listStatus", listStatus);
        return "index";
    }

    @PostMapping("/edit-card")
    public String EditCard(Model model, @ModelAttribute TheThuVienEntity cardEdit, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        //todo: Kiểm tra sdt đã được sử dụng chưa
        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        for(var i : listCard){
            if(i.getMathe()!=cardEdit.getMathe()){
                if(i.getSodienthoai().equals(cardEdit.getSodienthoai())){
                    // Nếu sdt đã được sử dụng
                    model.addAttribute("title", "Sửa thẻ thư viện");
                    model.addAttribute("source", "edit-card");
                    model.addAttribute("fragment", "sdt-da-su-dung");
                    model.addAttribute("card", cardEdit);
                    return "index";
                }
            }
        }
        theThuVienRepo.save(cardEdit);
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Cập nhật thẻ thư viện thành công!");
        model.addAttribute("linkBack", "/quan-ly-the-thu-vien/tat-ca-the");
        return "index";
    }

    // todo: Tìm thẻ
    @PostMapping("/tim-the-thu-vien")
    public String TimTheThuVien(Model model, @RequestParam(name = "mathe", defaultValue = "") String mathe,
            @RequestParam(name = "hoten", defaultValue = "") String hoten,
            @RequestParam(name = "sdt", defaultValue = "") String sdt, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Tìm thẻ thư viện");
        model.addAttribute("source", "tim-the-thu-vien");
        model.addAttribute("fragment", "tim-the-thu-vien");

        List<TheThuVienEntity> listCard = new ArrayList<TheThuVienEntity>();
        if (mathe.equals("") && hoten.equals("") && sdt.equals("")) {
            listCard = theThuVienRepo.findAll();
        }else{
            if(mathe.equals("")) {
                listCard = theThuVienRepo.findByHotenLikeAndSodienthoaiLike("%"+hoten+"%", "%"+sdt+"%");
            }else{
                listCard = theThuVienRepo.findByHotenLikeAndSodienthoaiLikeAndMathe("%"+hoten+"%", "%"+sdt+"%", Integer.parseInt(mathe));
            }
        }
        Collections.reverse(listCard);
        model.addAttribute("listCard", listCard);
        model.addAttribute("size", listCard.size());
        model.addAttribute("mathe", mathe);
        model.addAttribute("hoten", hoten);
        model.addAttribute("sdt", sdt);
        return "index";
    }
}
