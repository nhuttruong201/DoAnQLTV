package com.example.DoAnQLTV.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
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
import com.example.DoAnQLTV.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MuonSachController {
    @Autowired private SachRepo sachRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private ChiTietPhieuMuonRepo chiTietPhieuMuonRepo;
    @Autowired private QuyenHanRepo quyenHanRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private NhanVienRepo nhanVienRepo;

    //todo: Mượn sách - Kiểm tra thẻ
    @GetMapping("/muon-sach")
    public String MuonSach(Model model, HttpSession session){
        //todo: check login - note: truyền HttpSession session
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }
        //todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        
        model.addAttribute("source", "muon-sach");
        model.addAttribute("fragment", "kiem-tra-the");
        model.addAttribute("title", "Yâu cầu mượn sách");

        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        model.addAttribute("listCard", listCard);
        return "index";
    }

    @PostMapping("/muon-sach")
    public String MuonSach(HttpSession session, Model model,
        @RequestParam(name = "mathe", defaultValue = "") String mathe,
        @RequestParam(name = "sdt", defaultValue = "") String sdt
        ){
        //todo: check login - note: truyền HttpSession session
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }
        //todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("source", "muon-sach");
        model.addAttribute("title", "Yâu cầu mượn sách");
        model.addAttribute("mathe", mathe);
        model.addAttribute("sdt", sdt);
 
        List<SachEntity> listBook = sachRepo.findAll();
        //todo: phục vụ check mã sách mượn
        model.addAttribute("listBook", listBook);
    
        TheThuVienEntity cardCheck = new TheThuVienEntity();
        //todo: cả mã thẻ và sdt rỗng
        if(mathe.equals("") && sdt.equals("")){
            return "redirect:/muon-sach";
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
        //todo: check thẻ không tồn tại
        if(cardCheck==null){
            model.addAttribute("fragment", "check-fail");
            model.addAttribute("titleAlert", "Thẻ không tồn tại!");
            model.addAttribute("alert", "Vui lòng nhập mã thẻ hoặc số điện thoại khác để check!");
            return "index";
        }
        // todo: Load thẻ hết hạn
        LocalDate today = LocalDate.now();
        List<TheThuVienEntity> listAllCard = theThuVienRepo.findAll();
        for(var i : listAllCard){
            String hansudung = i.getHansudung().toString();
            if(LocalDate.parse(hansudung).compareTo(today)<0){
               i.setMatrangthai("lock");
               theThuVienRepo.save(i);
            }
        }
        //todo: check thẻ bị khóa
        if(cardCheck.getMatrangthai().equals("lock")){
            model.addAttribute("fragment", "check-fail");
            model.addAttribute("titleAlert", "Thẻ đã bị vô hiệu hóa!");
            model.addAttribute("alert", "Thẻ đã vi phạm điều lệ mượn sách nên bị khóa!");
            return "index";
        }
        
        //todo: Thẻ đủ điều kiện mượn sách
        //todo: xuất thông tin thẻ để lập phiếu mượn
        model.addAttribute("mathe", cardCheck.getMathe());
        model.addAttribute("hoten", cardCheck.getHoten());
        model.addAttribute("sdt", cardCheck.getSodienthoai());
        model.addAttribute("fragment", "muon-sach");
        //todo: phục vụ check ngày trả phải lớn hơn ngaỳ hiện tại
        LocalDate toDay = LocalDate.now();
        model.addAttribute("toDay", toDay);
        return "index";
    }
    
    //todo: Lập phiếu mượn
    @PostMapping("/lap-phieu-muon")
    public String LapPhieuMuon(HttpSession session, Model model, 
        @RequestParam(name = "mathe") int mathe,
        @RequestParam(name = "hantra") Date hantra,
        @RequestParam(name = "numBookType") int numBookType,
        @RequestParam(name = "bookId[0]") String bookId_1,
        @RequestParam(name = "bookId[1]") String bookId_2,
        @RequestParam(name = "bookId[2]") String bookId_3,
        @RequestParam(name = "bookId[3]") String bookId_4,
        @RequestParam(name = "bookId[4]") String bookId_5,
        @RequestParam(name = "numBook[0]") String numBook_1,
        @RequestParam(name = "numBook[1]") String numBook_2,
        @RequestParam(name = "numBook[2]") String numBook_3,
        @RequestParam(name = "numBook[3]") String numBook_4,
        @RequestParam(name = "numBook[4]") String numBook_5){
        //todo: check login - note: truyền HttpSession session
        if(!SessionService.CheckLogin(session)){
            return "redirect:/login";
        }
        //todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
       
        //todo: lấy ngày hiện tại để lập phiếu mượn
        LocalDate toDay = LocalDate.now();
        //todo: convert to Date để lưu vào csdl
        Date ngaymuon = Date.valueOf(toDay);
        //todo: lấy mã nhân viên để lập phiếu mượn
        TaiKhoanEntity acTemp = taiKhoanRepo.findByTentaikhoan(SessionService.getSession(session));
        int manhanvien = acTemp.getManhanvien();
        //todo: Lập phiếu mượn
        PhieuMuonEntity temp = new PhieuMuonEntity();
        temp.setMathe(mathe);
        temp.setManhanvien(manhanvien);
        temp.setNgaymuon(ngaymuon);
        temp.setHantra(hantra);
        temp.setTrangthai(1);
        phieuMuonRepo.save(temp);

        //todo: xử lý dữ liệu phiếu mượn vừa lập
        String[] arrBookId = {bookId_1, bookId_2, bookId_3, bookId_4, bookId_5};
        String[] arrNumBook = {numBook_1, numBook_2, numBook_3, numBook_4, numBook_5};

        List<PhieuMuonEntity> listBill = phieuMuonRepo.findAll();
        //todo: Đảo ngược list bill để lấy id bill vừa tạo
        Collections.reverse(listBill);
        int maphieumuon = listBill.get(0).getMaphieumuon();
        System.out.println("Mã phiếu mượn vừa tạo: " + maphieumuon);
        //todo: duyệt theo số lượng loại sách
        for(int i=0; i<numBookType; i++){
            ChiTietPhieuMuonEntity ctpmTemp = new ChiTietPhieuMuonEntity();
            ctpmTemp.setMaphieumuon(maphieumuon);
            ctpmTemp.setMasach(Integer.parseInt(arrBookId[i]));
            ctpmTemp.setSoluong(Integer.parseInt(arrNumBook[i]));
            chiTietPhieuMuonRepo.save(ctpmTemp);
            //todo: cập nhật lại số lượng sách trong kho
            SachEntity bookTemp = sachRepo.findByMasach(Integer.parseInt(arrBookId[i]));
            bookTemp.setSoluong(bookTemp.getSoluong()-Integer.parseInt(arrNumBook[i]));
            sachRepo.save(bookTemp);
        }
        //todo Lập phiếu mượn thành công
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Lập phiếu mượn thành công!");
        model.addAttribute("linkBack", "/muon-sach");
        return "index";
    }
}
