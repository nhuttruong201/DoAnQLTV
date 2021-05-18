package com.example.DoAnQLTV.repository;

import java.util.List;

import com.example.DoAnQLTV.entity.SachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SachRepo extends JpaRepository<SachEntity, Long> {
    List<SachEntity> findByTensachLike(String name);
    SachEntity findByMasach(int id);
    void deleteByMasach(int id);
    List<SachEntity> findByMasachAndTensachLikeAndTacgiaLike(int masach, String tensach, String tacgia);
    List<SachEntity> findByTensachLikeAndTacgiaLike(String tensach, String tacgia);
    List<SachEntity> findByTacgiaLike(String tacgia);
}
