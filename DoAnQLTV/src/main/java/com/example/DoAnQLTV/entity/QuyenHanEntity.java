package com.example.DoAnQLTV.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jdk.jfr.Name;

@Entity
@Table(name = "quyenhan")
public class QuyenHanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int maquyenhan;
    private String tenquyenhan;

    @Override
    public String toString() {
        return "{" +
            " maquyenhan='" + getMaquyenhan() + "'" +
            ", tenquyenhan='" + getTenquyenhan() + "'" +
            "}";
    }

    public int getMaquyenhan() {
        return this.maquyenhan;
    }

    public void setMaquyenhan(int maquyenhan) {
        this.maquyenhan = maquyenhan;
    }

    public String getTenquyenhan() {
        return this.tenquyenhan;
    }

    public void setTenquyenhan(String tenquyenhan) {
        this.tenquyenhan = tenquyenhan;
    }

}
