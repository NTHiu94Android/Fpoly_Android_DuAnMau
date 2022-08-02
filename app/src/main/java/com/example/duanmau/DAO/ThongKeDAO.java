package com.example.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DBHelper.DBHelper;
import com.example.duanmau.Modul.Top10;

import java.util.ArrayList;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;
    private SachDAO sachDAO;
    public ThongKeDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    //Lay 10 sach duoc muon nhieu nhat tu bang phieu muon
    public ArrayList<Top10> getTop10(){
        ArrayList<Top10> list = new ArrayList<>();
        sachDAO = new SachDAO(context);
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT MASACH, COUNT(MASACH) AS SOLUONG FROM PHIEUMUON GROUP BY MASACH ORDER BY SOLUONG DESC LIMIT 10";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String tenSach = sachDAO.getNameSach(Integer.parseInt(cursor.getString(0)));
                list.add(new Top10(tenSach, cursor.getInt(1)));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return list;
    }

    //Lay tong thu nhap trong bang phieu muon
    public double getRevenue(String tuNgay, String denNgay){
        ArrayList<Double> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT SUM(TIENTHUE) AS DOANHTHU FROM PHIEUMUON WHERE NGAYMUON >= ? AND NGAYMUON <= ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay, denNgay});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                list.add(cursor.getDouble(0));
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if(list.size() == 0){
            return 0;
        }else {
            return list.get(0);
        }
    }
}
