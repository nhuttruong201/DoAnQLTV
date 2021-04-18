package com.example.DoAnQLTV.entity;


import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "thethuvien")
public class TheThuVienEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String mathe;
    private String hoten;
    private String gioitinh;
    private String sodienthoai;
    private String diachi;
    private Date hansudung;
    private int solanphat;
    private String matrangthai;

    @Override
    public String toString() {
        return "TheThuVienEntity{" +
                "mathe='" + mathe + '\'' +
                ", hoten='" + hoten + '\'' +
                ", gioitinh='" + gioitinh + '\'' +
                ", sodienthoai='" + sodienthoai + '\'' +
                ", diachi='" + diachi + '\'' +
                ", hansudung='" + hansudung + '\'' +
                ", solanphat=" + solanphat +
                ", matrangthai='" + matrangthai + '\'' +
                '}';
    }

    public String getMathe() {
        return mathe;
    }

    public void setMathe(String mathe) {
        this.mathe = mathe;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Date getHansudung() {
        return hansudung;
    }

    public void setHansudung(Date hansudung) {
        this.hansudung = hansudung;
    }

    public int getSolanphat() {
        return solanphat;
    }

    public void setSolanphat(int solanphat) {
        this.solanphat = solanphat;
    }

    public String getMatrangthai() {
        return matrangthai;
    }

    public void setMatrangthai(String matrangthai) {
        this.matrangthai = matrangthai;
    }
}
