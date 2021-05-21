package com.example.DoAnQLTV.controller;

import java.util.List;

import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import com.example.DoAnQLTV.entity.SachEntity;
import com.example.DoAnQLTV.entity.TheThuVienEntity;
import com.example.DoAnQLTV.repository.ChiTietPhieuMuonRepo;
import com.example.DoAnQLTV.repository.PhieuMuonRepo;
import com.example.DoAnQLTV.repository.SachRepo;
import com.example.DoAnQLTV.repository.TheThuVienRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired private SachRepo sachRepo;
    @Autowired private ChiTietPhieuMuonRepo ctpmRepo;
    @Autowired private PhieuMuonRepo phieuMuonRepo;
    @Autowired private TheThuVienRepo theThuVienRepo;


    @GetMapping("/api/list-book")
    public List<SachEntity> ListBook(){
        List<SachEntity> listBook = sachRepo.findAll();
        return listBook;
    }
    @GetMapping("/api/search-book/{id}")
    public SachEntity SearchBook(@PathVariable(name = "id") int id){
        SachEntity book = sachRepo.findByMasach(id);
        return book;
    }
    @GetMapping("/api/search-bill-detail/{id}")
    public List<ChiTietPhieuMuonEntity> SearchBillDetail(@PathVariable(name = "id") int id){
        List<ChiTietPhieuMuonEntity> listBillDetail = ctpmRepo.findByMaphieumuon(id);
        return listBillDetail;
    }

    @GetMapping("/api/search-bill/{id-card}")
    public List<PhieuMuonEntity> SearchBill(@PathVariable(name = "id-card") int idCard){
        List<PhieuMuonEntity> listBill = phieuMuonRepo.findByMathe(idCard);
        return listBill;
    }

    @GetMapping("/api/search-reader/{id}")
    public TheThuVienEntity SearchReader(@PathVariable(name = "id") int id){
        TheThuVienEntity card = theThuVienRepo.findByMathe(id);
        return card;
    }

    
}
