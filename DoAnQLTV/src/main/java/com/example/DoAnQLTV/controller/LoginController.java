package com.example.DoAnQLTV.controller;

import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.service.BookBorrow;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired private SachRepo sachRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;
    @Autowired private NhanVienRepo nhanVienRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private ChiTietPhieuMuonRepo ctpmRepo;


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
                //todo: đăng nhập thành công
                model.addAttribute("title", "Trang chủ");
                model.addAttribute("source", "home");
                model.addAttribute("fragment", "trang-chu");
                List<SachEntity> listBook = sachRepo.findAll();
                int sl = 0;
                for(SachEntity book : listBook){
                    sl += book.getSoluong();
                }
                model.addAttribute("listBookSize", sl);
                List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
                model.addAttribute("listCardSize", listCard.size());
                List<NhanVienEntity> listNV = nhanVienRepo.findAll();
                model.addAttribute("listNvSize", listNV.size());
                List<TaiKhoanEntity> listAc = taiKhoanRepo.findAll();
                model.addAttribute("listAcSize", listAc.size());

                //todo: phiếu chưa thanh toán
                List<PhieuMuonEntity> listBillUnpaid = phieuMuonRepo.findByTrangthai(1);
                model.addAttribute("listBillUnpaid", listBillUnpaid);

                //todo: sách đang mượn
                List<BookBorrow> listBookBorrows = new ArrayList<BookBorrow>();
                List<ChiTietPhieuMuonEntity> listCTPM = ctpmRepo.findAll();
                List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
                for(PhieuMuonEntity bill : listBill){
                    for(ChiTietPhieuMuonEntity billDetail : listCTPM){
                        if(bill.getMaphieumuon() == billDetail.getMaphieumuon()){
                            BookBorrow temp = new BookBorrow();
                            temp.setMaphieumuon(billDetail.getMaphieumuon());
                            temp.setMasach(billDetail.getMasach());
                            temp.setTensach(temp.getTenSachBaseMaSach(sachRepo, billDetail.getMasach()));
                            temp.setSoluong(billDetail.getSoluong());
                            temp.setTendocgia(temp.getTenDocGiaBaseMaPhieuMuon(theThuVienRepo, temp.getMaTheBaseMaPhieuMuon(phieuMuonRepo, billDetail.getMaphieumuon())));
                            temp.setMadocgia(temp.getMaDocGiaBaseMaPhieuMuon(theThuVienRepo, temp.getMaTheBaseMaPhieuMuon(phieuMuonRepo, billDetail.getMaphieumuon())));
                            listBookBorrows.add(temp);
                        }
                    }
                }
                model.addAttribute("listBookBorrow", listBookBorrows);
                model.addAttribute("sizeBillUnpaid", listBillUnpaid.size());
                model.addAttribute("sizeBookBorow", listBookBorrows.size());
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
