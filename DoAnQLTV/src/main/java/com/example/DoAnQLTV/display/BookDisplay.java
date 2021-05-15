package com.example.DoAnQLTV.display;

import com.example.DoAnQLTV.entity.SachEntity;

import org.springframework.stereotype.Service;

@Service
public class BookDisplay {
    private int masach;
    private String tensach;
    private String tacgia;
    private int namxuatban;
    private String tennhaxuatban;
    private String tentheloai;
    private int soluong;



    public int getMasach() {
        return this.masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return this.tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return this.tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getNamxuatban() {
        return this.namxuatban;
    }

    public void setNamxuatban(int namxuatban) {
        this.namxuatban = namxuatban;
    }

    public String getTennhaxuatban() {
        return this.tennhaxuatban;
    }

    public void setTennhaxuatban(String tennhaxuatban) {
        this.tennhaxuatban = tennhaxuatban;
    }

    public String getTentheloai() {
        return this.tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public int getSoluong() {
        return this.soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
