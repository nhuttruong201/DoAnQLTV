package com.example.DoAnQLTV.entity;
import javax.persistence.*;

import com.example.DoAnQLTV.repository.NhaXuatBanRepo;
import com.example.DoAnQLTV.repository.TheLoaiRepo;

@Entity
@Table(name = "sach")
public class SachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int masach;
    private String tensach;
    private String tacgia;
    private int namxuatban;
    private String manhaxuatban;
    private String matheloai;
    private int soluong;

    
    public String getTenTheLoai(TheLoaiRepo theLoaiRepo, String matheloai){
        TheLoaiEntity theloai = theLoaiRepo.findByMatheloai(matheloai);
        return theloai.getTentheloai();
    }

    public String getTenNhaXuatBan(NhaXuatBanRepo nhaXuatBanRepo, String manhaxuatban){
        NhaXuatBanEntity nxb = nhaXuatBanRepo.findByManhaxuatban(manhaxuatban);
        return nxb.getTennhaxuatban();
    }

    @Override
    public String toString() {
        return "SachEntity{" +
                "masach='" + masach + '\'' +
                ", tensach='" + tensach + '\'' +
                ", tacgia='" + tacgia + '\'' +
                ", namxuatban=" + namxuatban +
                ", manhaxuatban='" + manhaxuatban + '\'' +
                ", matheloai='" + matheloai + '\'' +
                ", soluong=" + soluong +
                '}';
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getNamxuatban() {
        return namxuatban;
    }

    public void setNamxuatban(int namxuatban) {
        this.namxuatban = namxuatban;
    }

    public String getManhaxuatban() {
        return manhaxuatban;
    }

    public void setManhaxuatban(String manhaxuatban) {
        this.manhaxuatban = manhaxuatban;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
