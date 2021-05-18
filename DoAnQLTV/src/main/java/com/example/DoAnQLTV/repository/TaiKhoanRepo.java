package com.example.DoAnQLTV.repository;

import java.util.List;

import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaiKhoanRepo extends JpaRepository<TaiKhoanEntity, Long> {
    TaiKhoanEntity findByEmail(String email);
    TaiKhoanEntity findByTentaikhoan(String tentaikhoan);
    TaiKhoanEntity findById(int id);
    List<TaiKhoanEntity> findByStatus(String status);
    List<TaiKhoanEntity> findByTentaikhoanLikeAndEmailLike(String tentaikhoan, String email);
    List<TaiKhoanEntity> findByManhanvien(int manhanvien);
    List<TaiKhoanEntity> findByTentaikhoanLikeAndEmailLikeAndManhanvien(String tentaikhoan, String email, int manhanvien);
}
