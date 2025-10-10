package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.ChiTietHoaDon;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public ChiTietHoaDonDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert 1 chi tiết
    public long insertChiTiet(ChiTietHoaDon ct) {
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
    public ArrayList<ChiTietHoaDon> getChiTietByHoaDonId(int idHoaDon) {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
        if (c.moveToFirst()) {
            do {
                ChiTietHoaDon ct = new ChiTietHoaDon();
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
    public List<ChiTietHoaDon> getByHoaDonId(int idHoaDon) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
        if (c.moveToFirst()) {
            do {
                ChiTietHoaDon ct = new ChiTietHoaDon();
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

}
