package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.PhieuMuonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonRepo extends JpaRepository<PhieuMuonEntity, Integer> {
}
