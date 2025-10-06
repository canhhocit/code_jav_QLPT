package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.baocao_thuchi;

import java.util.ArrayList;
import java.util.List;

public class baocao_thuchiDAO {
    private SQLiteDatabase db;

    public baocao_thuchiDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }


    public void insertThuchi(baocao_thuchi thuchi) {
        ContentValues values = new ContentValues();
        values.put("tenthuchi", thuchi.getTenthuchi());
        values.put("sotien", thuchi.getSotienthuchi());
        values.put("loai", thuchi.getLoaithuchi());
        values.put("ngaythuchi", thuchi.getNgaythuchi()); // vẫn là String format yyyy-MM-dd
        db.insert("ThuChi", null, values);
    }

    public void updateThuchi(baocao_thuchi thuchi) {
        ContentValues values = new ContentValues();
        values.put("tenthuchi", thuchi.getTenthuchi());
        values.put("sotien", thuchi.getSotienthuchi());
        values.put("loai", thuchi.getLoaithuchi());
        values.put("ngaythuchi", thuchi.getNgaythuchi());
        db.update("ThuChi", values, "idthuchi = ?", new String[]{String.valueOf(thuchi.getIdthuchi())});
    }

    public void deleteThuchi(int id) {
         db.delete("ThuChi", "idthuchi = ?", new String[]{String.valueOf(id)});
    }

    // show all
    public List<baocao_thuchi> getAllthuchi() {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi", null);
        if (cursor.moveToFirst()) {
            do {
//                baocao_thuchi item = new baocao_thuchi();
//                item.setIdthuchi();
//                item.setTenthuchi(cursor.getString(1));
//                item.setSotienthuchi(cursor.getInt(2));
//                item.setLoaithuchi(cursor.getString(3));
//                item.setNgaythuchi(cursor.getString(4));
//                list.add(new baocao_thuchi(cursor.getInt(0),
//                        ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // ✅ Tìm theo loại (VD: "Thu" hoặc "Chi")
    public List<baocao_thuchi> searchByLoai(String loai) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE loai LIKE ?", new String[]{"%" + loai + "%"});
        if (cursor.moveToFirst()) {
            do {
                baocao_thuchi item = new baocao_thuchi();
                item.setIdthuchi(cursor.getInt(0));
                item.setTenthuchi(cursor.getString(1));
                item.setSotienthuchi(cursor.getInt(2));
                item.setLoaithuchi(cursor.getString(3));
                item.setNgaythuchi(cursor.getString(4));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // ✅ Tìm theo ngày (VD: "2025-10-06")
    public List<baocao_thuchi> searchByDate(String date) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE ngaythuchi LIKE ?", new String[]{"%" + date + "%"});
        if (cursor.moveToFirst()) {
            do {
                baocao_thuchi item = new baocao_thuchi();
                item.setIdthuchi(cursor.getInt(0));
                item.setTenthuchi(cursor.getString(1));
                item.setSotienthuchi(cursor.getInt(2));
                item.setLoaithuchi(cursor.getString(3));
                item.setNgaythuchi(cursor.getString(4));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
