package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.baocao_thuchi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        String ngayNhap = thuchi.getNgaythuchi();
        String ngayLuu = ngayNhap;

        try {
            // dd/MM/yyyy đổi sang yyyy-MM-dd để lưu DB
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = input.parse(ngayNhap);
            if (date != null) {
                ngayLuu = dbFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        values.put("ngaythuchi", ngayLuu);
        db.insert("ThuChi", null, values);
    }


    public int updateThuchi(baocao_thuchi thuchi) {
        ContentValues values = new ContentValues();
        values.put("tenthuchi", thuchi.getTenthuchi());
        values.put("sotien", thuchi.getSotienthuchi());
        values.put("loai", thuchi.getLoaithuchi());

        String ngayNhap = thuchi.getNgaythuchi();
        String ngayLuu = ngayNhap;

        try {
            // dd/MM/yyyy đổi sang yyyy-MM-dd để lưu DB
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = input.parse(ngayNhap);
            if (date != null) {
                ngayLuu = dbFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        values.put("ngaythuchi", ngayLuu);
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
    //check tontai
    public int checkexists(baocao_thuchi thuchi){
        Cursor cursor = db.rawQuery("select * from ThuChi where tenthuchi=? and sotien=? and loai=? and ngaythuchi=?",
                new String[]{thuchi.getTenthuchi(),String.valueOf(thuchi.getSotienthuchi()),thuchi.getLoaithuchi(),thuchi.getNgaythuchi()});
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    //check list
    public int chechlist(){
        Cursor cursor = db.rawQuery("select * from ThuChi",null);
        int countlist = cursor.getCount();
        cursor.close();
        return countlist;
    }

    //----tim kiem
    // tim theo loai

    public List<baocao_thuchi> searchByName(String str) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE tenthuchi LIKE ?", new String[]{"%" + str + "%"});
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
    public List<baocao_thuchi> searchByDate(String ngay) {
        String ngayLuu = ngay;

        try {
            // dd/MM/yyyy đổi sang yyyy-MM-dd
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = input.parse(ngay);
            if (date != null) {
                ngayLuu = dbFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi WHERE ngaythuchi=?", new String[]{ngayLuu});
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
    //--loc theo loai
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

    //--Sapxep
    public List<baocao_thuchi> orderbyDate(String order) {
        List<baocao_thuchi> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuChi order by date(ngaythuchi) "+ order, null);
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
