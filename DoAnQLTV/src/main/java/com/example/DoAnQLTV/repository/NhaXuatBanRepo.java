package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.NhaXuatBanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaXuatBanRepo extends JpaRepository<NhaXuatBanEntity, String> {
}
