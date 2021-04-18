package com.example.DoAnQLTV.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chitietphieumuon")
public class ChiTietPhieuMuonEntity {

    @EmbeddedId
    private Fk fk;
    
    private int soluong;

    @Override
    public String toString() {
        return "ChiTietPhieuMuonEntity{" +
                "fk=" + fk +
                ", soluong=" + soluong +
                '}';
    }

    public Fk getFk() {
        return fk;
    }

    public void setFk(Fk fk) {
        this.fk = fk;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Embeddable
    public static class Fk implements Serializable{
       private static final long serialVersionUID = 1L;
        private int maphieumuon;
        private String masach;

        @Override
        public String toString() {
            return "Fk{" +
                    "maphieumuon='" + maphieumuon + '\'' +
                    ", masach='" + masach + '\'' +
                    '}';
        }

        public int getMaphieumuon() {
            return maphieumuon;
        }

        public void setMaphieumuon(int maphieumuon) {
            this.maphieumuon = maphieumuon;
        }

        public String getMasach() {
            return masach;
        }

        public void setMasach(String masach) {
            this.masach = masach;
        }
    }





}
