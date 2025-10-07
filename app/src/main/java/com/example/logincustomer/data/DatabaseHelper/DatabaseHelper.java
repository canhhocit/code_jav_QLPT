package com.example.logincustomer.data.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "QLPT.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng Phòng trọ
        db.execSQL("CREATE TABLE PhongTro (" +
                "idphong INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenphong TEXT, " +
                "songuoi INTEGER, " +
                "gia REAL, " +
                "idhopdong INTEGER, " +
                "FOREIGN KEY (idhopdong) REFERENCES HopDong(idhopdong))");

        // Bảng Khách thuê
        db.execSQL("CREATE TABLE KhachThue (" +
                "idkhach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoten TEXT, " +
                "gioitinh TEXT, " +
                "ngaysinh TEXT, " +
                "sdt TEXT, " +
                "diachi TEXT, " +
                "idphong INTEGER, " +
                "FOREIGN KEY (idphong) REFERENCES PhongTro(idphong))");

        // Bảng Hợp đồng
        db.execSQL("CREATE TABLE HopDong (" +
                "idhopdong INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cccd TEXT, " +
                "ngaykyhopdong TEXT, " +
                "idphong INTEGER, " +
                "idkhach INTEGER, " +
                "FOREIGN KEY (idphong) REFERENCES PhongTro(idphong), " +
                "FOREIGN KEY (idkhach) REFERENCES KhachThue(idkhach))");

        // Bảng Thu chi
        db.execSQL("CREATE TABLE ThuChi (" +
                "idthuchi INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenthuchi TEXT, " +
                "sotien real, " +
                "loai TEXT, " +
                "ngaythuchi Date)");

        // Bảng Tài khoản
        db.execSQL("CREATE TABLE TaiKhoan (" +
                "idTK INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "pass TEXT, " +
                "phanloai TEXT)");

        // Bảng Hóa đơn
        db.execSQL("CREATE TABLE HoaDon (" +
                "idhoadon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idphong INTEGER, " +
                "ngaytaohdon DATE, " +
                "ghichu TEXT, " +
                "image TEXT, " +
                "tongtien REAL, " +
                "FOREIGN KEY (idphong) REFERENCES PhongTro(idphong))");

        // Bảng Chi tiết hóa đơn
        db.execSQL("CREATE TABLE ChiTietHoaDon (" +
                "idchitiethoadon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idhoadon INTEGER, " +
                "tendichvu TEXT, " +
                "sodiencu INTEGER, " +
                "sodienmoi INTEGER, " +
                "sonuoccu INTEGER, " +
                "sonuocmoi INTEGER, " +
                "sosudung INTEGER, " +
                "thanhtien INTEGER, " +
                "FOREIGN KEY (idhoadon) REFERENCES HoaDon(idhoadon))");

        // Bảng Dịch vụ con
        db.execSQL("CREATE TABLE DichVuCon (" +
                "iddichvucon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idchitiethoadon INTEGER, " +
                "tendichvu TEXT, " +
                "giatien REAL, " +
                "FOREIGN KEY (idchitiethoadon) REFERENCES ChiTietHoaDon(idchitiethoadon))");

        // Bảng giá mặc định (lưu giá điện & nước)
        db.execSQL("CREATE TABLE GiaMacDinh (" +
                "id INTEGER PRIMARY KEY CHECK (id = 1), " +
                "giadien REAL, " +
                "gianuoc REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DichVuCon");
        db.execSQL("DROP TABLE IF EXISTS ChiTietHoaDon");
        db.execSQL("DROP TABLE IF EXISTS HoaDon");
        db.execSQL("DROP TABLE IF EXISTS ThuChi");
        db.execSQL("DROP TABLE IF EXISTS HopDong");
        db.execSQL("DROP TABLE IF EXISTS KhachThue");
        db.execSQL("DROP TABLE IF EXISTS PhongTro");
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan");
        db.execSQL("DROP TABLE IF EXISTS GiaMacDinh");
        onCreate(db);
    }
}
