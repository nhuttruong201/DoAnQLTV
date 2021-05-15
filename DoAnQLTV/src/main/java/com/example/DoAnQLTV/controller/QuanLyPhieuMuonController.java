package com.example.DoAnQLTV.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class QuanLyPhieuMuonController {
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;

    //todo: Thống kê
    @GetMapping("/quan-ly-phieu-muon/{type}")
    public String QuanLyPhieuMuon(Model model, @PathVariable(name = "type") String type, HttpSession session){
        //todo: check login - note: truyền HttpSession session
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }

        model.addAttribute("title", "Quản lý phiếu mượn");
        model.addAttribute("source", "quan-ly-phieu-muon");
        model.addAttribute("fragment", "quan-ly-phieu-muon");
        model.addAttribute("type", type);
        
        List<PhieuMuonEntity> listAllBill = phieuMuonRepo.findAll();
        model.addAttribute("listAllBill", listAllBill);
        List<PhieuMuonEntity> listBill = new ArrayList<PhieuMuonEntity>();

        if(type.equals("tat-ca-phieu")){
            //todo: tất cả bill
            listBill = phieuMuonRepo.findAll();
        }else if(type.equals("chua-thanh-toan")){
            //todo: bill chưa thanh toán
            listBill = phieuMuonRepo.findByTrangthai(1);
        }else if(type.equals("phieu-qua-han")){
            //todo: quá hạn
           
            List<PhieuMuonEntity> listBillUnpaid = phieuMuonRepo.findByTrangthai(1);
            LocalDate ngayhientai = LocalDate.now();
            for(PhieuMuonEntity bill : listBillUnpaid){
                String hantra = bill.getHantra() + "";
                if(LocalDate.parse(hantra).compareTo(ngayhientai) < 0){
                    listBill.add(bill);
                }
            }
        }
        //todo: phục vụ cmb để tìm kiếm
        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        model.addAttribute("listCard", listCard);
        //todo: listBill thống kê
        Collections.reverse(listBill);
        model.addAttribute("listBill", listBill);
        return "index";
    }

    @PostMapping("/tim-phieu-muon")
    public String TimPhieuMuon(Model model,
        @RequestParam(name = "maphieumuon", defaultValue = "") String maphieumuon,
        @RequestParam(name = "mathe", defaultValue = "") String mathe,
        @RequestParam(name = "ngaymuon", defaultValue = "" ) String ngaymuon){
            model.addAttribute("title", "Tìm phiếu mượn");
            model.addAttribute("source", "tim-phieu-muon");
            model.addAttribute("fragment", "tim-phieu-muon");
            List<PhieuMuonEntity> listBill = new ArrayList<PhieuMuonEntity>();
            // nếu 3 trường đều rỗng -> gửi lại toàn bộ phiếu mượn do không có điều kiện tìm kiếm
            if(maphieumuon.equals("") && mathe.equals("") && ngaymuon.equals("")){
                model.addAttribute("maphieumuon", maphieumuon);
                model.addAttribute("mathe", mathe);
                model.addAttribute("ngaymuon", ngaymuon);
                listBill = phieuMuonRepo.findAll();
                Collections.reverse(listBill);              
                model.addAttribute("listAllBill", listBill);
                model.addAttribute("size", listBill.size());
                return "index";
            }
            // ưu tiên check mã phiếu mượn do nó là khóa chính
            // mã phiễu mượn rỗng
            if(maphieumuon.equals("")){
                // thẻ rỗng, ngày mượn có -> tìm theo ngày mượn
                if(mathe.equals("") && !ngaymuon.equals("")){
                    Date ngaymuonCopy = Date.valueOf(LocalDate.parse(ngaymuon));
                    listBill = phieuMuonRepo.findByNgaymuon(ngaymuonCopy);
                }
                // ngày mượn rỗng, thẻ có -> tìm theo mã thẻ
                else if(!mathe.equals("") && ngaymuon.equals("")){
                    listBill = phieuMuonRepo.findByMathe(Integer.parseInt(mathe));
                }
                // cả 2 đều tồn tại -> tìm theo cả 2
                else{
                    Date ngaymuonCopy = Date.valueOf(LocalDate.parse(ngaymuon));
                    listBill = phieuMuonRepo.findByMatheAndNgaymuon(Integer.parseInt(mathe), ngaymuonCopy);
                }
            }
            // mã phiếu mượn tồn tại
            else{
                mathe = "";
                ngaymuon = "";
                PhieuMuonEntity bill = phieuMuonRepo.findByMaphieumuon(Integer.parseInt(maphieumuon));
                if(bill != null){
                    listBill.add(bill);
                }
            }
            
            model.addAttribute("maphieumuon", maphieumuon);
            model.addAttribute("mathe", mathe);
            model.addAttribute("ngaymuon", ngaymuon);
            Collections.reverse(listBill);
            model.addAttribute("listAllBill", listBill);
            model.addAttribute("size", listBill.size());
            return "index";
    }
}
