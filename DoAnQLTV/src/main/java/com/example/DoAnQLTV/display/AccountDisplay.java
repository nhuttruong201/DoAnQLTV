package com.example.DoAnQLTV.display;

import com.example.DoAnQLTV.entity.NhanVienEntity;
import com.example.DoAnQLTV.repository.NhanVienRepo;

import org.springframework.stereotype.Service;

@Service
public class AccountDisplay {
    private int id;
    private String tentaikhoan;
    private String matkhau;
    private String hoten;
    private String email;
    private String tenquyenhan;


    


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTentaikhoan() {
        return this.tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return this.matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return this.hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenquyenhan() {
        return this.tenquyenhan;
    }

    public void setTenquyenhan(String tenquyenhan) {
        this.tenquyenhan = tenquyenhan;
    }

}
