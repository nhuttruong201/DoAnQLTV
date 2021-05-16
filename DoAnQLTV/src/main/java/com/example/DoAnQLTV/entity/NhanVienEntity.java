package com.example.DoAnQLTV.entity;


import javax.persistence.*;

import com.example.DoAnQLTV.repository.NhanVienRepo;

@Entity
@Table(name = "nhanvien")
public class NhanVienEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manhanvien;
    private String hoten;
    private int namsinh;
    private String gioitinh;
    private String sodienthoai;
    private String diachi;
    private String machucvu;


    public String getTenChucVu(){
        if(this.getMachucvu().equals("cvql")){
            return "Quản lý";
        }else if(this.getMachucvu().equals("cvtt")){
            return "Thủ thư";
        }else{
            return "Chưa xác định";
        }
    }
    

    @Override
    public String toString() {
        return "{" +
            " manhanvien='" + getManhanvien() + "'" +
            ", hoten='" + getHoten() + "'" +
            ", namsinh='" + getNamsinh() + "'" +
            ", gioitinh='" + getGioitinh() + "'" +
            ", sodienthoai='" + getSodienthoai() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", machucvu='" + getMachucvu() + "'" +
            "}";
    }
    



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


    public int getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(int manhanvien) {
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

}
