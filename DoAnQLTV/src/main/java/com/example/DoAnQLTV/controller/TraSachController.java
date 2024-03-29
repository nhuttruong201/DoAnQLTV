package com.example.DoAnQLTV.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TraSachController {
    @Autowired
    private SachRepo sachRepo;
    @Autowired
    private TheThuVienRepo theThuVienRepo;
    @Autowired
    private PhieuMuonRepo phieuMuonRepo;
    @Autowired
    private ChiTietPhieuMuonRepo chiTietPhieuMuonRepo;
    @Autowired private QuyenHanRepo quyenHanRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private NhanVienRepo nhanVienRepo;
 
    // todo: Trả sách - Kiểm tra thẻ
    @GetMapping("/tra-sach")
    public String TraSach(Model model, HttpSession session) {
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("source", "tra-sach");
        model.addAttribute("fragment", "kiem-tra-the");
        model.addAttribute("title", "Yêu cầu trả sách");
        

        List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
        List<Integer> arrIdCardOnly = new ArrayList<Integer>();
        // todo: loại bỏ mã thẻ trùng nhau
        for (int i = 0; i < listBill.size(); i++) {
            if (!arrIdCardOnly.contains(listBill.get(i).getMathe())) {
                arrIdCardOnly.add(listBill.get(i).getMathe());
            }
        }
        model.addAttribute("listIdCardBorrow", arrIdCardOnly);
        return "index";
    }

    @PostMapping("/tra-sach")
    public String TraSach(HttpSession session, Model model, 
        @RequestParam(name = "mathe", defaultValue = "") String mathe,
        @RequestParam(name = "sdt", defaultValue = "") String sdt) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("source", "tra-sach");
        model.addAttribute("title", "Yâu cầu trả sách");
        model.addAttribute("mathe", mathe);
        model.addAttribute("sdt", sdt);

        TheThuVienEntity cardCheck = new TheThuVienEntity();
        // todo: check rỗng submit
        if (mathe.equals("") && sdt.equals("")) {
            return "redirect:/tra-sach";
        } 
        else if(!mathe.equals("") && !sdt.equals("")){
            cardCheck = theThuVienRepo.findByMatheAndSodienthoai(Integer.parseInt(mathe), sdt);
        }
        else{
            if(mathe.equals("")){
                cardCheck = theThuVienRepo.findBySodienthoai(sdt);
            }else{
                cardCheck = theThuVienRepo.findByMathe(Integer.parseInt(mathe));
            }
        }
        // todo: check thẻ không tồn tại
        if(cardCheck == null){
            model.addAttribute("fragment", "check-fail");
            model.addAttribute("titleAlert", "Thẻ không tồn tại!");
            model.addAttribute("alert", "Vui lòng nhập mã thẻ hoặc số điện thoại khác để check!");
            return "index";
        }
        // todo: check thẻ có mượn không
        List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(cardCheck.getMathe(), 1);
        if(listBill.size()==0){
            model.addAttribute("fragment", "check-fail");
            model.addAttribute("titleAlert", "Thẻ hiện không mượn sách!");
            model.addAttribute("alert", "Vui lập nhập mã thẻ hoặc số điện thoại khác để check!");
            return "index";
        }
        //todo: Check thẻ thành công
        model.addAttribute("mathe", cardCheck.getMathe());
        model.addAttribute("hoten", cardCheck.getHoten());
        model.addAttribute("sdt", cardCheck.getSodienthoai());
        model.addAttribute("fragment", "tra-sach");
        // phục vụ check ngày trả phải lớn hơn ngaỳ hiện tại
        LocalDate toDay = LocalDate.now();
        model.addAttribute("toDay", toDay);

        List<BookBorrow> listBookBorrows = new ArrayList<BookBorrow>();
        for (var bill : listBill) {
            List<ChiTietPhieuMuonEntity> listCTPM = chiTietPhieuMuonRepo.findByMaphieumuon(bill.getMaphieumuon());
            for (var temp : listCTPM) {
                BookBorrow tempBookBorrow = new BookBorrow();
                tempBookBorrow.setMaphieumuon(temp.getMaphieumuon());
                tempBookBorrow.setMasach(temp.getMasach());
                tempBookBorrow.setTensach(tempBookBorrow.getTenSachBaseMaSach(sachRepo, temp.getMasach()));
                tempBookBorrow.setSoluong(temp.getSoluong());

                listBookBorrows.add(tempBookBorrow);
            }
        }
        

        model.addAttribute("listBill", listBill);
        model.addAttribute("listBookBorrow", listBookBorrows);
        return "index";

    }

    // todo: thanh toán sách trả
    @PostMapping("/thanh-toan-sach-tra")
    public String ThanhToanSachTra(HttpSession session, Model model, 
        @RequestParam(name = "idBill") String idBill,
        @RequestParam(name = "mathe") int mathe, 
        @RequestParam(name = "ngaytra") Date ngaytra
        // ,
        // @RequestParam(name = "idBookDebit_1", defaultValue = "0") String idBookDebit_1,
        // @RequestParam(name = "idBookDebit_2", defaultValue = "0") String idBookDebit_2,
        // @RequestParam(name = "idBookDebit_3", defaultValue = "0") String idBookDebit_3,
        // @RequestParam(name = "numBookDebit_1", defaultValue = "0") String numBookDebit_1,
        // @RequestParam(name = "numBookDebit_2", defaultValue = "0") String numBookDebit_2,
        // @RequestParam(name = "numBookDebit_3", defaultValue = "0") String numBookDebit_3
        ) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        // todo: get nhân viên thanh toán
        TaiKhoanEntity acTemp = taiKhoanRepo.findByTentaikhoan(SessionService.getSession(session));
        if (idBill.equals("allBill")) {
            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(mathe, 1);
            for (PhieuMuonEntity i : listBill) {
                PhieuMuonEntity billTemp = phieuMuonRepo.findByMaphieumuon(i.getMaphieumuon());
                billTemp.setTrangthai(0);
                billTemp.setManhanvien(acTemp.getManhanvien());
                billTemp.setNgaytra(ngaytra);
                phieuMuonRepo.save(billTemp);

                List<ChiTietPhieuMuonEntity> listBillDetail = chiTietPhieuMuonRepo.findByMaphieumuon(i.getMaphieumuon());
                for (ChiTietPhieuMuonEntity j : listBillDetail) {
                    SachEntity bookUndo = sachRepo.findByMasach(j.getMasach());
                    bookUndo.setSoluong(bookUndo.getSoluong() + j.getSoluong());
                    sachRepo.save(bookUndo);
                }
            }
        } else {
            
            PhieuMuonEntity billTemp = phieuMuonRepo.findByMaphieumuon(Integer.parseInt(idBill));
            billTemp.setTrangthai(0);
            billTemp.setNgaytra(ngaytra);
            billTemp.setManhanvien(acTemp.getManhanvien());
            phieuMuonRepo.save(billTemp);

            List<ChiTietPhieuMuonEntity> listBillDetail = chiTietPhieuMuonRepo
                    .findByMaphieumuon(Integer.parseInt(idBill));
            for (ChiTietPhieuMuonEntity i : listBillDetail) {
                SachEntity bookUndo = sachRepo.findByMasach(i.getMasach());
                bookUndo.setSoluong(bookUndo.getSoluong() + i.getSoluong());
                sachRepo.save(bookUndo);
            }
        }


        // // todo: cập nhật lại số lượng sách thông qua sách nợ
        // String[] arrIdBookDebit = {idBookDebit_1, idBookDebit_2, idBookDebit_3};
        // String[] arrNumBookDebit = {numBookDebit_1, numBookDebit_2, numBookDebit_3}; 
        // for(int i=0; i<3; i++){
        //     SachEntity bookTemp = sachRepo.findByMasach(Integer.parseInt(arrIdBookDebit[i]));
        //     if(bookTemp != null){
        //         bookTemp.setSoluong(bookTemp.getSoluong() - Integer.parseInt(arrNumBookDebit[i]));
        //         sachRepo.save(bookTemp);
        //     }
        // }

        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Thanh toán sách thành công!");
        model.addAttribute("linkBack", "/tra-sach");
        return "index";
    }
}
