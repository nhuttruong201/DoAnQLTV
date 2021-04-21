package com.example.DoAnQLTV.controller;

import java.util.List;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TheLoaiRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class mainController {
    @Autowired
    private TheThuVienRepo cardRepo;
    @Autowired
    private SachRepo sachRepo;
    @Autowired
    private TheLoaiRepo theLoaiRepo;
    @Autowired
    private ChiTietPhieuMuonRepo ctpmRepo;
    @Autowired
    private TheThuVienRepo theThuVienRepo;
    // Làm thẻ thư viện
    @GetMapping("/lam-the-thu-vien")
    public String LamTheThuVien(Model model){
        model.addAttribute("source", "lam-the-thu-vien");
        model.addAttribute("fragment", "lam_the_thu_vien");
        model.addAttribute("title", "Làm thẻ thư viện");
        return "index";
    }
    @PostMapping("/lam-the-thu-vien")
    public String XuLyLamTheThuVien(Model model,
        @RequestParam(name = "fullname") String hoten,
        @RequestParam(name = "gioitinh") String gioitinh,
        @RequestParam(name = "sdt") String sodienthoai ){


        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        System.out.println(date);
      
        TheThuVienEntity newCard = new TheThuVienEntity();
        newCard.setHoten(hoten);
        newCard.setGioitinh(gioitinh);
        newCard.setSodienthoai(sodienthoai);
        newCard.setDiachi("chưa xác định");
        newCard.setHansudung(date);
        newCard.setSolanphat(0);
        newCard.setMatrangthai("open");

        // cardRepo.save(newCard);

        System.out.println("Họ tên: " + hoten + ", Giới tính: " + gioitinh + ", SDT: " + sodienthoai);

        model.addAttribute("source", "lam-the-thu-vien");
        model.addAttribute("fragment", "tao_the_thanh_cong");
        model.addAttribute("title", "Làm thẻ thư viện");
        return "index";
    }
    
    // Mượn sách
   
    @GetMapping("/muon-sach")
    public String MuonSach(Model model){

        List<SachEntity> listBook = sachRepo.findAll();
        model.addAttribute("listBook", listBook);

        model.addAttribute("source", "muon-sach");
        model.addAttribute("fragment", "muon_sach");
        model.addAttribute("title", "Cho mượn sách");

        return "index";
    }

    // Trả sách
    @GetMapping("/tra-sach")
    public String TraSach(Model model){
        model.addAttribute("source", "tra-sach");
        model.addAttribute("fragment", "tra_sach");
        model.addAttribute("title", "Nhận trả sách");
        return "index";
    }

    // Thống kê
    @GetMapping("/thong-ke/{type}")
    public String ThongKe(
        @PathVariable(name = "type") String type, Model model){
        List<SachEntity> listBook = sachRepo.findAll();
        model.addAttribute("listBook", listBook);
        List<ChiTietPhieuMuonEntity> listSachMuon = ctpmRepo.findAll();
        model.addAttribute("listSachMuon", listSachMuon);
        List<TheThuVienEntity> listTheBiKhoa = theThuVienRepo.findByMatrangthai("lock");
        model.addAttribute("listCard", listTheBiKhoa);

        model.addAttribute("source", "thong-ke");
        model.addAttribute("fragment", "thong_ke");
        model.addAttribute("type", type);
        System.out.println("type = " + type);
        model.addAttribute("title", "Thống kê");
        return "index";
    }

    // Tìm sách
    @PostMapping("/tim-kiem")
    public String TimKiem(@RequestParam(name="bookName", defaultValue = "", required = false) String bookName, Model model){

        List<SachEntity> listBook = sachRepo.findByTensachLike("%" + bookName + "%");
        model.addAttribute("listBook", listBook);
        model.addAttribute("length", listBook.size());

        model.addAttribute("source", "tim-kiem");
        model.addAttribute("fragment", "tim_kiem");
        model.addAttribute("title", "Tìm kiếm");
        model.addAttribute("keyword", bookName);
        return "index";
    }

}
