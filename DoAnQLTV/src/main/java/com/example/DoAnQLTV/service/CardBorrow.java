package com.example.DoAnQLTV.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

@Service
public class CardBorrow {
   
    private int mathe;
    private String hoten;
    private int maphieumuon;
    private String tensach;
    private Date ngaymuon;
    private Date hantra;


    public int getMathe() {
        return this.mathe;
    }

    public void setMathe(int mathe) {
        this.mathe = mathe;
    }

    public String getHoten() {
        return this.hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getMaphieumuon() {
        return this.maphieumuon;
    }

    public void setMaphieumuon(int maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

    public String getTensach() {
        return this.tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public Date getNgaymuon() {
        return this.ngaymuon;
    }

    public void setNgaymuon(Date ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public Date getHantra() {
        return this.hantra;
    }

    public void setHantra(Date ngaytra) {
        this.hantra = ngaytra;
    }

}
