package com.example.DoAnQLTV.service;

import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;

import org.springframework.stereotype.Service;

@Service
public class BookBorrow {
    
    private int maphieumuon;
    private int masach;
    private String tensach;
    private int soluong;
    private int madocgia;
    private String tendocgia;




    public String getTenSachBaseMaSach(SachRepo sachRepo, int masach){
        SachEntity book = sachRepo.findByMasach(masach);
        return book.getTensach();
    }

    public int getMaTheBaseMaPhieuMuon(PhieuMuonRepo phieuMuonRepo, int maphieumuon){
        PhieuMuonEntity bill = phieuMuonRepo.findByMaphieumuon(maphieumuon);
        return bill.getMathe();
    }

    public String getTenDocGiaBaseMaPhieuMuon(TheThuVienRepo theThuVienRepo, int mathe){
        TheThuVienEntity card = theThuVienRepo.findByMathe(mathe);
        return card.getHoten();
    }

    public int getMaDocGiaBaseMaPhieuMuon(TheThuVienRepo theThuVienRepo, int mathe){
        TheThuVienEntity card = theThuVienRepo.findByMathe(mathe);
        return card.getMathe();
    }


    
    public int getMadocgia() {
        return this.madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public int getMaphieumuon() {
        return this.maphieumuon;
    }

    public void setMaphieumuon(int maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

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

    public int getSoluong() {
        return this.soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTendocgia() {
        return this.tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
    }

}
