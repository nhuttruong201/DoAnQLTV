package com.example.DoAnQLTV.entity;

import javax.persistence.*;


@Entity
@Table(name = "chitietphieumuon")
public class ChiTietPhieuMuonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mactpm;
    private int maphieumuon;
    private int masach;
    private int soluong;


    @Override
    public String toString() {
        return "{" +
            " maphieumuon='" + getMaphieumuon() + "'" +
            ", masach='" + getMasach() + "'" +
            ", soluong='" + getSoluong() + "'" +
            "}";
    }


    public int getMaphieumuon() {
        return this.maphieumuon;
    }

    public void setMaphieumuon(int maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

    public int getMasach() {
        return this.masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public int getSoluong() {
        return this.soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }


    // @EmbeddedId
    // private Fk fk;
    // private int soluong;

    // @Override
    // public String toString() {
    //     return "ChiTietPhieuMuonEntity{" +
    //             "fk=" + fk +
    //             ", soluong=" + soluong +
    //             '}';
    // }

    // public Fk getFk() {
    //     return fk;
    // }

    // public void setFk(Fk fk) {
    //     this.fk = fk;
    // }

    // public int getSoluong() {
    //     return soluong;
    // }

    // public void setSoluong(int soluong) {
    //     this.soluong = soluong;
    // }

    // @Embeddable
    // public static class Fk implements Serializable{
    //    private static final long serialVersionUID = 1L;
       
    //     private int maphieumuon;
    //     private int masach;

    //     @Override
    //     public String toString() {
    //         return "Fk{" +
    //                 "maphieumuon='" + maphieumuon + '\'' +
    //                 ", masach='" + masach + '\'' +
    //                 '}';
    //     }

    //     public int getMaphieumuon() {
    //         return maphieumuon;
    //     }

    //     public void setMaphieumuon(int maphieumuon) {
    //         this.maphieumuon = maphieumuon;
    //     }

    //     public int getMasach() {
    //         return masach;
    //     }

    //     public void setMasach(int masach) {
    //         this.masach = masach;
    //     }
    // }



}
