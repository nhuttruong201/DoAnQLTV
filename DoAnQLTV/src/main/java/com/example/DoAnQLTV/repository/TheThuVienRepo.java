package com.example.DoAnQLTV.repository;

import java.sql.Date;
import java.util.List;

import com.example.DoAnQLTV.entity.TheThuVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheThuVienRepo extends JpaRepository<TheThuVienEntity, String> {
    // Date findByHansudung();
    List<TheThuVienEntity> findByMatrangthai(String matrangthai);
}
