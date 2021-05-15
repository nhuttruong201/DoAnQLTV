package com.example.DoAnQLTV.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
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
    @Autowired private SachRepo sachRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private ChiTietPhieuMuonRepo chiTietPhieuMuonRepo;

    //todo: Trả sách - Kiểm tra thẻ
    @GetMapping("/tra-sach")
    public String TraSach(Model model, HttpSession session){
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }
        model.addAttribute("source", "tra-sach");
        model.addAttribute("fragment", "kiem-tra-the");
        model.addAttribute("title", "Yêu cầu trả sách");

        List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
        List<Integer> arrIdCardOnly = new ArrayList<Integer>();
        //todo: loại bỏ mã thẻ trùng nhau
        for(int i=0; i<listBill.size(); i++){
            if(!arrIdCardOnly.contains(listBill.get(i).getMathe())){
                arrIdCardOnly.add(listBill.get(i).getMathe());
            }
        }
        model.addAttribute("listIdCardBorrow", arrIdCardOnly);
        return "index";
    }

    @PostMapping("/tra-sach")
    public String TraSach(Model model, 
        @RequestParam(name = "mathe", defaultValue = "0") String mathe,
        @RequestParam(name = "sdt", defaultValue = "0") String sdt){

        model.addAttribute("source", "tra-sach");
        model.addAttribute("title", "Yâu cầu trả sách");

        //todo: check rỗng submit
        if(mathe.equals("0") && sdt.equals("0")){
            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
            List<Integer> arrIdCardOnly = new ArrayList<Integer>();
            //todo: loại bỏ mã thẻ trùng nhau
            for(int i=0; i<listBill.size(); i++){
                if(!arrIdCardOnly.contains(listBill.get(i).getMathe())){
                    arrIdCardOnly.add(listBill.get(i).getMathe());
                }
            }
            model.addAttribute("listIdCardBorrow", arrIdCardOnly);
            model.addAttribute("fragment", "khong-co-du-lieu");
            return "index";
        }

        if(mathe.equals("0")){
            //todo: check với số điện thoại
            TheThuVienEntity card = theThuVienRepo.findBySodienthoai(sdt);
            if(card == null){
                List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
                List<Integer> arrIdCardOnly = new ArrayList<Integer>();
                //todo: loại bỏ mã thẻ trùng nhau
                for(int i=0; i<listBill.size(); i++){
                    if(!arrIdCardOnly.contains(listBill.get(i).getMathe())){
                        arrIdCardOnly.add(listBill.get(i).getMathe());
                    }
                }
                model.addAttribute("listIdCardBorrow", arrIdCardOnly);
                model.addAttribute("fragment", "the-khong-ton-tai");
                model.addAttribute("alert", "không tồn tại thẻ với số điện thoại: " + sdt);
                model.addAttribute("sdt", sdt);

                return "index";
            }else{
                model.addAttribute("mathe", card.getMathe());
                model.addAttribute("hoten", card.getHoten());
                model.addAttribute("sdt", card.getSodienthoai());
                model.addAttribute("fragment", "tra-sach");       
                
                TheThuVienEntity cardTemp = theThuVienRepo.findBySodienthoai(sdt);
                List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(cardTemp.getMathe(), 1);
                List<BookBorrow> listBookBorrows = new ArrayList<BookBorrow>();

                for(PhieuMuonEntity bill : listBill){
                    List<ChiTietPhieuMuonEntity> listCTPM = chiTietPhieuMuonRepo.findByMaphieumuon(bill.getMaphieumuon());
                    for(ChiTietPhieuMuonEntity temp : listCTPM){
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
                 // phục vụ check ngày trả phải lớn hơn ngaỳ hiện tại
                LocalDate toDay = LocalDate.now();
                model.addAttribute("toDay", toDay);
                return "index";
            }
        }else{
            //todo: check với mã thẻ, không thể null
            TheThuVienEntity card = theThuVienRepo.findByMathe(Integer.parseInt(mathe));
            model.addAttribute("mathe", card.getMathe());
            model.addAttribute("hoten", card.getHoten());
            model.addAttribute("sdt", card.getSodienthoai());
            model.addAttribute("fragment", "tra-sach");

            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(Integer.parseInt(mathe), 1);
            listBill.forEach(System.out :: println);
            List<BookBorrow> listBookBorrows = new ArrayList<BookBorrow>();

            for(PhieuMuonEntity bill : listBill){
                List<ChiTietPhieuMuonEntity> listCTPM = chiTietPhieuMuonRepo.findByMaphieumuon(bill.getMaphieumuon());
                for(ChiTietPhieuMuonEntity temp : listCTPM){
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
            //todo: phục vụ set value ngày trả phải = ngaỳ hiện tại
            LocalDate toDay = LocalDate.now();
            model.addAttribute("toDay", toDay);
            return "index";
        }
    }

    //todo: thanh toán sách trả
    @PostMapping("/thanh-toan-sach-tra")
    public String ThanhToanSachTra(Model model,
        @RequestParam(name = "idBill") String idBill,
        @RequestParam(name = "mathe") int mathe,
        @RequestParam(name = "ngaytra") Date ngaytra){

        if(idBill.equals("allBill")){
            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(mathe, 1);
            for(PhieuMuonEntity i : listBill){
                PhieuMuonEntity billTemp = phieuMuonRepo.findByMaphieumuon(i.getMaphieumuon());
                billTemp.setTrangthai(0);
                billTemp.setNgaytra(ngaytra);
                phieuMuonRepo.save(billTemp);

                List<ChiTietPhieuMuonEntity> listBillDetal = chiTietPhieuMuonRepo.findByMaphieumuon(i.getMaphieumuon());
                for(ChiTietPhieuMuonEntity j : listBillDetal){
                    SachEntity bookUndo = sachRepo.findByMasach(j.getMasach());
                    bookUndo.setSoluong(bookUndo.getSoluong() + j.getSoluong());
                    sachRepo.save(bookUndo);
                }
            }
        }else{
            PhieuMuonEntity billTemp = phieuMuonRepo.findByMaphieumuon(Integer.parseInt(idBill));
            billTemp.setTrangthai(0);
            billTemp.setNgaytra(ngaytra);
            phieuMuonRepo.save(billTemp);

            List<ChiTietPhieuMuonEntity> listBillDetal = chiTietPhieuMuonRepo.findByMaphieumuon(Integer.parseInt(idBill));
            for(ChiTietPhieuMuonEntity i : listBillDetal){
                SachEntity bookUndo = sachRepo.findByMasach(i.getMasach());
                bookUndo.setSoluong(bookUndo.getSoluong() + i.getSoluong());
                sachRepo.save(bookUndo);
            }
        }

        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Thanh toán sách thành công!");
        model.addAttribute("linkBack", "/tra-sach");
        return "index";
    }
}
