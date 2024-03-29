package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.NhanVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NhanVienRepo extends JpaRepository<NhanVienEntity, Long> {
    List<NhanVienEntity> findByHotenLike(String hoten);
    List<NhanVienEntity> findBySodienthoai(String sodienthoai);
    List<NhanVienEntity> findByHotenLikeAndSodienthoaiLike(String hoten, String sodienthoai);
    List<NhanVienEntity> findByHotenLikeAndSodienthoaiLikeAndManhanvien(String hoten, String sodienthoai, int manhanvien);
    NhanVienEntity findByManhanvien(int manhanvien);
    
}
