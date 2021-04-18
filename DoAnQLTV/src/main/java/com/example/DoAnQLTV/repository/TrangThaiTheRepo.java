package com.example.DoAnQLTV.repository;

import com.example.DoAnQLTV.entity.TrangThaiTheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrangThaiTheRepo extends JpaRepository<TrangThaiTheEntity, String> {
}
