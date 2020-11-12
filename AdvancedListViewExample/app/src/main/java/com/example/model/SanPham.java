package com.example.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int imageId;
    private  String ten;
    private double gia;

    public SanPham() {
    }

    public SanPham(int imageId, String ten, double gia) {
        this.imageId = imageId;
        this.ten = ten;
        this.gia = gia;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
