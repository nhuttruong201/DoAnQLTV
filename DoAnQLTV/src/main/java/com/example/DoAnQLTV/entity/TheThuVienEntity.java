package com.example.DoAnQLTV.entity;

import java.sql.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "thethuvien")
public class TheThuVienEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mathe;
    private String hoten;
    private String gioitinh;
    private String sodienthoai;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date hansudung;
    private String diachi;
    private String matrangthai;

   
    public String getTenTrangThai(){
        if(this.matrangthai.equals("lock")){
            return "OFF";
        }else{
            return "ON";
        }
    }

    // public String getTenTrangThai(TrangThaiTheRepo trangThaiTheRepo, String matrangthai){
    //     TrangThaiTheEntity trangthai = trangThaiTheRepo.findByMatrangthai(matrangthai);
    //     return trangthai.getTentrangthai();
    // }




    @Override
    public String toString() {
        return "{" +
            " mathe='" + getMathe() + "'" +
            ", hoten='" + getHoten() + "'" +
            ", gioitinh='" + getGioitinh() + "'" +
            ", sodienthoai='" + getSodienthoai() + "'" +
            ", hansudung='" + getHansudung() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", matrangthai='" + getMatrangthai() + "'" +
            "}";
    }

   

    public String getDiachi() {
        return this.diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
   

    public int getMathe() {
        return mathe;
    }

    public void setMathe(int mathe) {
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

    public Date getHansudung() {
        return hansudung;
    }

    public void setHansudung(Date hansudung) {
        this.hansudung = hansudung;
    }
    
    public String getMatrangthai() {
        return matrangthai;
    }

    public void setMatrangthai(String matrangthai) {
        this.matrangthai = matrangthai;
    }
}
