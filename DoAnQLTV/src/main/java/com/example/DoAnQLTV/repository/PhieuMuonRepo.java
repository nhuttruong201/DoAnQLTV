package com.example.DoAnQLTV.repository;

import java.sql.Date;
import java.util.List;

import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonRepo extends JpaRepository<PhieuMuonEntity, Integer> {
    PhieuMuonEntity findByMaphieumuon(int maphieumuon);
    List<PhieuMuonEntity> findByTrangthai(int trangthai);
    // List<PhieuMuonEntity> findDistinctMatheByTrangthai(int trangthai);
    List<PhieuMuonEntity> findByMathe(int mathe);
    List<PhieuMuonEntity> findByMatheAndTrangthai(int mathe, int trangthai);
    List<PhieuMuonEntity> findByMatheAndNgaymuon(int mathe, Date ngaymuon);
    List<PhieuMuonEntity> findByNgaymuon(Date ngaymuon);
    
    
    
}
