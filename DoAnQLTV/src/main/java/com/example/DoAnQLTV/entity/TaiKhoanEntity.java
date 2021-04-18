package com.example.DoAnQLTV.entity;

import javax.persistence.*;

@Entity
@Table(name = "taikhoan")
public class TaiKhoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tentaikhoan;
    private String matkhau;
    private String manhanvien;
    private String maquyenhan;

    public String getMaquyenhan() {
        return this.maquyenhan;
    }

    public void setMaquyenhan(String maquyenhan) {
        this.maquyenhan = maquyenhan;
    }


    @Override
    public String toString() {
        return "{" +
            " tentaikhoan='" + getTentaikhoan() + "'" +
            ", matkhau='" + getMatkhau() + "'" +
            ", manhanvien='" + getManhanvien() + "'" +
            ", maquyenhan='" + getMaquyenhan() + "'" +
            "}";
    }
  

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }
}
