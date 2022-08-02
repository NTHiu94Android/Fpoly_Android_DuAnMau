package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.Sach;

import java.util.ArrayList;

public class SachDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public SachDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    //Lấy all sách
    public ArrayList<Sach> getAllSach(){
        ArrayList<Sach> listSach = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SACH", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                int masach = cursor.getInt(0);
                String tensach = cursor.getString(1);
                double giathue = cursor.getDouble(2);
                int maloaisach = cursor.getInt(3);
                listSach.add(new Sach(masach, tensach, giathue, maloaisach));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listSach;
    }
    //Lay ten sach
    public String getNameSach(int masachtim) {
        ArrayList<Sach> listSach = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SACH WHERE MASACH =?", new String[]{String.valueOf(masachtim)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int masach = cursor.getInt(0);
                String tensach = cursor.getString(1);
                double giathue = cursor.getDouble(2);
                int maloaisach = cursor.getInt(3);
                listSach.add(new Sach(masach, tensach, giathue, maloaisach));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listSach.get(0).getTenSach();
    }
    //Thêm sách
    public void insertSach(Sach sach){
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();
        values.put("TENSACH", sach.getTenSach());
        values.put("GIATHUE", sach.getGiaThue());
        values.put("MALOAISACH", sach.getMaLoaiSach());
        db.insert("SACH", null, values);
    }
    //Sửa sách
    public void updateSach(Sach sach){
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();
        values.put("TENSACH", sach.getTenSach());
        values.put("GIATHUE", sach.getGiaThue());
        values.put("MALOAISACH", sach.getMaLoaiSach());
        db.update("SACH", values, "MASACH=?", new String[]{String.valueOf(sach.getMaSach())});
    }
    //Xóa sách
    public void deleteSach(int masach){
        db = dbHelper.getWritableDatabase();
        db.delete("SACH", "MASACH=?", new String[]{String.valueOf(masach)});
    }
}
