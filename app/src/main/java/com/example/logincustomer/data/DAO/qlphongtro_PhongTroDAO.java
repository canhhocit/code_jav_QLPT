package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.data.Model.hopdong_ifRoom;

import java.util.ArrayList;
import java.util.List;

public class qlphongtro_PhongTroDAO {
    private SQLiteDatabase db;

    public qlphongtro_PhongTroDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // 🔹 Thêm phòng
    public long insertPhongTro(PhongTro phong) {
        ContentValues values = new ContentValues();
        values.put("tenphong", phong.getTenphong()); // ✅ đúng tên cột
        values.put("songuoi", phong.getSonguoi());   // ✅ đúng tên cột
        values.put("gia", phong.getGia());           // ✅ đúng tên cột
        values.put("idhopdong", phong.getIdhopdong()); // nếu có, có thể null

        return db.insert("PhongTro", null, values);
    }

    // 🔹 Cập nhật phòng
    public int updatePhongTro(PhongTro phong) {
        ContentValues values = new ContentValues();
        values.put("tenphong", phong.getTenphong()); // đúng tên cột
        values.put("gia", phong.getGia());           // đúng tên cột
        values.put("songuoi", phong.getSonguoi());  // đúng tên cột

        return db.update("PhongTro", values, "idphong = ?", new String[]{String.valueOf(phong.getIdphong())});
    }

    // 🔹 Xóa phòng theo id
    public int deletePhongTro(int idphong) {
        return db.delete("PhongTro", "idphong = ?", new String[]{String.valueOf(idphong)});
    }

    // 🔹 Lấy toàn bộ danh sách phòng
    public List<PhongTro> getAllPhongTro() {
        List<PhongTro> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM PhongTro order by tenphong asc", null);
        if (c.moveToFirst()) {
            do {
                list.add(new PhongTro(
                        c.getInt(0),      // idphong
                        c.getString(1),   // tenphong
                        c.getInt(2),      // songuoi
                        c.getDouble(3),   // gia
                        c.getInt(4)       // idhopdong
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // 🔹 Tìm kiếm phòng theo tên
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
                        c.getInt(4)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

// Kiểm tra phòng tồn tại (so sánh không phân biệt hoa thường)
    public boolean isPhongExists(String tenPhong) {
        if (tenPhong == null) return false;
        Cursor c = db.rawQuery(
                "SELECT idphong FROM PhongTro WHERE lower(tenphong) = lower(?)",
                new String[]{tenPhong.trim()}
        );
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    // Dùng khi sửa: kiểm tra tên có tồn tại ở phòng khác (loại trừ id hiện tại)
    public boolean isPhongExistsExceptId(String tenPhong, int excludeId) {
        if (tenPhong == null) return false;
        Cursor c = db.rawQuery(
                "SELECT idphong FROM PhongTro WHERE lower(tenphong) = lower(?) AND idphong <> ?",
                new String[]{tenPhong.trim(), String.valueOf(excludeId)}
        );
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
    // 🔹 Hàm đếm số người trong phòng
    public int countNguoiTrongPhong(int idPhong) {
        int count = 0;
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM KhachThue WHERE idphong = ?", new String[]{String.valueOf(idPhong)});
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    // Cập nhật số người trong phòng
    public int updateSoNguoiPhong(int idPhong) {
        int soNguoi = countNguoiTrongPhong(idPhong);

        ContentValues values = new ContentValues();
        values.put("songuoi", soNguoi);

        return db.update("PhongTro", values, "idphong = ?", new String[]{String.valueOf(idPhong)});
    }
    public PhongTro getPhongById(int idPhong) {
        PhongTro p = null;
        Cursor c = db.rawQuery("SELECT * FROM PhongTro WHERE idphong = ?", new String[]{ String.valueOf(idPhong) });
        if (c != null && c.moveToFirst()) {
            // Sử dụng constructor giống như getAllPhongTro để tránh gọi setter sai tên
            p = new PhongTro(
                    c.getInt(0),      // idphong
                    c.getString(1),   // tenphong
                    c.getInt(2),      // songuoi
                    c.getDouble(3),   // gia
                    c.getInt(4)       // idhopdong
            );
        }
        if (c != null) c.close();
        return p;
    }
    public String getTenPhongById(int idPhong) {
        String tenPhong = "";
        Cursor c = db.rawQuery("SELECT tenphong FROM PhongTro WHERE idphong = ?", new String[]{String.valueOf(idPhong)});
        if (c.moveToFirst()) {
            tenPhong = c.getString(0);
        }
        c.close();
        return tenPhong;
    }

}
