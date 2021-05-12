package com.example.DoAnQLTV.entity;


import javax.persistence.*;

import com.example.DoAnQLTV.repository.TheThuVienRepo;

import java.sql.Date;

@Entity
@Table(name = "phieumuon")
public class PhieuMuonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maphieumuon;
    private int mathe;
    private int manhanvien;
    private Date ngaymuon;
    private Date hantra;
    private Date ngaytra;
    private int trangthai;

    public String getHoTenDocGia(TheThuVienRepo theThuVienRepo, int id){
        TheThuVienEntity docgia = theThuVienRepo.findByMathe(id);
        return docgia.getHoten();
    }


    @Override
    public String toString() {
        return "{" +
            " maphieumuon='" + getMaphieumuon() + "'" +
            ", mathe='" + getMathe() + "'" +
            ", manhanvien='" + getManhanvien() + "'" +
            ", ngaymuon='" + getNgaymuon() + "'" +
            ", hantra='" + getHantra() + "'" +
            ", ngaytra='" + getNgaytra() + "'" +
            ", trangthai='" + getTrangthai() + "'" +
            "}";
    }
    

    public Date getHantra() {
        return this.hantra;
    }

    public void setHantra(Date hantra) {
        this.hantra = hantra;
    }


    public int getMaphieumuon() {
        return maphieumuon;
    }

    public void setMaphieumuon(int maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

    public int getMathe() {
        return mathe;
    }

    public void setMathe(int mathe) {
        this.mathe = mathe;
    }

    public int getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(int manhanvien) {
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
