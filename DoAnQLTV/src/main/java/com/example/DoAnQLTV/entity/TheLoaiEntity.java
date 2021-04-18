package com.example.DoAnQLTV.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "theloai")
public class TheLoaiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String matheloai;
    private String tentheloai;

    @Override
    public String toString() {
        return "TheLoaiEntity{" +
                "matheloai='" + matheloai + '\'' +
                ", tentheloai='" + tentheloai + '\'' +
                '}';
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }
}
