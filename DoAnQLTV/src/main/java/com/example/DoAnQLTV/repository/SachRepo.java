package com.example.DoAnQLTV.repository;

import java.util.List;

import com.example.DoAnQLTV.entity.SachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SachRepo extends JpaRepository<SachEntity, String> {
    List<SachEntity> findByTensachLike(String name);
}
