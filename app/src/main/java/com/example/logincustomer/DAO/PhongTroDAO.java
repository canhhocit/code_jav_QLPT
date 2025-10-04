package com.example.logincustomer.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.Model.PhongTro;

import java.util.ArrayList;
import java.util.List;

public class PhongTroDAO {
    private SQLiteDatabase db;

    public PhongTroDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Lấy toàn bộ danh sách phòng
    public List<PhongTro> getAllPhongTro() {
        List<PhongTro> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM PhongTro", null);
        if (c.moveToFirst()) {
            do {
                list.add(new PhongTro(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getDouble(3),
                        c.getString(4),
                        c.getInt(5)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Tìm kiếm phòng theo tên
    public List<PhongTro> searchPhong(String keyword) {
        List<PhongTro> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM PhongTro WHERE tenphong LIKE ?", new String[]{"%" + keyword + "%"});
        if (c.moveToFirst()) {
            do {
                list.add(new PhongTro(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getDouble(3),
                        c.getString(4),
                        c.getInt(5)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}

