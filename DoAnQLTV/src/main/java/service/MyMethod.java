package service;

import com.example.DoAnQLTV.entity.TheLoaiEntity;
import com.example.DoAnQLTV.repository.TheLoaiRepo;
import org.springframework.stereotype.Service;

@Service
public class MyMethod{
    public static TheLoaiEntity getTheLoai(TheLoaiRepo theLoaiRepo, String matheloai){
        return theLoaiRepo.findByMatheloai(matheloai);
    }
}
