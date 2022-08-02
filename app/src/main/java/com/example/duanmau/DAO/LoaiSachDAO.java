package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.LoaiSach;
import com.example.duanmau.Modul.Sach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }
    //Lấy loại sách
    public ArrayList<LoaiSach> getAllLoaiSach(){
        db = dbHelper.getReadableDatabase();
        ArrayList<LoaiSach> listLoaiSach = new ArrayList<>();
        String sql = "SELECT * FROM LOAISACH";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                int maloai = cursor.getInt(0);
                String tenloai = cursor.getString(1);
                LoaiSach loaiSach = new LoaiSach(maloai, tenloai);
                listLoaiSach.add(loaiSach);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listLoaiSach;
    }
    //Lay ten loai sach theo ma
    public String getNameBook(int maloaisach){
        ArrayList<LoaiSach> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH WHERE MALOAISACH =?", new String[]{String.valueOf(maloaisach)});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maLoaiSach = cursor.getInt(0);
                String tenLoaiSach = cursor.getString(1);
                list.add(new LoaiSach(maLoaiSach, tenLoaiSach));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list.get(0).getTenLoaiSach();
    }
    //Thêm loại sách
    public void insertLoaiSach(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();
        values.put("TENLOAISACH", loaiSach.getTenLoaiSach());
        db.insert("LOAISACH", null, values);
    }
    //Sửa loại sách
    public void updateLoaiSach(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();
        values.put("TENLOAISACH", loaiSach.getTenLoaiSach());
        db.update("LOAISACH", values, "MALOAISACH=?", new String[]{String.valueOf(loaiSach.getMaLoaiSach())});
    }
    //Xóa loại sách
    public void deleteLoaiSach(int maLS){
        db = dbHelper.getWritableDatabase();
        db.delete("LOAISACH", "MALOAISACH=?", new String[]{String.valueOf(maLS)});
    }
}
