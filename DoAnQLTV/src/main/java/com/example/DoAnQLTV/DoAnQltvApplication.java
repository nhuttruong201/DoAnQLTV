package com.example.DoAnQLTV;

import com.example.DoAnQLTV.entity.*;
import com.example.DoAnQLTV.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    @Autowired private QuyenHanRepo quyenHanRepo;

    
    public static void main(String[] args) {
        System.out.println(PURPLE);
        SpringApplication.run(DoAnQltvApplication.class, args);
    }
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" + CYAN_BOLD);
        System.out.println("_______________________");
        System.out.println("-----------------------");
        System.out.println("===== B =============");
        System.out.println("===== E ===========");
        System.out.println("===== G =========");
        System.out.println("===== I =======");
        System.out.println("===== N =====");
        System.out.println("_________");
        System.out.println("_______");
        System.out.println("_____");
        System.out.println("___");
        System.out.println("_");
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  CHỨC VỤ  -----------------------------------");
        System.out.println(BLUE);
        List<ChucVuEntity> listChucVu = chucVuRepo.findAll();
        listChucVu.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  NHÂN VIÊN  ---------------------------------");
        System.out.println(BLUE);
        List<NhanVienEntity> listNhanVien = nhanVienRepo.findAll();
        listNhanVien.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  NHÀ XUẤT BẢN  ------------------------------");
        System.out.println(BLUE);
        List<NhaXuatBanEntity> nhaXuatBanEntityList = nhaXuatBanRepo.findAll();
        nhaXuatBanEntityList.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  PHIẾU MƯỢN  --------------------------------");
        System.out.println(BLUE);
        List<PhieuMuonEntity> phieuMuonEntityList = phieuMuonRepo.findAll();
        phieuMuonEntityList.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  CHI TIẾT PHIẾU MƯỢN  -----------------------");
        System.out.println(BLUE);
        List<ChiTietPhieuMuonEntity> listCTPM = chiTietPhieuMuonRepo.findAll();
        listCTPM.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  SÁCH  --------------------------------------");
        System.out.println(BLUE);
        List<SachEntity> sachEntityList = sachRepo.findAll();
        sachEntityList.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  QUYỀN HẠN  -----------------------");
        System.out.println(BLUE);
        List<QuyenHanEntity> listQH = quyenHanRepo.findAll();
        listQH.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  TÀI KHOẢN  ---------------------------------");
        System.out.println(BLUE);
        List<TaiKhoanEntity> taiKhoanEntities = taiKhoanRepo.findAll();
        taiKhoanEntities.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  THỂ LOẠI  ----------------------------------");
        System.out.println(BLUE);
        List<TheLoaiEntity> theLoaiEntities = theLoaiRepo.findAll();
        theLoaiEntities.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  THẺ THƯ VIỆN  ------------------------------");
        System.out.println(BLUE);
        List<TheThuVienEntity> theThuVienEntityList = theThuVienRepo.findAll();
        theThuVienEntityList.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  TRẠNG THÁI THẺ  ----------------------------");
        System.out.println(BLUE);
        List<TrangThaiTheEntity> trangThaiTheEntities = trangThaiTheRepo.findAll();
        trangThaiTheEntities.forEach(System.out::println);
        System.out.println(GREEN);
        System.out.println("___________________________________________________________________");
        System.out.println("---------------------  END  ---------------------------------------");

        System.out.println(PURPLE);
        LocalDate localDate =  LocalDate.now();
        System.out.println("Ngày " + localDate.getDayOfMonth());
        System.out.println("Tháng " + localDate.getMonthValue());
        System.out.println("Năm " + localDate.getYear());


        System.out.println("\n\n\n");
        System.out.println(RESET);

        // todo: Load thẻ bị khóa
        //todo: Thẻ hết hạn
        LocalDate today = LocalDate.now();
        List<TheThuVienEntity> listAllCard = theThuVienRepo.findAll();
        for(var i : listAllCard){
            String hansudung = i.getHansudung().toString();
            if(LocalDate.parse(hansudung).compareTo(today)<0){
               i.setMatrangthai("lock");
               theThuVienRepo.save(i);
            }
        }
    }
}
