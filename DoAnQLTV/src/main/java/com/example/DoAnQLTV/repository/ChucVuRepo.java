package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.ChucVuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepo extends JpaRepository<ChucVuEntity, String> {
}
