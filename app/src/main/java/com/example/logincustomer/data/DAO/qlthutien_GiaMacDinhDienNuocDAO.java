package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;

public class qlthutien_GiaMacDinhDienNuocDAO {
    private SQLiteDatabase db;

    public qlthutien_GiaMacDinhDienNuocDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public boolean insertGiaMacDinh(double giaDien, double giaNuoc) {
        if (hasGiaMacDinh()) {
            updateGiaMacDinh(giaDien, giaNuoc);
            return true;
        } else {
            ContentValues values = new ContentValues();
            values.put("id", 1);
            values.put("giadien", giaDien);
            values.put("gianuoc", giaNuoc);
            long result = db.insert("GiaMacDinh", null, values);
            return result != -1;
        }
    }


    // Cập nhật giá điện & nước
    public void updateGiaMacDinh(double giaDien, double giaNuoc) {
        ContentValues values = new ContentValues();
        values.put("giadien", giaDien);
        values.put("gianuoc", giaNuoc);
        db.update("GiaMacDinh", values, "id = 1", null);
    }

    // Lấy giá điện
    public double getGiaDien() {
        Cursor c = db.rawQuery("SELECT giadien FROM GiaMacDinh WHERE id = 1", null);
        double result = 0;
        if (c.moveToFirst()) result = c.getDouble(0);
        c.close();
        return result;
    }

    // Lấy giá nước
    public double getGiaNuoc() {
        Cursor c = db.rawQuery("SELECT gianuoc FROM GiaMacDinh WHERE id = 1", null);
        double result = 0;
        if (c.moveToFirst()) result = c.getDouble(0);
        c.close();
        return result;
    }

    public boolean hasGiaMacDinh() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM GiaMacDinh", null);
        boolean hasData = false;

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            hasData = count > 0;
        }

        cursor.close();
        return hasData;
    }
}
