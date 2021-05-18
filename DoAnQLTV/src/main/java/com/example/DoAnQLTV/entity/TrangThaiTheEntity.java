package com.example.DoAnQLTV.entity;

import javax.persistence.*;

@Entity
@Table(name = "trangthaithe")
public class TrangThaiTheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String matrangthai;
    private String tentrangthai;

    @Override
    public String toString() {
        return "TrangThaiTheEntity{" +
                "matrangthai='" + matrangthai + '\'' +
                ", tentrangthai='" + tentrangthai + '\'' +
                '}';
    }

    public String getMatrangthai() {
        return matrangthai;
    }

    public void setMatrangthai(String matrangthai) {
        this.matrangthai = matrangthai;
    }

    public String getTentrangthai() {
        return tentrangthai;
    }

    public void setTentrangthai(String tentrangthai) {
        this.tentrangthai = tentrangthai;
    }
}
