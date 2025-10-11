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
                "ngaytaohdon TEXT, " +
                "trangthai INTEGER DEFAULT 0, " +
                "ghichu TEXT, " +
                "image_diencu TEXT, " +
                "image_dienmoi TEXT, " +
                "image_nuoccu TEXT, " +
                "image_nuocmoi TEXT, " +
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



        db.execSQL("INSERT INTO PhongTro (tenphong, songuoi, gia) VALUES\n" +
                "('Phòng A1', 2, 2000000),\n" +
                "('Phòng A2', 3, 2500000),\n" +
                "('Phòng B1', 1, 1800000),\n" +
                "('Phòng B2', 2, 2200000),\n" +
                "('Phòng C1', 4, 3000000);");

        db.execSQL("INSERT INTO KhachThue (hoten, gioitinh, ngaysinh, sdt, diachi, idphong) VALUES\n" +
                "('Nguyễn Văn A', 'Nam', '1999-05-10', '0901234567', 'Hà Nội', 1),\n" +
                "('Trần Thị B', 'Nữ', '2000-03-20', '0912345678', 'Đà Nẵng', 2),\n" +
                "('Lê Văn C', 'Nam', '1998-08-15', '0923456789', 'HCM', 3),\n" +
                "('Phạm Thị D', 'Nữ', '2001-11-02', '0934567890', 'Hà Nội', 4),\n" +
                "('Hoàng Văn E', 'Nam', '1997-07-12', '0945678901', 'Huế', 5);");

        db.execSQL("INSERT INTO HopDong (cccd, ngaykyhopdong, idphong, idkhach) VALUES\n" +
                "('012345678901', '2024-01-01', 1, 1),\n" +
                "('012345678902', '2024-02-10', 2, 2),\n" +
                "('012345678903', '2024-03-15', 3, 3),\n" +
                "('012345678904', '2024-04-20', 4, 4),\n" +
                "('012345678905', '2024-05-25', 5, 5);");

        db.execSQL("INSERT INTO ThuChi (tenthuchi, sotien, loai, ngaythuchi) VALUES\n" +
                "('Thu tiền cọc', 500000, 'Thu', '2024-01-05'),\n" +
                "('Mua bóng đèn', 100000, 'Chi', '2024-02-03'),\n" +
                "('Thu điện nước', 300000, 'Thu', '2024-04-12'),\n" +
                "('Sửa ống nước', 150000, 'Chi', '2024-05-12'),\n" +
                "('Thu khác', 200000, 'Thu', '2024-01-12');");

        db.execSQL("INSERT INTO ThuChi (tenthuchi, sotien, loai, ngaythuchi) VALUES\n" +
                "('Thu tiền cọc', 500000, 'Thu', '2025-01-05'),\n" +
                "('Mua bóng đèn', 100000, 'Chi', '2025-02-03'),\n" +
                "('Thu điện nước', 300000, 'Thu', '2025-04-12'),\n" +
                "('Sửa ống nước', 150000, 'Chi', '2025-05-12'),\n" +
                "('Thu khác', 200000, 'Thu', '2025-01-12');");

        db.execSQL("INSERT INTO HoaDon (idphong, ngaytaohdon, trangthai, ghichu, tongtien) VALUES\n" +
                "(1, '2024-04-20', 1, 'Tháng 1', 2500000),\n" +
                "(2, '2024-02-12', 1, 'Tháng 2', 2800000),\n" +
                "(3, '2024-01-12', 1, 'Tháng 3', 1900000),\n" +
                "(4, '2024-03-04', 1, 'Tháng 4', 2200000),\n" +
                "(5, '2024-05-12', 1, 'Tháng 5', 3100000);");

        db.execSQL("INSERT INTO HoaDon (idphong, ngaytaohdon, trangthai, ghichu, tongtien) VALUES\n" +
                "(1, '2025-04-20', 1, 'Tháng 1', 2500000),\n" +
                "(2, '2025-02-12', 1, 'Tháng 2', 2800000),\n" +
                "(3, '2025-01-12', 1, 'Tháng 3', 1900000),\n" +
                "(4, '2025-03-04', 1, 'Tháng 4', 2200000),\n" +
                "(5, '2025-05-12', 1, 'Tháng 5', 3100000);");

        db.execSQL("INSERT INTO ChiTietHoaDon (idhoadon, tendichvu, sodiencu, sodienmoi, sonuoccu, sonuocmoi, sosudung, thanhtien) VALUES\n" +
                "(1, 'Điện', 10, 30, 5, 10, 20, 40000),\n" +
                "(2, 'Nước', 20, 35, 10, 15, 15, 30000),\n" +
                "(3, 'Rác', 0, 0, 0, 0, 0, 20000),\n" +
                "(4, 'Internet', 0, 0, 0, 0, 0, 100000),\n" +
                "(5, 'Điện', 15, 40, 10, 20, 25, 50000);");

        db.execSQL("INSERT INTO DichVuCon (idchitiethoadon, tendichvu, giatien) VALUES\n" +
                "(1, 'Wifi', 100000),\n" +
                "(2, 'Rác', 20000),\n" +
                "(3, 'Giữ xe', 50000),\n" +
                "(4, 'Vệ sinh', 30000),\n" +
                "(5, 'Truyền hình', 40000);\n");

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
