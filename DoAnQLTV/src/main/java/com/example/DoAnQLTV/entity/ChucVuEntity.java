package com.example.DoAnQLTV.entity;

import javax.persistence.*;

@Entity
@Table(name = "chucvu")
public class ChucVuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String machucvu;
    private String tenchucvu;

    @Override
    public String toString() {
        return "ChucVuEntity{" +
                "machucvu='" + machucvu + '\'' +
                ", tenchucvu='" + tenchucvu + '\'' +
                '}';
    }

    public String getMachucvu() {
        return machucvu;
    }

    public void setMachucvu(String machucvu) {
        this.machucvu = machucvu;
    }

    public String getTenchucvu() {
        return tenchucvu;
    }

    public void setTenchucvu(String tenchucvu) {
        this.tenchucvu = tenchucvu;
    }
}
