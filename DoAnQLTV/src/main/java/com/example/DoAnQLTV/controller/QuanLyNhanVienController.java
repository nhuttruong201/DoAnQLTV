package com.example.DoAnQLTV.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DoAnQLTV.entity.ChucVuEntity;
import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.repository.ChucVuRepo;
import com.example.DoAnQLTV.repository.NhanVienRepo;
import com.example.DoAnQLTV.repository.QuyenHanRepo;
import com.example.DoAnQLTV.repository.TaiKhoanRepo;
import com.example.DoAnQLTV.service.SessionService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class QuanLyNhanVienController {
    
    @Autowired private NhanVienRepo nhanVienRepo;
    @Autowired private QuyenHanRepo quyenHanRepo;
    @Autowired private TaiKhoanRepo taiKhoanRepo;
    @Autowired private ChucVuRepo chucVuRepo;

    //todo: Thống kê
    @GetMapping("/quan-ly-nhan-vien/{type}")
    public String QuanLyNhanVien(HttpSession session, Model model, @PathVariable(name = "type") String type){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Quản lý nhân viên");
        model.addAttribute("source", "quan-ly-nhan-vien");
        model.addAttribute("fragment", "quan-ly-nhan-vien");
        model.addAttribute("type", type);

        List<NhanVienEntity> listNV = new ArrayList<NhanVienEntity>();
        if(type.equals("tat-ca-nhan-vien")){
            listNV = nhanVienRepo.findAll();
        }

        Collections.reverse(listNV);
        model.addAttribute("listNV", listNV);
        
        return "index";
    }

    //todo: thêm nhân viên
    @GetMapping("/them-nhan-vien")
    public String ThemNhanVien(HttpSession session, Model model){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Thêm nhân viên");
        model.addAttribute("source", "them-nhan-vien");
        model.addAttribute("fragment", "them-nhan-vien");
        model.addAttribute("staff", new NhanVienEntity());
        List<ChucVuEntity> listCV = chucVuRepo.findAll();
        model.addAttribute("listCV", listCV);
        return "index";
    }

    @PostMapping("/them-nhan-vien")
    public String ThemNhanVien(HttpSession session, Model model, @ModelAttribute NhanVienEntity newStaff){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));

        model.addAttribute("title", "Thêm nhân viên");
        model.addAttribute("source", "them-nhan-vien");
        // todo: Xử lý lưu nhân viên vào csdl
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        for(var i : listNV){
            // check sdt đã được sử dụng chưa
            if(i.getSodienthoai().equals(newStaff.getSodienthoai())){
                model.addAttribute("fragment", "sdt-da-su-dung");
                model.addAttribute("staff", newStaff);
                List<ChucVuEntity> listCV = chucVuRepo.findAll();
                model.addAttribute("listCV", listCV);
                return "index";
            }
        }
        nhanVienRepo.save(newStaff);
        model.addAttribute("fragment", "them-nhan-vien-thanh-cong");
        model.addAttribute("staff", new NhanVienEntity());
        List<ChucVuEntity> listCV = chucVuRepo.findAll();
        model.addAttribute("listCV", listCV);
        // System.out.println(newStaff);
        return "index";
    }

    //todo: Sửa nhân viên
    @GetMapping("/edit-staff/{id}")
    public String EditStaff(HttpSession session, Model model, @PathVariable(name = "id") int id){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));

        model.addAttribute("title", "Sửa nhân viên");
        model.addAttribute("source", "edit-staff");
        model.addAttribute("fragment", "sua-nhan-vien");
        // phục vụ cho cmb chức vụ
        List<ChucVuEntity> listCV = chucVuRepo.findAll();
        model.addAttribute("listCV", listCV);
        // todo: tìm nhân viên cần sửa
        NhanVienEntity nvEdit = nhanVienRepo.findByManhanvien(id);
        model.addAttribute("staff", nvEdit);
       
        return "index";
    }

    @PostMapping("/edit-staff")
    public String EditStaff(HttpSession session, Model model, @ModelAttribute NhanVienEntity staffEdit){
        // todo: check login - note: truyền HttpSession session
        if (!SessionService.CheckLogin(session)) {
            return "redirect:/login";
        }
        //todo: check admin
        if(!SessionService.CheckAdmin(session, taiKhoanRepo)){
            return "blocked";
        }
        // todo: get tài khoản đang đăng nhập
        String tentaikhoan = SessionService.getSession(session);
        model.addAttribute("currentAccount", SessionService.getQuyenHan(quyenHanRepo, taiKhoanRepo, tentaikhoan));
        model.addAttribute("fullname", SessionService.getFullName(taiKhoanRepo, tentaikhoan, nhanVienRepo));
        model.addAttribute("title", "Sửa nhân viên");

        // todo: check sdt đã sử dụng chưa
        System.out.println(staffEdit.getManhanvien());
        List<NhanVienEntity> listNV = nhanVienRepo.findAll();
        for(var i : listNV){
            if(i.getManhanvien()!=staffEdit.getManhanvien()){
                if(i.getSodienthoai().equals(staffEdit.getSodienthoai())){
                    model.addAttribute("source", "edit-staff");
                    model.addAttribute("fragment", "sdt-da-su-dung");
                    model.addAttribute("staff", staffEdit);
                    List<ChucVuEntity> listCV = chucVuRepo.findAll();
                    model.addAttribute("listCV", listCV);
                    return "index";
                }
            }
        }
        nhanVienRepo.save(staffEdit);
        model.addAttribute("source", "success");
        model.addAttribute("fragment", "success");
        model.addAttribute("alert", "Cập nhật thông tin nhân viên thành công!");
        model.addAttribute("linkBack", "/quan-ly-nhan-vien/tat-ca-nhan-vien");
       
        return "index";
    }
}
