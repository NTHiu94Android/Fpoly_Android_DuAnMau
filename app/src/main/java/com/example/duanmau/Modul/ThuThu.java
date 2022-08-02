package com.example.duanmau.Modul;

public class ThuThu {
    private String maThuThu;
    private String tenThuThu;
    private String matKhau;

    public ThuThu(String maThuThu, String tenThuThu, String matKhau) {
        this.maThuThu = maThuThu;
        this.tenThuThu = tenThuThu;
        this.matKhau = matKhau;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getTenThuThu() {
        return tenThuThu;
    }

    public void setTenThuThu(String tenThuThu) {
        this.tenThuThu = tenThuThu;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
