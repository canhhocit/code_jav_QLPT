package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.hopdong;
import com.example.logincustomer.data.Model.hopdong_display;
import com.example.logincustomer.data.Model.hopdong_ifKhach;
import com.example.logincustomer.data.Model.hopdong_ifRoom;
import com.example.logincustomer.data.Model.khachthue;

import java.util.ArrayList;
import java.util.List;

public class qlhopdong_hopdongDAO {
    private SQLiteDatabase db;

    public qlhopdong_hopdongDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertHD(hopdong hd) {
        ContentValues vlu = new ContentValues();
        vlu.put("cccd", hd.getCccd());
        vlu.put("ngaykyhopdong", hd.getNgayky());
        vlu.put("idphong", hd.getIdphong());
        vlu.put("idkhach", hd.getIdkhach());
        return db.insert("HopDong", null, vlu);
    }
    //them khach
    public long insertKhachThue(khachthue kt){
        ContentValues vlu = new ContentValues();
        vlu.put("hoten",kt.getHoten());
        vlu.put("gioitinh",kt.getGioitinh());
        vlu.put("ngaysinh",kt.getNgaysinh());
        vlu.put("sdt",kt.getSdt());
        vlu.put("diachi",kt.getDiachi());
        vlu.put("idphong", kt.getIdphong());
        return db.insert("KhachThue",null,vlu);
    }
    public int getIDkhachbyten(String ten){
        Cursor cursor = db.rawQuery("select idkhach from khachthue where hoten =?",new String[]{ten});
        int id=-1;
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
    public int getIdHDbytenphong(String tenphong) {
        int id = -1;
        String sql = "SELECT h.idhopdong " +
                "FROM HopDong h " +
                "JOIN PhongTro p ON h.idphong = p.idphong " +
                "WHERE p.tenphong = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tenphong});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }


    public hopdong getInf_HDbyIDphong(int idp){
        hopdong hd=null ;
        Cursor cursor = db.rawQuery("select * from hopdong where idphong =?",new String[]{String.valueOf(idp)});
        if(cursor.moveToFirst()){
            hd = new hopdong(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getInt(4));
        }
        cursor.close();
        return hd;
    }

    public long updateHD(hopdong hd) {
        ContentValues vlu = new ContentValues();
        vlu.put("cccd", hd.getCccd());
        vlu.put("ngaykyhopdong", hd.getNgayky());
        vlu.put("idphong", hd.getIdphong());
        vlu.put("idkhach", hd.getIdkhach());
        return db.update("HopDong", vlu, "idhopdong=?",
                new String[]{String.valueOf(hd.getIdhopdong())});
    }

    public int deleteHD(int id) {
        return db.delete("HopDong", "idhopdong=?",
                new String[]{String.valueOf(id)});
    }
    public void deleteKhacThue(int idkhach) {
        db.delete("KhachThue", "idkhach=?", new String[]{String.valueOf(idkhach)});
    }
    public List<hopdong_display> getAllHopDongDisplay() {
        List<hopdong_display> list = new ArrayList<>();
        String sql = "SELECT p.tenphong, k.hoten, h.ngaykyhopdong " +
                "FROM HopDong h " +
                "JOIN PhongTro p ON h.idphong = p.idphong " +
                "JOIN KhachThue k ON h.idkhach = k.idkhach";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                list.add(new hopdong_display(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }



    public List<hopdong_ifRoom> getInfPhongTro() {
        List<hopdong_ifRoom> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT idphong, tenphong, songuoi, gia FROM PhongTro", null);
        if (c.moveToFirst()) {
            do {
                list.add(new hopdong_ifRoom(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getDouble(3)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public hopdong_ifKhach getinfKhachbyID(int id) {
        hopdong_ifKhach ifkhach = null;
        Cursor cursor = db.rawQuery(
                "SELECT hoten, ngaysinh, sdt FROM KhachThue WHERE idkhach=?",
                new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            ifkhach = new hopdong_ifKhach(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }
        cursor.close();
        return ifkhach;
    }

    public hopdong_ifRoom getinfRoombyID(int id) {
        hopdong_ifRoom ifRoom = null;
        Cursor cursor = db.rawQuery(
                "SELECT tenphong, songuoi, gia FROM PhongTro WHERE idphong=?",
                new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            ifRoom = new hopdong_ifRoom(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getDouble(2)
            );
        }
        cursor.close();
        return ifRoom;
    }
    public hopdong getinfHDbyIdHD(int id){
        Cursor cursor = db.rawQuery("select cccd, ngaykyhopdong,idphong,idkhach from HopDong where idhopdong=?",
                new String[]{String.valueOf(id)});
        hopdong hd = null;
        if(cursor.moveToFirst()){
            hd = new hopdong(cursor.getString(0),cursor.getString(1),
                    cursor.getInt(2),cursor.getInt(3));
        }
        cursor.close();
        return hd;
    }


}
