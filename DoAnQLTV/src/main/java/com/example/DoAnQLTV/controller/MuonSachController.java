package com.example.DoAnQLTV.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.NhaXuatBanRepo;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheLoaiRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.repository.TrangThaiTheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MuonSachController {
    @Autowired private SachRepo sachRepo;
    @Autowired private TheLoaiRepo theLoaiRepo;
    @Autowired private ChiTietPhieuMuonRepo ctpmRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;
    @Autowired private NhaXuatBanRepo nhaXuatBanRepo;
    @Autowired private TrangThaiTheRepo trangThaiTheRepo;
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private ChiTietPhieuMuonRepo chiTietPhieuMuonRepo;
    @Autowired private NhanVienRepo nhanVienRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;

    
    // Mượn sách - Kiểm tra thẻ
    @GetMapping("/muon-sach")
    public String MuonSach(Model model){
        model.addAttribute("source", "muon-sach");
        model.addAttribute("fragment", "kiem-tra-the");
        model.addAttribute("title", "Yâu cầu mượn sách");

        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        model.addAttribute("listCard", listCard);
        return "index";
    }

    @PostMapping("/muon-sach")
    public String MuonSach(Model model,
        @RequestParam(name = "mathe", defaultValue = "0") String mathe,
        @RequestParam(name = "sdt", defaultValue = "0") String sdt){

        model.addAttribute("source", "muon-sach");
        model.addAttribute("title", "Yâu cầu mượn sách");
        List<TheThuVienEntity> listCard = theThuVienRepo.findAll();
        List<SachEntity> listBook = sachRepo.findAll();
        model.addAttribute("listCard", listCard);
        model.addAttribute("listBook", listBook);
        
        // aler
        String noSdt = "Không tồn tại thẻ với số điện thoại: " + sdt;
        // Check Card
        TheThuVienEntity cardCheck;
        // cả thẻ và sdt rỗng
        if(mathe.equals("0") && sdt.equals("0")){
            model.addAttribute("fragment", "khong-co-du-lieu");
            model.addAttribute("sdt", sdt);
            return "index";
        }  
        // thẻ rỗng
        if(mathe.equals("0")){
            cardCheck = theThuVienRepo.findBySodienthoai(sdt);
            if(cardCheck==null){
                 // thẻ không tồn tại
                model.addAttribute("fragment", "the-khong-ton-tai");
                model.addAttribute("sdt", sdt);
                model.addAttribute("dataSearch", noSdt);
                return "index";
            }
        }else{
            cardCheck = theThuVienRepo.findByMathe(Integer.parseInt(mathe));
            // check = mã thẻ chắc chắn tồn tại thẻ do tải dữ liệu db lên select input nên ko cần check thẻ null
        }
        // check thẻ bị khóa
        if(cardCheck.getMatrangthai().equals("lock")){
            model.addAttribute("fragment", "the-bi-khoa");
            // xuất thông tin thẻ hiện tại
            TheThuVienEntity card = theThuVienRepo.findByMathe(Integer.parseInt(mathe));
            model.addAttribute("mathe", card.getMathe());
            model.addAttribute("hoten", card.getHoten());
            model.addAttribute("gioitinh", card.getGioitinh());
            model.addAttribute("sdt", card.getSodienthoai());
            return "index";
        }else{
            // check mã thẻ rỗng
            // nếu mã thẻ rỗng thì data submit sẽ là sdt
            if(mathe.equals("0")){
                // duyệt để tìm thẻ yêu cầu mượn sách
                for(TheThuVienEntity i : listCard){
                    if(i.getSodienthoai().equals(sdt)){
                        model.addAttribute("mathe", i.getMathe());
                        model.addAttribute("hoten", i.getHoten());
                        model.addAttribute("sdt", i.getSodienthoai());
                        model.addAttribute("fragment", "muon-sach");
                        
                        return "index";
                    }
                }
                // không tồn tại thẻ với sdt submit
                model.addAttribute("dataSearch", noSdt);
                model.addAttribute("fragment", "the-khong-ton-tai");
                model.addAttribute("sdt", sdt);
                return "index";
            }else{
                // mã thẻ không rỗng -> data submit = mã thẻ
                List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMatheAndTrangthai(Integer.parseInt(mathe), 1);
                // check thẻ hết lượt: tối đa 2 lượt mượn cùng lúc
                if(listBill.size()<2){
                    // còn lượt
                    // duyệt để tìm thẻ yêu cầu mượn sách
                    for(TheThuVienEntity i : listCard){
                        if(i.getMathe() == Integer.parseInt(mathe)){
                            model.addAttribute("mathe", i.getMathe());
                            model.addAttribute("hoten", i.getHoten());
                            model.addAttribute("sdt", i.getSodienthoai());
                            model.addAttribute("fragment", "muon-sach");
                            
                            return "index";
                        }
                    }
                }else{
                    // quá số lần mượn
                    model.addAttribute("fragment", "het-luot");
                    TheThuVienEntity card = theThuVienRepo.findByMathe(Integer.parseInt(mathe));
                    model.addAttribute("mathe", mathe);
                    model.addAttribute("hoten", card.getHoten());
                    model.addAttribute("gioitinh", card.getGioitinh());
                    model.addAttribute("sdt", card.getSodienthoai());
    
                    return "index";
                }
            }
    
            // thẻ không tồn tại
            model.addAttribute("fragment", "the-khong-ton-tai");
            model.addAttribute("sdt", sdt);
            return "index";
        }

    }
    
    // Lập phiếu mượn
    @PostMapping("/lap-phieu-muon")
    public String LapPhieuMuon(Model model, 
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
       
        LocalDate localDate = LocalDate.now();
        Date ngaymuon = Date.valueOf(localDate);

        PhieuMuonEntity temp = new PhieuMuonEntity();
        temp.setMathe(mathe);
        temp.setManhanvien(1);
        temp.setNgaymuon(ngaymuon);
        temp.setHantra(hantra);
        temp.setTrangthai(1);
        phieuMuonRepo.save(temp);

        // save ctpm
        String[] arrBookId = {bookId_1, bookId_2, bookId_3, bookId_4, bookId_5};
        String[] arrNumBook = {numBook_1, numBook_2, numBook_3, numBook_4, numBook_5};

        List<PhieuMuonEntity> listBill = phieuMuonRepo.findAll();
        
        // Đảo ngược list để lấy mã vừa tạo
        Collections.reverse(listBill);
        int maphieumuon = listBill.get(0).getMaphieumuon();
        System.out.println("Mã phiếu mượn vừa tạo: " + maphieumuon);
        for(int i=0; i<numBookType; i++){
            ChiTietPhieuMuonEntity ctpmTemp = new ChiTietPhieuMuonEntity();
            ctpmTemp.setMaphieumuon(maphieumuon);
            ctpmTemp.setMasach(Integer.parseInt(arrBookId[i]));
            ctpmTemp.setSoluong(Integer.parseInt(arrNumBook[i]));
            chiTietPhieuMuonRepo.save(ctpmTemp);
            SachEntity bookTemp = sachRepo.findByMasach(Integer.parseInt(arrBookId[i]));
            bookTemp.setSoluong(bookTemp.getSoluong()-Integer.parseInt(arrNumBook[i]));
            sachRepo.save(bookTemp);
        }
        
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Lập phiếu mượn thành công!");
        model.addAttribute("linkBack", "/muon-sach");
        return "index";
    }
}
