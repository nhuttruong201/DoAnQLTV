package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.ChiTietPhieuMuonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuMuonRepo extends JpaRepository<ChiTietPhieuMuonEntity, Integer> {
}
