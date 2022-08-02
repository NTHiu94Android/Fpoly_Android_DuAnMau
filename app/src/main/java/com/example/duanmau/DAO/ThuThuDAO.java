package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.ThuThu;

import java.util.ArrayList;

public class ThuThuDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    //Lay all thu thu
    public ArrayList<ThuThu> getAllThuThu(){
        ArrayList<ThuThu> listTT = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maTT = cursor.getString(0);
                String tenTT = cursor.getString(1);
                String matKhau = cursor.getString(2);
                listTT.add(new ThuThu(maTT, tenTT, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listTT;
    }
    //Lay thu thu theo ten
    public ThuThu getThuThu(String MaTT){
        ArrayList<ThuThu> listTT = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATHUTHU =?", new String[]{MaTT});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String maTT = cursor.getString(0);
                String tenTT = cursor.getString(1);
                String matKhau = cursor.getString(2);
                listTT.add(new ThuThu(maTT, tenTT, matKhau));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return listTT.get(0);
    }
    public void insertThuThu(ThuThu thuThu){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATHUTHU", thuThu.getMaThuThu());
        values.put("TENTHUTHU", thuThu.getTenThuThu());
        values.put("MATKHAU", thuThu.getMatKhau());
        db.insert("THUTHU", null, values);
    }

    public void updateThuThu(ThuThu thuThu){
        db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENTHUTHU", thuThu.getTenThuThu());
        values.put("MATKHAU", thuThu.getMatKhau());
        db.update("THUTHU", values, "MATHUTHU=?", new String[]{thuThu.getMaThuThu()});
    }

    public void deleteThuThu(ThuThu thuthu){
        db = dbHelper.getWritableDatabase();
        db.delete("THUTHU", "MATHUTHU=?", new String[]{thuthu.getMaThuThu()});
    }

    //Dang ky
    public Boolean checkusername(String username) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from THUTHU where MATHUTHU = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Dang nhap
    public Boolean checkusernamepassword(String username, String password){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from THUTHU where MATHUTHU = ? and MATKHAU = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
