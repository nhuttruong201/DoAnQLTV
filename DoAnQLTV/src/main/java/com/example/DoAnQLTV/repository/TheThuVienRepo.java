package com.example.DoAnQLTV.repository;

import java.util.List;

import com.example.DoAnQLTV.entity.TheThuVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheThuVienRepo extends JpaRepository<TheThuVienEntity, Long> {
    
    List<TheThuVienEntity> findByMatrangthai(String matrangthai);
    TheThuVienEntity findByMathe(int id);
    List<TheThuVienEntity> findByHotenLikeAndSodienthoaiLike(String hoten, String sdt);
    List<TheThuVienEntity> findByHotenLikeAndSodienthoaiLikeAndMathe(String hoten, String sdt, int mathe);
    TheThuVienEntity findBySodienthoai(String sdt); 
    List<TheThuVienEntity> findByHotenLike(String hoten);
    
}
