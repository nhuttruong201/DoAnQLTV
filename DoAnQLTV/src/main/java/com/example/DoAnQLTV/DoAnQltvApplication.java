package com.example.DoAnQLTV;

import com.example.DoAnQLTV.entity.*;
import com.example.DoAnQLTV.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class DoAnQltvApplication implements CommandLineRunner {

    @Autowired
    private ChiTietPhieuMuonRepo chiTietPhieuMuonRepo;
    @Autowired
    private ChucVuRepo chucVuRepo;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private NhaXuatBanRepo nhaXuatBanRepo;
    @Autowired
    private PhieuMuonRepo phieuMuonRepo;
    @Autowired
    private SachRepo sachRepo;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;
    @Autowired
    private TheLoaiRepo theLoaiRepo;
    @Autowired
    private TheThuVienRepo theThuVienRepo;
    @Autowired
    private TrangThaiTheRepo trangThaiTheRepo;

    public static void main(String[] args) {
        SpringApplication.run(DoAnQltvApplication.class, args);
    }

    @GetMapping("/search/{id}")
    public String search(@PathVariable String id, Model model) {
        List<NhanVienEntity> listName = nhanVienRepo.findByHotenLike("%" + id + "%");
        listName.forEach(System.out::println);
        // List<SachEntity> listBook = sachRepo.findByTensachLike("%" + id + "%");
        // listBook.forEach(System.out::println);
        return id;
    }


   

    @Override
    public void run(String... args) throws Exception {
        System.out.println("**********************");
        System.out.println("***** B ************");
        System.out.println("***** E **********");
        System.out.println("***** G ********");
        System.out.println("***** I ******");
        System.out.println("***** N ****");
        System.out.println("_________");
        System.out.println("_______");
        System.out.println("_____");
        System.out.println("___");
        System.out.println("_");
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  CHI TIẾT PHIẾU MƯỢN  -----------------------");
        List<ChiTietPhieuMuonEntity> listCTPM = chiTietPhieuMuonRepo.findAll();
        listCTPM.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  CHỨC VỤ  -----------------------------------");
        List<ChucVuEntity> listChucVu = chucVuRepo.findAll();
        listChucVu.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  NHÂN VIÊN  ---------------------------------");
        List<NhanVienEntity> listNhanVien = nhanVienRepo.findAll();
        listNhanVien.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  NHÀ XUẤT BẢN  ------------------------------");
        List<NhaXuatBanEntity> nhaXuatBanEntityList = nhaXuatBanRepo.findAll();
        nhaXuatBanEntityList.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  PHIẾU MƯỢN  --------------------------------");
        List<PhieuMuonEntity> phieuMuonEntityList = phieuMuonRepo.findAll();
        phieuMuonEntityList.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  SÁCH  --------------------------------------");
        List<SachEntity> sachEntityList = sachRepo.findAll();
        sachEntityList.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  TÀI KHOẢN  ---------------------------------");
        List<TaiKhoanEntity> taiKhoanEntities = taiKhoanRepo.findAll();
        taiKhoanEntities.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  THỂ LOẠI  ----------------------------------");
        List<TheLoaiEntity> theLoaiEntities = theLoaiRepo.findAll();
        theLoaiEntities.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  THẺ THƯ VIỆN  ------------------------------");
        List<TheThuVienEntity> theThuVienEntityList = theThuVienRepo.findAll();
        theThuVienEntityList.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  TRẠNG THÁI THẺ  ----------------------------");
        List<TrangThaiTheEntity> trangThaiTheEntities = trangThaiTheRepo.findAll();
        trangThaiTheEntities.forEach(System.out::println);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  END  ---------------------------------------");
   
        
        LocalDate localDate =  LocalDate.now();
        System.out.println("Ngày " + localDate.getDayOfMonth());
        System.out.println("Tháng " + localDate.getMonthValue());
        System.out.println("Năm " + localDate.getYear());

        
        

        // Date temp = theThuVienRepo.findByHansudung();

        // System.out.println(temp);
    
    


    }
}
