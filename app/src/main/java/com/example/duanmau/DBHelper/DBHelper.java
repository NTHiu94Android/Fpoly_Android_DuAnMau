package com.example.duanmau.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private String sql = "";
    public DBHelper(Context context) {
        super(context, "DuAnMau.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sql = "CREATE TABLE LOAISACH (MALOAISACH INTEGER PRIMARY KEY AUTOINCREMENT, TENLOAISACH TEXT)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE SACH (MASACH INTEGER PRIMARY KEY AUTOINCREMENT, TENSACH TEXT, " +
                "GIATHUE DOUBLE, MALOAISACH INTEGER, FOREIGN KEY (MALOAISACH) REFERENCES LOAISACH(MALOAISACH))";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE THANHVIEN (MATHANHVIEN INTEGER PRIMARY KEY AUTOINCREMENT, TENTHANHVIEN TEXT, NAMSINH TEXT)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE THUTHU (MATHUTHU TEXT PRIMARY KEY, TENTHUTHU TEXT, MATKHAU TEXT)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE PHIEUMUON (MAPHIEUMUON INTEGER PRIMARY KEY AUTOINCREMENT, NGAYMUON TEXT, TRANGTHAI TEXT, TIENTHUE DOUBLE, " +
                "MASACH INTEGER, MATHUTHU TEXT, MATHANHVIEN INTEGER, " +
                "FOREIGN KEY (MASACH) REFERENCES SACH(MASACH), " +
                "FOREIGN KEY (MATHUTHU) REFERENCES THUTHU(MATHUTHU), " +
                "FOREIGN KEY (MATHANHVIEN) REFERENCES THANHVIEN(MATHANHVIEN))";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO THUTHU VALUES('admin', 'Nguyen Trong Hieu', 'admin'), ('thuthu1', 'thuthu1', 'thuthu1')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO LOAISACH(TENLOAISACH) VALUES('Lap trinh'), ('Ngoai ngu')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO SACH(TENSACH, GIATHUE, MALOAISACH) VALUES('Java', 1000, 1), ('English', 1200, 2)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO THANHVIEN(TENTHANHVIEN, NAMSINH) VALUES('Nguyen Van A', '15/03/1992'), ('Tran Van B', '02/10/1996')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO PHIEUMUON(NGAYMUON, TRANGTHAI, TIENTHUE, MASACH, MATHUTHU, MATHANHVIEN) " +
                "VALUES('15/10/2021', '(Pain)', 1200, 1, 'admin', 1), ('15/5/2022', '(Unpain)', 1000, 2, 'admin', 2)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sql = "DROP TABLE IF EXISTS LOAISACH";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS SACH";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS THANHVIEN";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS THUTHU";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS PHIEUMUON";
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);
    }
}
