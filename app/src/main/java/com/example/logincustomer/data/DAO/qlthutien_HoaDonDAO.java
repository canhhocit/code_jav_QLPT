package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class qlthutien_HoaDonDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final int HOA_DON_CHUA_TON_TAI = 0;
    public static final int HOA_DON_CHUA_THANH_TOAN = 1;
    public static final int HOA_DON_DA_THANH_TOAN = 2;
    public qlthutien_HoaDonDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // ✅ Thêm hóa đơn mới
    public long insertHoaDon(qlthutien_HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("idphong", hd.getIdphong());

        String ngayNhap = hd.getNgaytaohdon();
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

        values.put("ngaytaohdon", ngayLuu );
        values.put("trangthai", hd.isTrangthai() ? 1 : 0);
        values.put("ghichu", hd.getGhichu());
        values.put("tongtien", hd.getTongtien());
        // ✅ 4 ảnh điện nước cũ mới
        values.put("image_diencu", hd.getImgDienCu());
        values.put("image_dienmoi", hd.getImgDienMoi());
        values.put("image_nuoccu", hd.getImgNuocCu());
        values.put("image_nuocmoi", hd.getImgNuocMoi());
        return db.insert("HoaDon", null, values);
    }

    // ✅ Cập nhật hóa đơn
    public int updateHoaDon(qlthutien_HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("idphong", hd.getIdphong());
        values.put("ngaytaohdon", hd.getNgaytaohdon());
        values.put("trangthai", hd.isTrangthai() ? 1 : 0);
        values.put("ghichu", hd.getGhichu());
        values.put("tongtien", hd.getTongtien());

        values.put("image_diencu", hd.getImgDienCu());
        values.put("image_dienmoi", hd.getImgDienMoi());
        values.put("image_nuoccu", hd.getImgNuocCu());
        values.put("image_nuocmoi", hd.getImgNuocMoi());
        return db.update("HoaDon", values, "idhoadon = ?", new String[]{String.valueOf(hd.getIdhoadon())});
    }

    // ✅ Xóa hóa đơn
    public int deleteHoaDon(int idhoadon) {
        return db.delete("HoaDon", "idhoadon = ?", new String[]{String.valueOf(idhoadon)});
    }

    // ✅ Lấy toàn bộ danh sách hóa đơn
    public ArrayList<qlthutien_HoaDon> getAllHoaDon() {
        ArrayList<qlthutien_HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM HoaDon", null);
        if (c.moveToFirst()) {
            do {
                qlthutien_HoaDon hd = new qlthutien_HoaDon();
                hd.setIdhoadon(c.getInt(c.getColumnIndexOrThrow("idhoadon")));
                hd.setIdphong(c.getInt(c.getColumnIndexOrThrow("idphong")));
                hd.setNgaytaohdon(c.getString(c.getColumnIndexOrThrow("ngaytaohdon")));
                hd.setTrangthai(c.getInt(c.getColumnIndexOrThrow("trangthai")) == 1);
                hd.setGhichu(c.getString(c.getColumnIndexOrThrow("ghichu")));
                hd.setTongtien(c.getDouble(c.getColumnIndexOrThrow("tongtien")));

                hd.setImgDienCu(c.getString(c.getColumnIndexOrThrow("image_diencu")));
                hd.setImgDienMoi(c.getString(c.getColumnIndexOrThrow("image_dienmoi")));
                hd.setImgNuocCu(c.getString(c.getColumnIndexOrThrow("image_nuoccu")));
                hd.setImgNuocMoi(c.getString(c.getColumnIndexOrThrow("image_nuocmoi")));

                list.add(hd);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
    // ✅ Lấy hóa đơn theo Id phòng
    public qlthutien_HoaDon getHoaDonByIdPhong(int idPhong) {
        Cursor c = db.rawQuery("SELECT * FROM HoaDon WHERE idphong = ?", new String[]{String.valueOf(idPhong)});
        if (c.moveToFirst()) {
            qlthutien_HoaDon hd = new qlthutien_HoaDon();
            hd.setIdhoadon(c.getInt(c.getColumnIndexOrThrow("idhoadon")));
            hd.setIdphong(c.getInt(c.getColumnIndexOrThrow("idphong")));
            hd.setNgaytaohdon(c.getString(c.getColumnIndexOrThrow("ngaytaohdon")));
            hd.setTrangthai(c.getInt(c.getColumnIndexOrThrow("trangthai")) == 1);
            hd.setGhichu(c.getString(c.getColumnIndexOrThrow("ghichu")));
            hd.setTongtien(c.getDouble(c.getColumnIndexOrThrow("tongtien")));

            hd.setImgDienCu(c.getString(c.getColumnIndexOrThrow("image_diencu")));
            hd.setImgDienMoi(c.getString(c.getColumnIndexOrThrow("image_dienmoi")));
            hd.setImgNuocCu(c.getString(c.getColumnIndexOrThrow("image_nuoccu")));
            hd.setImgNuocMoi(c.getString(c.getColumnIndexOrThrow("image_nuocmoi")));

            c.close();
            return hd;
        }
        c.close();
        return null;
    }
    // ✅ Kiểm tra phòng có hóa đơn chưa
    public boolean coHoaDonChoPhong(int idPhong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT idhoadon FROM HoaDon WHERE idphong = ?", new String[]{String.valueOf(idPhong)});
        boolean co = c.moveToFirst();
        c.close();
        return co;
    }

    public int kiemTraTinhTrangHoaDon(int idPhong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 1️⃣ Kiểm tra có hóa đơn chưa thanh toán không
        Cursor c1 = db.rawQuery(
                "SELECT idhoadon FROM HoaDon WHERE idphong = ? AND trangthai = 0",
                new String[]{String.valueOf(idPhong)}
        );
        if (c1.moveToFirst()) {
            c1.close();
            return 1; // có hóa đơn chưa thanh toán
        }
        c1.close();

        // 2️⃣ Kiểm tra xem tháng hiện tại đã có hóa đơn thanh toán chưa
        Cursor c2 = db.rawQuery(
                "SELECT idhoadon FROM HoaDon WHERE idphong = ? " +
                        "AND trangthai = 1 " +
                        "AND strftime('%m', ngaytaohdon) = strftime('%m', 'now') " +
                        "AND strftime('%Y', ngaytaohdon) = strftime('%Y', 'now')",
                new String[]{String.valueOf(idPhong)}
        );
        boolean coHoaDonThangNay = c2.moveToFirst();
        c2.close();

        if (coHoaDonThangNay) {
            return 2; // có hóa đơn đã thanh toán trong tháng này
        }
        return 0; // có thể tạo mới
    }
    public int kiemTraHoaDonThangHienTai(int idPhong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        // Mặc định kết quả là không có hóa đơn
        int ketQua = HOA_DON_CHUA_TON_TAI;

        try {
            // 2. CÂU LỆNH SQL TỐI ƯU
            // Lấy trạng thái hóa đơn của phòng trong tháng và năm hiện tại
            String sqlQuery = "SELECT trangthai FROM HoaDon WHERE idphong = ? " +
                    "AND strftime('%Y-%m', ngaytaohdon) = strftime('%Y-%m', 'now')";

            cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(idPhong)});

            // 3. XỬ LÝ KẾT QUẢ
            // Nếu con trỏ di chuyển được đến bản ghi đầu tiên (tức là tìm thấy hóa đơn)
            if (cursor.moveToFirst()) {
                // Lấy giá trị của cột 'trangthai' (cột đầu tiên, index = 0)
                int trangThaiDb = cursor.getInt(0);

                if (trangThaiDb == 1) { // Giả sử 1 là "đã thanh toán"
                    ketQua = HOA_DON_DA_THANH_TOAN;
                } else { // Ngược lại (trạng thái là 0) là "chưa thanh toán"
                    ketQua = HOA_DON_CHUA_THANH_TOAN;
                }
            }
        } catch (Exception e) {
            Log.e("HoaDonDAO", "Lỗi khi kiểm tra hóa đơn tháng hiện tại", e);
        } finally {
            // 4. ĐÓNG CURSOR ĐỂ TRÁNH RÒ RỈ BỘ NHỚ
            if (cursor != null) {
                cursor.close();
            }
        }
        return ketQua;
    }
    public boolean kiemTraHoaDonDaTonTaiWithThangNam(int idPhong, String thangNam) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Câu lệnh SQL không cần lấy 'trangthai' nữa, chỉ cần kiểm tra sự tồn tại.
            // Dùng "SELECT 1" sẽ hiệu quả hơn một chút.
            String sqlQuery = "SELECT 1 FROM HoaDon WHERE idphong = ? " +
                    "AND strftime('%Y-%m', ngaytaohdon) = ? LIMIT 1";

            cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(idPhong), thangNam});

            // Nếu con trỏ có bất kỳ bản ghi nào (count > 0), nghĩa là hóa đơn đã tồn tại.
            if (cursor != null && cursor.getCount() > 0) {
                return true; // Tìm thấy hóa đơn
            }
        } catch (Exception e) {
            Log.e("HoaDonDAO", "Lỗi khi kiểm tra sự tồn tại của hóa đơn", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // Mặc định trả về false nếu không tìm thấy hoặc có lỗi
        return false;
    }
    // ✅ Cập nhật trạng thái thanh toán riêng
    public int updateTrangThai(int idHoaDon, boolean trangThai) {
        ContentValues values = new ContentValues();
        values.put("trangthai", trangThai ? 1 : 0);
        return db.update("HoaDon", values, "idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
    }
    // ✅ Kiểm tra xem hóa đơn có thể sửa không (chưa thanh toán thì mới được)
    public boolean coTheSuaHoaDon(int idPhong) {
        Cursor c = db.rawQuery(
                "SELECT trangthai FROM HoaDon WHERE idPhong = ?",
                new String[]{String.valueOf(idPhong)}
        );
        boolean coTheSua = false;
        if (c.moveToFirst()) {
            int trangThai = c.getInt(c.getColumnIndexOrThrow("trangthai"));
            coTheSua = (trangThai == 0);
        }
        c.close();
        return coTheSua;
    }

    public qlthutien_HoaDon getHoaDonById(int idHoaDon) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        qlthutien_HoaDon hoaDon = null;

        Cursor cursor = db.rawQuery("SELECT * FROM HoaDon WHERE Idhoadon = ?", new String[]{String.valueOf(idHoaDon)});
        if (cursor.moveToFirst()) {
            hoaDon = new qlthutien_HoaDon();
            hoaDon.setIdhoadon(cursor.getInt(cursor.getColumnIndexOrThrow("idhoadon")));
            hoaDon.setIdphong(cursor.getInt(cursor.getColumnIndexOrThrow("idphong")));
            hoaDon.setNgaytaohdon(cursor.getString(cursor.getColumnIndexOrThrow("ngaytaohdon")));
            hoaDon.setTrangthai(cursor.getInt(cursor.getColumnIndexOrThrow("trangthai")) == 1);
            hoaDon.setGhichu(cursor.getString(cursor.getColumnIndexOrThrow("ghichu")));
            hoaDon.setTongtien(cursor.getDouble(cursor.getColumnIndexOrThrow("tongtien")));
            hoaDon.setImgDienCu(cursor.getString(cursor.getColumnIndexOrThrow("image_diencu")));
            hoaDon.setImgDienMoi(cursor.getString(cursor.getColumnIndexOrThrow("image_dienmoi")));
            hoaDon.setImgNuocCu(cursor.getString(cursor.getColumnIndexOrThrow("image_nuoccu")));
            hoaDon.setImgNuocMoi(cursor.getString(cursor.getColumnIndexOrThrow("image_nuocmoi")));
        }

        cursor.close();
        return hoaDon;
    }
    public int getIdHoaDonByIdPhong(int idPhong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int idHoaDon = -1; // mặc định nếu không tìm thấy

        Cursor cursor = db.rawQuery(
                "SELECT idhoadon FROM HoaDon WHERE idphong = ? ORDER BY idhoadon DESC LIMIT 1",
                new String[]{String.valueOf(idPhong)}
        );

        if (cursor.moveToFirst()) {
            idHoaDon = cursor.getInt(0);
        }

        cursor.close();
        return idHoaDon;
    }
    public int getIdPhongByIdHoaDon(int idHoaDon) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int idPhong = -1; // mặc định nếu không tìm thấy

        Cursor cursor = db.rawQuery(
                "SELECT idphong FROM HoaDon WHERE idhoadon = ?",
                new String[]{String.valueOf(idHoaDon)}
        );

        if (cursor.moveToFirst()) {
            idPhong = cursor.getInt(0);
        }

        cursor.close();
        return idPhong;
    }
    public boolean trangthaihoadon(int idHoaDon) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean result = false;

        // Câu truy vấn lấy cột trangthai theo id hóa đơn
        Cursor cursor = db.rawQuery(
                "SELECT trangthai FROM HoaDon WHERE idhoadon = ?",
                new String[]{String.valueOf(idHoaDon)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            int trangthai = cursor.getInt(0); // lấy giá trị cột trangthai
            result = (trangthai == 1);       // 1 = đã thanh toán (true)
            cursor.close();
        }

        return result; // false nếu chưa thanh toán hoặc không tồn tại
    }
    // ✅ Kiểm tra xem phòng có hóa đơn nào CHƯA thanh toán không
    public boolean hasUnpaidHoaDonByPhong(int idPhong) {
        boolean hasUnpaid = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn tất cả hóa đơn của phòng có trạng thái = 0 (chưa thanh toán)
        Cursor cursor = db.rawQuery(
                "SELECT idhoadon FROM HoaDon WHERE idphong = ? AND trangthai = 0",
                new String[]{String.valueOf(idPhong)}
        );

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                hasUnpaid = true; // có ít nhất 1 hóa đơn chưa thanh toán
            }
            cursor.close();
        }

        return hasUnpaid;
    }
    // ✅ Lấy idhoadon của hóa đơn mới nhất theo idphong
    public int getNewestHoaDonIdByPhong(int idPhong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int idHoaDon = -1; // mặc định -1 nếu không có hóa đơn

        Cursor c = db.rawQuery(
                "SELECT idhoadon FROM HoaDon WHERE idphong = ? ORDER BY datetime(ngaytaohdon) DESC LIMIT 1",
                new String[]{String.valueOf(idPhong)}
        );

        if (c.moveToFirst()) {
            idHoaDon = c.getInt(c.getColumnIndexOrThrow("idhoadon"));
        }

        c.close();
        return idHoaDon;
    }
    public List<qlthutien_HoaDon> filterHoaDon(String fromDate, String toDate, String tenPhong, Boolean trangThai, boolean showAll) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<qlthutien_HoaDon> list = new ArrayList<>();
        List<String> args = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM HoaDon WHERE 1=1");

        if (!showAll) {
            if (fromDate != null && !fromDate.isEmpty()) {
                sql.append(" AND date(ngaytaohdon) >= date(?)");
                args.add(fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                sql.append(" AND date(ngaytaohdon) <= date(?)");
                args.add(toDate);
            }
            if (tenPhong != null && !tenPhong.equals("Tất cả")) {
                sql.append(" AND idphong IN (SELECT idphong FROM PhongTro WHERE tenphong = ?)");
                args.add(tenPhong);
            }
            if (trangThai != null) {
                sql.append(" AND trangthai = ?");
                args.add(trangThai ? "1" : "0");
            }
        }

        Cursor c = db.rawQuery(sql.toString(), args.toArray(new String[0]));
        if (c.moveToFirst()) {
            do {
                qlthutien_HoaDon hd = new qlthutien_HoaDon();
                hd.setIdhoadon(c.getInt(c.getColumnIndexOrThrow("idhoadon")));
                hd.setIdphong(c.getInt(c.getColumnIndexOrThrow("idphong")));
                hd.setNgaytaohdon(c.getString(c.getColumnIndexOrThrow("ngaytaohdon")));
                hd.setTongtien(c.getDouble(c.getColumnIndexOrThrow("tongtien")));
                hd.setTrangthai(c.getInt(c.getColumnIndexOrThrow("trangthai")) == 1);
                list.add(hd);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


}
