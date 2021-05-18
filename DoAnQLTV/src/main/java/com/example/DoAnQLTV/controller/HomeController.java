package com.example.DoAnQLTV.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.service.BookBorrow;
import com.example.DoAnQLTV.service.SessionService;
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
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private ChiTietPhieuMuonRepo ctpmRepo;
    @Autowired private QuyenHanRepo quyenHanRepo;
    
    // Trang chủ
    @GetMapping("/trang-chu")
    public String Home(Model model, HttpSession session){
        //todo: check login - note: truyền HttpSession session
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }
        //todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Trang chủ");
        model.addAttribute("source", "home");
        model.addAttribute("fragment", "trang-chu");
        
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

        //todo: phiếu chưa thanh toán
        List<PhieuMuonEntity> listBillUnpaid = phieuMuonRepo.findByTrangthai(1);
        model.addAttribute("listBillUnpaid", listBillUnpaid);
        model.addAttribute("sizeBillUnpaid", listBillUnpaid.size());

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
        int sumBookBorrow = 0;
        for(var i : listBookBorrows){
            sumBookBorrow+=i.getSoluong();
        }
        model.addAttribute("sizeBookBorow", sumBookBorrow);

        return "index";
    }
}
