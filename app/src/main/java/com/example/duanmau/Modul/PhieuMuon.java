package com.example.duanmau.Modul;

public class PhieuMuon {
    private int maPhieuMuon;
    private String ngayMuon;
    private String trangThai;
    private double tienThue;
    private int maSach;
    private String maThuThu;
    private int maThanhVien;

    public PhieuMuon(String ngayMuon, String trangThai, double tienThue, int maSach, String maThuThu, int maThanhVien) {
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.tienThue = tienThue;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
        this.maThanhVien = maThanhVien;
    }

    public PhieuMuon(int maPhieuMuon, String ngayMuon, String trangThai, double tienThue, int maSach, String maThuThu, int maThanhVien) {
        this.maPhieuMuon = maPhieuMuon;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.tienThue = tienThue;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
        this.maThanhVien = maThanhVien;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTienThue() {
        return tienThue;
    }

    public void setTienThue(double tienThue) {
        this.tienThue = tienThue;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }
}
