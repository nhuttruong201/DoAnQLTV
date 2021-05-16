package com.example.DoAnQLTV.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.display.BookDisplay;
import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.NhaXuatBanEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheLoaiEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.NhaXuatBanRepo;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.repository.TheLoaiRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;
import com.example.DoAnQLTV.service.BookBorrow;
import com.example.DoAnQLTV.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuanLySachController {
    @Autowired
    private SachRepo sachRepo;
    @Autowired
    private TheLoaiRepo theLoaiRepo;
    @Autowired
    private ChiTietPhieuMuonRepo ctpmRepo;
    @Autowired
    private TheThuVienRepo theThuVienRepo;
    @Autowired
    private NhaXuatBanRepo nhaXuatBanRepo;
    @Autowired
    private PhieuMuonRepo phieuMuonRepo;
    @Autowired
    private QuyenHanRepo quyenHanRepo;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;
    @Autowired
    NhanVienRepo nhanVienRepo;

    // todo: Quản lý sách - Thống kê
    @GetMapping("/quan-ly-sach/{type}")
    public String QuanLySach(Model model, @PathVariable(name = "type") String type, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));

        model.addAttribute("source", "quan-ly-sach");
        model.addAttribute("fragment", "quan-ly-sach");
        model.addAttribute("title", "Quản lý sách");
        model.addAttribute("type", type);

        List<SachEntity> listBook = new ArrayList<SachEntity>();
        List<BookBorrow> listBookBorrows = new ArrayList<BookBorrow>();
        List<BookDisplay> listBookDisplay = new ArrayList<BookDisplay>();

        if (type.equals("tat-ca-sach")) {
            // todo: tất cả sách
            listBook = sachRepo.findAll();
            for (SachEntity i : listBook) {
                BookDisplay temp = new BookDisplay();
                temp.setMasach(i.getMasach());
                temp.setTensach(i.getTensach());
                temp.setTacgia(i.getTacgia());
                temp.setTentheloai(i.getTenTheLoai(theLoaiRepo, i.getMatheloai()));
                temp.setTennhaxuatban(i.getTenNhaXuatBan(nhaXuatBanRepo, i.getManhaxuatban()));
                temp.setSoluong(i.getSoluong());
                listBookDisplay.add(temp);
            }
        } else if (type.equals("sach-dang-muon")) {
            // todo: sách đang mượn
            List<ChiTietPhieuMuonEntity> listCTPM = ctpmRepo.findAll();
            List<PhieuMuonEntity> listBill = phieuMuonRepo.findByTrangthai(1);
            for (PhieuMuonEntity bill : listBill) {
                for (ChiTietPhieuMuonEntity billDetail : listCTPM) {
                    if (bill.getMaphieumuon() == billDetail.getMaphieumuon()) {
                        BookBorrow temp = new BookBorrow();
                        temp.setMaphieumuon(billDetail.getMaphieumuon());
                        temp.setMasach(billDetail.getMasach());
                        temp.setTensach(temp.getTenSachBaseMaSach(sachRepo, billDetail.getMasach()));
                        temp.setSoluong(billDetail.getSoluong());
                        temp.setTendocgia(temp.getTenDocGiaBaseMaPhieuMuon(theThuVienRepo,
                                temp.getMaTheBaseMaPhieuMuon(phieuMuonRepo, billDetail.getMaphieumuon())));
                        temp.setMadocgia(temp.getMaDocGiaBaseMaPhieuMuon(theThuVienRepo,
                                temp.getMaTheBaseMaPhieuMuon(phieuMuonRepo, billDetail.getMaphieumuon())));
                        listBookBorrows.add(temp);
                    }
                }
            }
        }
        Collections.reverse(listBookDisplay);
        model.addAttribute("listBook", listBookDisplay);
        Collections.reverse(listBookBorrows);
        model.addAttribute("listBookBorrow", listBookBorrows);
        return "index";
    }

    // todo: Thêm sách
    @GetMapping("/them-sach")
    public String ThemSach(Model model, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));

        model.addAttribute("title", "Thêm sách");
        model.addAttribute("source", "them-sach");
        model.addAttribute("fragment", "them-sach");
        model.addAttribute("book", new SachEntity());

        List<TheLoaiEntity> listType = theLoaiRepo.findAll();
        model.addAttribute("listType", listType);
        List<NhaXuatBanEntity> listNXB = nhaXuatBanRepo.findAll();
        model.addAttribute("listNXB", listNXB);
        return "index";
    }

    @PostMapping("/them-sach")
    public String ThemSach(Model model, @ModelAttribute SachEntity book, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        // todo: xử lý lưu thông tin sách thêm
        sachRepo.save(book);
        model.addAttribute("title", "Thêm sách");
        model.addAttribute("source", "them-sach");
        model.addAttribute("fragment", "them-sach-thanh-cong");
        // phục vụ tiếp tục thêm sách
        model.addAttribute("book", new SachEntity());
        List<TheLoaiEntity> listType = theLoaiRepo.findAll();
        model.addAttribute("listType", listType);
        List<NhaXuatBanEntity> listNXB = nhaXuatBanRepo.findAll();
        model.addAttribute("listNXB", listNXB);
        return "index";
        // return Optional.ofNullable(sachRepo.save(book))
        // .map(success->"index")
        // .orElse("index");
    }

    // todo: edit sách
    @GetMapping("/edit-book/{id}")
    public String EditBook(Model model, @PathVariable(name = "id") int id, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("source", "edit-book");
        model.addAttribute("fragment", "sua-sach");
        model.addAttribute("title", "Sửa sách");
        
        // todo: tìm sách cần sửa
        SachEntity bookEdit = sachRepo.findByMasach(id);
        model.addAttribute("book", bookEdit);
        List<TheLoaiEntity> listType = theLoaiRepo.findAll();
        model.addAttribute("listType", listType);
        List<NhaXuatBanEntity> listNXB = nhaXuatBanRepo.findAll();
        model.addAttribute("listNXB", listNXB);

        return "index";
    }

    // nhận kết quả edit
    @PostMapping("/edit-book")
    public String EditBook(Model model, @ModelAttribute SachEntity bookEdit, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        // todo: xử lý sách đã sửa
        sachRepo.save(bookEdit);
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Cập nhật sách thành công!");
        model.addAttribute("linkBack", "/quan-ly-sach/tat-ca-sach");
        return "index";
    }

    // todo: delete book
    @GetMapping("/delete-book/{id}")
    public String DeleteBook(Model model, @PathVariable(name = "id") int id, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        // sachRepo.deleteByMasach(id);
        SachEntity temp = sachRepo.findByMasach(id);
        temp.setSoluong(0);
        sachRepo.save(temp);

        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Xóa sách thành công!");
        model.addAttribute("linkBack", "/quan-ly-sach/tat-ca-sach");
        return "index";
    }

    // todo: Tìm sách
    @PostMapping("/tim-sach")
    public String TimSach(Model model, @RequestParam(name = "masach", defaultValue = "") String masach,
            @RequestParam(name = "tensach", defaultValue = "") String tensach,
            @RequestParam(name = "tacgia", defaultValue = "") String tacgia, HttpSession session) {
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Tìm sách");
        model.addAttribute("source", "tim-sach");
        model.addAttribute("fragment", "tim-sach");

        List<SachEntity> listBook = new ArrayList<SachEntity>();
        //todo: cả 3 trường đều rỗng
        if (masach.equals("") && tensach.equals("") && tacgia.equals("")) {
            listBook = sachRepo.findAll();
        }else{
            //todo: mã sách rỗng -> tìm theo tên sách và tác giả
            if (masach.equals("")) {
                if (!tensach.equals("") && tacgia.equals("")) {
                    listBook = sachRepo.findByTensachLike("%" + tensach + "%");
                } else if (tensach.equals("") && !tacgia.equals("")) {
                    listBook = sachRepo.findByTacgiaLike("%" + tacgia + "%");
                }else {
                    // cả 2 tồn tại
                    listBook = sachRepo.findByTensachLikeAndTacgiaLikeAllIgnoreCase("%" + tensach + "%", "%" + tacgia + "%");
                }
            } else {
                //todo: mã sách tồn tại
                // làm rỗng tên sách và tác giả vì ưu tiên mã sách
                tensach = "";
                tacgia = "";
                SachEntity book = sachRepo.findByMasach(Integer.parseInt(masach));
                if (book != null) {
                    listBook.add(book);
                }
            }
        }
        //todo: xử lý dữ liệu tìm được
        List<BookDisplay> listBookDisplay = new ArrayList<BookDisplay>();
        for (SachEntity i : listBook) {
            BookDisplay temp = new BookDisplay();
            temp.setMasach(i.getMasach());
            temp.setTensach(i.getTensach());
            temp.setTacgia(i.getTacgia());
            temp.setTentheloai(i.getTenTheLoai(theLoaiRepo, i.getMatheloai()));
            temp.setTennhaxuatban(i.getTenNhaXuatBan(nhaXuatBanRepo, i.getManhaxuatban()));
            temp.setSoluong(i.getSoluong());
            listBookDisplay.add(temp);
        }
        Collections.reverse(listBookDisplay);
        model.addAttribute("listBook", listBookDisplay);
        model.addAttribute("size", listBookDisplay.size());
        model.addAttribute("masach", masach);
        model.addAttribute("tensach", tensach);
        model.addAttribute("tacgia", tacgia);
        return "index";
    }

}
