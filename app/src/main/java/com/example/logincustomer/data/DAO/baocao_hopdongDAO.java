package com.example.logincustomer.data.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.baocao_hopdong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class baocao_hopdongDAO {
    private SQLiteDatabase db;

    public baocao_hopdongDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();
    }

    public List<baocao_hopdong> getAllbc_hd() {
        List<baocao_hopdong> list = new ArrayList<>();
        String sql = "SELECT p.idphong, h.idhopdong, h.ngaykyhopdong " +
                "FROM PhongTro p " +
                "JOIN HopDong h ON p.idphong = h.idphong " +
                "order by p.tenphong asc";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int idp = cursor.getInt(0);
                int idhopdong = cursor.getInt(1);
                String ngayky = cursor.getString(2);

                int hanConLai = tinhHanConLai(ngayky, 6);

                baocao_hopdong bc = new baocao_hopdong(idp, idhopdong, hanConLai);
                list.add(bc);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    private int tinhHanConLai(String ngayKy, int thangHieuLuc) {
        try {
            Date ngayKyDate= new SimpleDateFormat("dd/MM/yyy").parse(ngayKy);

            long timeHientai = System.currentTimeMillis();
            long hanHopDong = ngayKyDate.getTime() + (long) thangHieuLuc * 30 * 24 * 60 * 60 * 1000;
            long conLai = hanHopDong - timeHientai;

            return (int) (conLai / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            return 0;
        }
    }

    public String getNgaykybyIDHD(int id){
        Cursor cursor= db.rawQuery("select ngaykyhopdong from HopDong where idhopdong=?",new String[]{String.valueOf(id)});
        String ngay="";
        if(cursor.moveToFirst()){
            ngay=cursor.getString(0).trim();
        }
        cursor.close();
        return ngay;
    }
    public String getTenphongbyID(int idp){
        Cursor cursor= db.rawQuery("select tenphong from phongtro where idphong=?",new String[]{String.valueOf(idp)});
        String tenphong="";
        if(cursor.moveToFirst()){
            tenphong=cursor.getString(0).trim();
        }
        cursor.close();
        return tenphong;
    }
}
