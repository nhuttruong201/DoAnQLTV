package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.TaiKhoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepo extends JpaRepository<TaiKhoanEntity, Long> {
    TaiKhoanEntity findByEmail(String email);
    TaiKhoanEntity findByTentaikhoan(String tentaikhoan);
    TaiKhoanEntity findById(int id);
}
