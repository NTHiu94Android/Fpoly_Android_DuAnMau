package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.PhieuMuon;
import java.util.ArrayList;

public class PhieuMuonDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public PhieuMuonDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //lay toan bo phieu muon
    public ArrayList<PhieuMuon> getAllPhieuMuon(){
        ArrayList<PhieuMuon> listPM = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maPM = cursor.getInt(0);
                String ngayMuon = cursor.getString(1);
                String trangThai = cursor.getString(2);
                double tienThue = cursor.getDouble(3);
                int maSach = cursor.getInt(4);
                String maThuThu = cursor.getString(5);
                int maThanhVien = cursor.getInt(6);
                listPM.add(new PhieuMuon(maPM, ngayMuon, trangThai, tienThue, maSach, maThuThu, maThanhVien));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listPM;
    }
    //Them PM
    public void insertPhieuMuon(PhieuMuon phieuMuon){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NGAYMUON", String.valueOf(phieuMuon.getNgayMuon()));
        values.put("TRANGTHAI", phieuMuon.getTrangThai());
        values.put("TIENTHUE", phieuMuon.getTienThue());
        values.put("MASACH", phieuMuon.getMaSach());
        values.put("MATHUTHU", phieuMuon.getMaThuThu());
        values.put("MATHANHVIEN", phieuMuon.getMaThanhVien());
        db.insert("PHIEUMUON", null, values);
    }
    //Sua PM
    public void updatePhieuMuon(PhieuMuon phieuMuon){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NGAYMUON", String.valueOf(phieuMuon.getNgayMuon()));
        values.put("TRANGTHAI", phieuMuon.getTrangThai());
        values.put("TIENTHUE", phieuMuon.getTienThue());
        values.put("MASACH", phieuMuon.getMaSach());
        values.put("MATHUTHU", phieuMuon.getMaThuThu());
        values.put("MATHANHVIEN", phieuMuon.getMaThanhVien());
        db.update("PHIEUMUON", values, "MAPHIEUMUON=?", new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
    }
    //Xoa PM
    public void deletePhieuMuon(int maPM){
        db = dbHelper.getWritableDatabase();
        db.delete("PHIEUMUON", "MAPHIEUMUON=?", new String[]{String.valueOf(maPM)});
    }
}
