package com.example.DoAnQLTV.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phieumuon")
public class PhieuMuonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maphieumuon;
    private String mathe;
    private String manhanvien;
    private Date ngaymuon;
    private Date ngaytra;
    private int trangthai;

    @Override
    public String toString() {
        return "PhieuMuonEntity{" +
                "maphieumuon=" + maphieumuon +
                ", mathe='" + mathe + '\'' +
                ", manhanvien='" + manhanvien + '\'' +
                ", ngaymuon=" + ngaymuon +
                ", ngaytra=" + ngaytra +
                ", trangthai=" + trangthai +
                '}';
    }

    public int getMaphieumuon() {
        return maphieumuon;
    }

    public void setMaphieumuon(int maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

    public String getMathe() {
        return mathe;
    }

    public void setMathe(String mathe) {
        this.mathe = mathe;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public Date getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(Date ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public Date getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(Date ngaytra) {
        this.ngaytra = ngaytra;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
