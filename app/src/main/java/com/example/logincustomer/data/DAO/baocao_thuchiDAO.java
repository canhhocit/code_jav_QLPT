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
        values.put("ngaythuchi", thuchi.getNgaythuchi()); //  format yyyy-MM-dd
        db.insert("ThuChi", null, values);
    }

    public int updateThuchi(baocao_thuchi thuchi) {
        ContentValues values = new ContentValues();
        values.put("tenthuchi", thuchi.getTenthuchi());
        values.put("sotien", thuchi.getSotienthuchi());
        values.put("loai", thuchi.getLoaithuchi());
        values.put("ngaythuchi", thuchi.getNgaythuchi());
        return db.update("ThuChi", values, "idthuchi = ?", new String[]{String.valueOf(thuchi.getIdthuchi())});
    }

    public void deleteThuchi(int id) {
         db.delete("ThuChi", "idthuchi = ?", new String[]{String.valueOf(id)});
    }
    public void deleteAll(){
        db.execSQL("delete from ThuChi");
    }

    // get all
    public List<baocao_thuchi> getAllthuchi() {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0), //id
                        cursor.getString(1), //ten
                        cursor.getInt(2), //tien
                        cursor.getString(3),//loai
                        cursor.getString(4)));//ngay
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // loc theo loai thu/chi
    public List<baocao_thuchi> searchByLoai(String loai) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE loai LIKE ?", new String[]{"%" + loai + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    //----tim kiem
    // tim theo ten
    public List<baocao_thuchi> searchByName(String loai) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE loai LIKE ?", new String[]{"%" + loai + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // timtheongay
    public List<baocao_thuchi> searchByDate(String date) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE ngaythuchi LIKE ?", new String[]{"%" + date + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    //--Sapxep
    public List<baocao_thuchi> orderbyDate(String order) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi order by ngaythuchi "+ order, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<baocao_thuchi> orderbyMoney(String order) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi order by sotien "+ order, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new baocao_thuchi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
