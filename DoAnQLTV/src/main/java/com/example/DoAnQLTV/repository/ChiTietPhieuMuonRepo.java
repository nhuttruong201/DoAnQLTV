package com.example.DoAnQLTV.repository;

import java.util.List;

import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuMuonRepo extends JpaRepository<ChiTietPhieuMuonEntity, Long> {

    List<ChiTietPhieuMuonEntity> findByMaphieumuon(int maphieumuon);
}
