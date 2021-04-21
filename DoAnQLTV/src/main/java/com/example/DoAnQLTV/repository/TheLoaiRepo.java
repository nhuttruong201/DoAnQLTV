package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.TheLoaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheLoaiRepo extends JpaRepository<TheLoaiEntity, String> {
    TheLoaiEntity findByMatheloai(String matheloai);
}
