package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.HoaDon;

import java.util.ArrayList;

public class qlthutien_HoaDonDAO {
    private SQLiteDatabase db;

    public qlthutien_HoaDonDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm hóa đơn mới
    public long insertHoaDon(HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("Idphong", hd.getIdphong());
        values.put("Ngaytaohdon", hd.getNgaytaohdon());
        values.put("Ghichu", hd.getGhichu());
        values.put("Image", hd.getImage());
        values.put("tongtien", hd.getTongtien());
        return db.insert("Hoadon", null, values);
    }

    // Cập nhật hóa đơn
    public int updateHoaDon(HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("Idphong", hd.getIdphong());
        values.put("Ngaytaohdon", hd.getNgaytaohdon());
        values.put("Ghichu", hd.getGhichu());
        values.put("Image", hd.getImage());
        values.put("tongtien", hd.getTongtien());
        return db.update("Hoadon", values, "Idhoadon = ?", new String[]{String.valueOf(hd.getIdhoadon())});
    }

    // Xóa hóa đơn
    public int deleteHoaDon(int idhoadon) {
        return db.delete("Hoadon", "Idhoadon = ?", new String[]{String.valueOf(idhoadon)});
    }

    // Lấy toàn bộ danh sách hóa đơn
    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Hoadon", null);
        if (c.moveToFirst()) {
            do {
                list.add(new HoaDon(
                        c.getInt(0), // Idhoadon
                        c.getInt(1), // Idphong
                        c.getString(2), // Ngaytaohdon
                        c.getString(3), // Ghichu
                        c.getString(4), // Image
                        c.getDouble(5)  // tongtien
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Lấy hóa đơn theo Id phòng (dùng cho sửa / kiểm tra phòng có hóa đơn chưa)
    public HoaDon getHoaDonByIdPhong(int idPhong) {
        Cursor c = db.rawQuery("SELECT * FROM Hoadon WHERE Idphong = ?", new String[]{String.valueOf(idPhong)});
        if (c.moveToFirst()) {
            HoaDon hd = new HoaDon(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getDouble(5)
            );
            c.close();
            return hd;
        }
        c.close();
        return null; // chưa có hóa đơn
    }

    public boolean coHoaDonChoPhong(int idPhong) {
        Cursor c = db.rawQuery("SELECT idhoadon FROM HoaDon WHERE idphong = ?", new String[]{String.valueOf(idPhong)});
        boolean co = c.moveToFirst();
        c.close();
        return co;
    }

}
