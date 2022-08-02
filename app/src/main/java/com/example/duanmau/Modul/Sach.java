package com.example.duanmau.Modul;

public class Sach {
    private int maSach;
    private String tenSach;
    private double giaThue;
    private int maLoaiSach;

    public Sach(String tenSach, double giaThue, int maLoaiSach) {
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
    }

    public Sach(int maSach, String tenSach, double giaThue, int maLoaiSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }
}
