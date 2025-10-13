package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.qlthutien_ChiTietHoaDon;

import java.util.ArrayList;
import java.util.List;

public class qlthutien_ChiTietHoaDonDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public qlthutien_ChiTietHoaDonDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert 1 chi tiết
    public long insertChiTiet(qlthutien_ChiTietHoaDon ct) {
        ContentValues values = new ContentValues();
        values.put("idhoadon", ct.getIdhoadon());
        values.put("tendichvu", ct.getTendichvu());
        values.put("sodiencu", ct.getSodiencu());
        values.put("sodienmoi", ct.getSodienmoi());
        values.put("sonuoccu", ct.getSonuoccu());
        values.put("sonuocmoi", ct.getSonuocmoi());
        values.put("sosudung", ct.getSosudung());
        values.put("thanhtien", ct.getThanhtien());
        return db.insert("ChiTietHoaDon", null, values);
    }

    // Lấy danh sách chi tiết theo idhoadon
    public ArrayList<qlthutien_ChiTietHoaDon> getChiTietByHoaDonId(int idHoaDon) {
        ArrayList<qlthutien_ChiTietHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
        if (c.moveToFirst()) {
            do {
                qlthutien_ChiTietHoaDon ct = new qlthutien_ChiTietHoaDon();
                ct.setIdchitiethoadon(c.getInt(c.getColumnIndexOrThrow("idchitiethoadon")));
                ct.setIdhoadon(c.getInt(c.getColumnIndexOrThrow("idhoadon")));
                ct.setTendichvu(c.getString(c.getColumnIndexOrThrow("tendichvu")));
                ct.setSodiencu(c.getInt(c.getColumnIndexOrThrow("sodiencu")));
                ct.setSodienmoi(c.getInt(c.getColumnIndexOrThrow("sodienmoi")));
                ct.setSonuoccu(c.getInt(c.getColumnIndexOrThrow("sonuoccu")));
                ct.setSonuocmoi(c.getInt(c.getColumnIndexOrThrow("sonuocmoi")));
                ct.setSosudung(c.getInt(c.getColumnIndexOrThrow("sosudung")));
                ct.setThanhtien(c.getDouble(c.getColumnIndexOrThrow("thanhtien")));
                list.add(ct);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Xóa theo idhoadon (nếu cần)
    public int deleteByHoaDonId(int idHoaDon) {
        return db.delete("ChiTietHoaDon", "idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
    }
    public List<qlthutien_ChiTietHoaDon> getByHoaDonId(int idHoaDon) {
        List<qlthutien_ChiTietHoaDon> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
        if (c.moveToFirst()) {
            do {
                qlthutien_ChiTietHoaDon ct = new qlthutien_ChiTietHoaDon();
                ct.setIdchitiethoadon(c.getInt(c.getColumnIndexOrThrow("idchitiethoadon")));
                ct.setIdhoadon(c.getInt(c.getColumnIndexOrThrow("idhoadon")));
                ct.setTendichvu(c.getString(c.getColumnIndexOrThrow("tendichvu")));
                ct.setSodiencu(c.getInt(c.getColumnIndexOrThrow("sodiencu")));
                ct.setSodienmoi(c.getInt(c.getColumnIndexOrThrow("sodienmoi")));
                ct.setSonuoccu(c.getInt(c.getColumnIndexOrThrow("sonuoccu")));
                ct.setSonuocmoi(c.getInt(c.getColumnIndexOrThrow("sonuocmoi")));
                ct.setSosudung(c.getInt(c.getColumnIndexOrThrow("sosudung")));
                ct.setThanhtien(c.getInt(c.getColumnIndexOrThrow("thanhtien")));
                list.add(ct);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public int updateSoDienNuoc(qlthutien_ChiTietHoaDon chiTiet) {
        ContentValues values = new ContentValues();
        values.put("sodiencu", chiTiet.getSodiencu());
        values.put("sodienmoi", chiTiet.getSodienmoi());
        values.put("sonuoccu", chiTiet.getSonuoccu());
        values.put("sonuocmoi", chiTiet.getSonuocmoi());
        values.put("sosudung", chiTiet.getSosudung());
        values.put("thanhtien", chiTiet.getThanhtien());

        int rows = db.update(
                "ChiTietHoaDon",
                values,
                "idhoadon = ? AND tendichvu = ?",
                new String[]{String.valueOf(chiTiet.getIdhoadon()), chiTiet.getTendichvu()}
        );

        return rows;
    }

    public qlthutien_ChiTietHoaDon getChiTietHoaDonByIdHoaDonDien(int idHoaDon) {
        db = dbHelper.getReadableDatabase();
        qlthutien_ChiTietHoaDon chiTiet = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM ChiTietHoaDon WHERE idhoadon = ? AND tendichvu = ?",
                new String[]{String.valueOf(idHoaDon), "Điện"}
        );

        if (cursor.moveToFirst()) {
            chiTiet = new qlthutien_ChiTietHoaDon();
            chiTiet.setIdchitiethoadon(cursor.getInt(cursor.getColumnIndexOrThrow("idchitiethoadon")));
            chiTiet.setIdhoadon(cursor.getInt(cursor.getColumnIndexOrThrow("idhoadon")));
            chiTiet.setTendichvu(cursor.getString(cursor.getColumnIndexOrThrow("tendichvu")));
            chiTiet.setSodiencu(cursor.getInt(cursor.getColumnIndexOrThrow("sodiencu")));
            chiTiet.setSodienmoi(cursor.getInt(cursor.getColumnIndexOrThrow("sodienmoi")));
            chiTiet.setSonuoccu(cursor.getInt(cursor.getColumnIndexOrThrow("sonuoccu")));
            chiTiet.setSonuocmoi(cursor.getInt(cursor.getColumnIndexOrThrow("sonuocmoi")));
            chiTiet.setSosudung(cursor.getInt(cursor.getColumnIndexOrThrow("sosudung")));
            chiTiet.setThanhtien(cursor.getInt(cursor.getColumnIndexOrThrow("thanhtien")));
        }

        cursor.close();
        return chiTiet;
    }

    public qlthutien_ChiTietHoaDon getChiTietHoaDonByIdHoaDonNuoc(int idHoaDon) {
        db = dbHelper.getReadableDatabase();
        qlthutien_ChiTietHoaDon chiTiet = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM ChiTietHoaDon WHERE idhoadon = ? AND tendichvu = ?",
                new String[]{String.valueOf(idHoaDon), "Nước"}
        );

        if (cursor.moveToFirst()) {
            chiTiet = new qlthutien_ChiTietHoaDon();
            chiTiet.setIdchitiethoadon(cursor.getInt(cursor.getColumnIndexOrThrow("idchitiethoadon")));
            chiTiet.setIdhoadon(cursor.getInt(cursor.getColumnIndexOrThrow("idhoadon")));
            chiTiet.setTendichvu(cursor.getString(cursor.getColumnIndexOrThrow("tendichvu")));
            chiTiet.setSodiencu(cursor.getInt(cursor.getColumnIndexOrThrow("sodiencu")));
            chiTiet.setSodienmoi(cursor.getInt(cursor.getColumnIndexOrThrow("sodienmoi")));
            chiTiet.setSonuoccu(cursor.getInt(cursor.getColumnIndexOrThrow("sonuoccu")));
            chiTiet.setSonuocmoi(cursor.getInt(cursor.getColumnIndexOrThrow("sonuocmoi")));
            chiTiet.setSosudung(cursor.getInt(cursor.getColumnIndexOrThrow("sosudung")));
            chiTiet.setThanhtien(cursor.getInt(cursor.getColumnIndexOrThrow("thanhtien")));
        }

        cursor.close();
        return chiTiet;
    }

}
