package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.DichVuCon;

import java.util.ArrayList;

public class DichVuConDAO {
    private SQLiteDatabase db;

    public DichVuConDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(DichVuCon dichVuCon) {
        ContentValues values = new ContentValues();
        values.put("tendichvu", dichVuCon.getTendichvu());
        values.put("giatien", dichVuCon.getGiatien());
        values.put("idchitiethoadon", dichVuCon.getIdchitiethoadon());
        return db.insert("DichVuCon", null, values);
    }

    public int update(DichVuCon dichVuCon) {
        ContentValues values = new ContentValues();
        values.put("tendichvu", dichVuCon.getTendichvu());
        values.put("giatien", dichVuCon.getGiatien());
        return db.update("DichVuCon", values, "iddichvucon = ?",
                new String[]{String.valueOf(dichVuCon.getIddichvucon())});
    }

    public int delete(int id) {
        return db.delete("DichVuCon", "iddichvucon = ?", new String[]{String.valueOf(id)});
    }

    // 🔹 Xoá toàn bộ bảng DichVuCon
    public void deleteAll() {
        db.delete("DichVuCon", null, null);
    }
    public ArrayList<DichVuCon> getAll() {
        ArrayList<DichVuCon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DichVuCon", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int idChiTiet = cursor.getInt(1);
                String ten = cursor.getString(2);
                double gia = cursor.getDouble(3);
                list.add(new DichVuCon(id, idChiTiet, ten, gia));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
