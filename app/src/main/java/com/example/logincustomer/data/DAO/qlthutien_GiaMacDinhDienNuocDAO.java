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
        ensureRecordExists();
    }

    // Tạo dòng dữ liệu mặc định nếu chưa có
    private void ensureRecordExists() {
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM GiaMacDinh", null);
        if (c.moveToFirst() && c.getInt(0) == 0) {
            ContentValues values = new ContentValues();
            values.put("id", 1);
            values.put("giadien", 3500);
            values.put("gianuoc", 20000);
            db.insert("GiaMacDinh", null, values);
        }
        c.close();
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
}
