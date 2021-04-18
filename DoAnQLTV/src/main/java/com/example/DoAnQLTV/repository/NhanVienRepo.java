package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.NhanVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NhanVienRepo extends JpaRepository<NhanVienEntity, String> {
    List<NhanVienEntity> findByHotenLike(String name);
}