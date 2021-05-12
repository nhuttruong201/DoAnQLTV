package com.example.DoAnQLTV.entity;

import javax.persistence.*;

@Entity
@Table(name = "taikhoan")
public class TaiKhoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tentaikhoan;
    private String matkhau;
    private int manhanvien;
    private String email;
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
            " id='" + getId() + "'" +
            ", tentaikhoan='" + getTentaikhoan() + "'" +
            ", matkhau='" + getMatkhau() + "'" +
            ", manhanvien='" + getManhanvien() + "'" +
            ", email='" + getEmail() + "'" +
            ", maquyenhan='" + getMaquyenhan() + "'" +
            "}";
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
  

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(int manhanvien) {
        this.manhanvien = manhanvien;
    }
}
