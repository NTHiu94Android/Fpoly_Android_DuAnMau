package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //Lay toan bo thanh vien
    public ArrayList<ThanhVien> getAllThanhVien(){
        ArrayList<ThanhVien> listTV = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maTV = cursor.getInt(0);
                String tenTV = cursor.getString(1);
                String namSinhTV = cursor.getString(2);
                listTV.add(new ThanhVien(maTV, tenTV, namSinhTV));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listTV;
    }

    //Lay ten tv
    public String getNameTV(int mathanhvien){
        ArrayList<ThanhVien> listTV = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN WHERE MATHANHVIEN =?", new String[]{String.valueOf(mathanhvien)});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maTV = cursor.getInt(0);
                String tenTV = cursor.getString(1);
                String namSinhTV = cursor.getString(2);
                listTV.add(new ThanhVien(maTV, tenTV, namSinhTV));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listTV.get(0).getTenThanhVien();
    }

    public void insertThanhVien(ThanhVien tv){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENTHANHVIEN", tv.getTenThanhVien());
        values.put("NAMSINH", tv.getNamSinh());
        db.insert("THANHVIEN", null, values);
    }
    public void updateThanhVien(ThanhVien tv){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENTHANHVIEN", tv.getTenThanhVien());
        values.put("NAMSINH", tv.getNamSinh());
        db.update("THANHVIEN", values, "MATHANHVIEN=?", new String[]{String.valueOf(tv.getMaThanhVien())});
    }
    public void deleteThanhVien(int maTV){
        db = dbHelper.getWritableDatabase();
        db.delete("THANHVIEN", "MATHANHVIEN=?", new String[]{String.valueOf(maTV)});
    }


}
