package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.QuyenHanEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuyenHanRepo extends JpaRepository<QuyenHanEntity, Integer> {
    QuyenHanEntity findByMaquyenhan(int maquyenhan);
    
}
