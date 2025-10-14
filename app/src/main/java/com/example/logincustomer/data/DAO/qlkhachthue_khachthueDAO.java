package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.khachthue;

import java.util.ArrayList;
import java.util.List;

public class qlkhachthue_khachthueDAO {
    private SQLiteDatabase db;
    public qlkhachthue_khachthueDAO(Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //them khach
    public void insertKhachThue(khachthue kt){
        ContentValues vlu = new ContentValues();
        vlu.put("hoten",kt.getHoten());
        vlu.put("gioitinh",kt.getGioitinh());
        vlu.put("ngaysinh",kt.getNgaysinh());
        vlu.put("sdt",kt.getSdt());
        vlu.put("diachi",kt.getDiachi());
        vlu.put("idphong", kt.getIdphong());
        db.insert("KhachThue",null,vlu);
    }
    // update
    public void updateKhachThue(khachthue kt) {
        ContentValues vlu = new ContentValues();
        vlu.put("hoten",kt.getHoten());
        vlu.put("gioitinh",kt.getGioitinh());
        vlu.put("ngaysinh",kt.getNgaysinh());
        vlu.put("sdt",kt.getSdt());
        vlu.put("diachi",kt.getDiachi());
        vlu.put("idphong", kt.getIdphong());
        db.update("KhachThue", vlu, "idkhach=?", new String[]{String.valueOf(kt.getIdkhach())});
    }
    //delete
    public void deleteKhacThue(int idkhach) {
        db.delete("KhachThue", "idkhach=?", new String[]{String.valueOf(idkhach)});
    }

    //lay toan bo ds khach
    public List<khachthue> getAllKhachThue() {
        List<khachthue> list = new ArrayList<>();
        Cursor cur = db.rawQuery("SELECT * FROM KhachThue", null);

        while (cur.moveToNext()) {
            list.add(new khachthue(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getInt(6)
            ));
        }
        cur.close();
        return list;
    }

    //find
    public List<khachthue> getfindbyname(String name){
        List<khachthue> list = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from KhachThue where hoten like ?",
                                    new String[]{"%"+ name+"%"});
        while (cur.moveToNext()) {
            list.add(new khachthue(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getInt(6)
            ));
        }
        cur.close();
        return list;
    }
    //check ex
    public int checkexists(khachthue khach){
        Cursor cursor = db.rawQuery("select * from KhachThue where hoten=? and gioitinh=? and ngaysinh=? and sdt=? and diachi=?",
                new String[]{khach.getHoten(), khach.getGioitinh(), khach.getNgaysinh(), khach.getSdt(), khach.getDiachi()});
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    //check nguoi trong phong
    public int checkCountperson(int id){
        Cursor cursor = db.rawQuery("select songuoi from PhongTro where idphong=?",new String[]{String.valueOf(id)});
        int num =-1;
        if(cursor.moveToFirst()){
            num = cursor.getInt(0);
        }
        cursor.close();
        return num;
    }
    //lay ten phong tu id de show lv
    public String getTenphongbyID(int idphong){
        String tenphong=null;
        Cursor cur = db.rawQuery("select tenphong from PhongTro where idphong =?",new String[]{String.valueOf(idphong)});
        if (cur.moveToFirst()) tenphong = cur.getString(0);
        cur.close();
        return tenphong;
    }

    //check id tu ten phong
    public boolean checkIDbyTenphong(String tenphong) {
        Cursor cur = db.rawQuery("SELECT idphong FROM PhongTro WHERE tenphong = ?", new String[]{tenphong});
        boolean exists = cur.moveToFirst();  // if 1=
        cur.close();
        return exists;
    }

    public int getIDbyTenphong(String tenphong) {
        int id = -1;
        Cursor cur = db.rawQuery("SELECT idphong FROM PhongTro WHERE tenphong = ?", new String[]{tenphong});
        if (cur.moveToFirst()) {
            id = cur.getInt(0);
        }
        cur.close();
        return id;
    }
    public List<khachthue> getKhachTheoPhong(int idPhong) {
        List<khachthue> list = new ArrayList<>();
        Cursor cur = db.rawQuery("SELECT * FROM KhachThue WHERE idphong = ?", new String[]{ String.valueOf(idPhong) });
        while (cur.moveToNext()) {
            list.add(new khachthue(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getInt(6)
            ));
        }
        cur.close();
        return list;
    }


}
