package com.example.DoAnQLTV.entity;

import javax.persistence.*;

import com.example.DoAnQLTV.repository.NhanVienRepo;


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
    private int maquyenhan;
    private String status;


    public String getHoTen(NhanVienRepo nhanVienRepo){
        NhanVienEntity nv = nhanVienRepo.findByManhanvien(this.manhanvien);
        return nv.getHoten();
    }

    public String getTenQuyenHan(){
        if(this.maquyenhan==0){
            return "Staff";
        }else if(this.maquyenhan==1){
            return "Admin";
        }else{
            return "Undefined";
        }
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
            ", status='" + getStatus() + "'" +
            "}";
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaquyenhan() {
        return this.maquyenhan;
    }

    public void setMaquyenhan(int maquyenhan) {
        this.maquyenhan = maquyenhan;
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
