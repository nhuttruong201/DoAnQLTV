package com.example.DoAnQLTV.entity;


import javax.persistence.*;

@Entity
@Table(name = "nhanvien")
public class NhanVienEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String manhanvien;
    private String hoten;
    private int namsinh;
    private String gioitinh;
    private String sodienthoai;
    private String diachi;

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMachucvu() {
        return machucvu;
    }

    public void setMachucvu(String machucvu) {
        this.machucvu = machucvu;
    }

    private String machucvu;

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    @Override
    public String toString() {
        return "NhanVienEntity{" +
                "manhanvien='" + manhanvien + '\'' +
                ", hoten='" + hoten + '\'' +
                ", namsinh=" + namsinh +
                ", gioitinh='" + gioitinh + '\'' +
                ", sodienthoai='" + sodienthoai + '\'' +
                ", diachi='" + diachi + '\'' +
                ", machucvu='" + machucvu + '\'' +
                '}';
    }
}
